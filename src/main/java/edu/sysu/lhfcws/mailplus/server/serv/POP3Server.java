package edu.sysu.lhfcws.mailplus.server.serv;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDBCounter;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.ThreadMonitor;
import edu.sysu.lhfcws.mailplus.server.serv.executor.POP3Executor;
import edu.sysu.lhfcws.mailplus.server.serv.executor.POP3RQsWatcher;
import edu.sysu.lhfcws.mailplus.server.serv.executor.ServerListener;
import edu.sysu.lhfcws.mailplus.commons.queue.MultiPersistentRequestQueues;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * POP3 server.
 * @author lhfcws
 * @time 14-10-27.
 */
public class POP3Server extends AdvRunnable {
    public static final String NAME = "POP3Server";
    private static Log LOG = LogFactory.getLog(POP3Server.class);

    // key: pop3Host ; value: execute thread
    private ConcurrentHashMap<String, AdvRunnable> scheduler;
    private BDBCounter counter;
    private ExecutorService threadPool;
    private ThreadMonitor threadMonitor;
    private ServerListener serverListener;
    private MultiPersistentRequestQueues multiPersistentRequestQueues;

    public POP3Server(ServerListener serverListener) {
        super(NAME);
        this.scheduler = new ConcurrentHashMap<String, AdvRunnable>();
        this.counter = new BDBCounter(Consts.BDB_PATH + NAME);
        this.serverListener = serverListener;
        this.multiPersistentRequestQueues = RQCenter.getMultiRQ(POP3RQsWatcher.NAME);
        this.threadPool = Executors.newCachedThreadPool();
        this.threadMonitor = new ThreadMonitor();
        this.threadMonitor.register(new POP3RQsWatcher(POP3RQsWatcher.NAME, this));
    }

    public void start() {
        this.threadMonitor.start();
        this.threadMonitor.monitor();
    }

    /**
     *
     * @param req
     */
    public void execute(Request req) {
        LogUtil.debug("POP3Server execute: " + req);
        String pop3Host = req.getMailUser().getPop3Host();
        this.counter.inc(pop3Host);

        if (scheduler.containsKey(pop3Host)) {
            // put into corresponding smtp queue
            this.multiPersistentRequestQueues.enQueue(req.getMailUser().getPop3Host(), req);
        } else {
            POP3Executor executor = new POP3Executor(pop3Host, this);
            executor.init(req);
            this.scheduler.put(pop3Host, executor);
            // start the sending thread
            this.threadPool.execute(executor);
        }
    }

    /**
     * Push Request back to the RQ, as Remote servers are not so stable.
     * We have to try several times.
     * @param req
     */
    public void repushRequest(Request req) {
        LogUtil.debug("POP3Server repush: " + req);
        this.multiPersistentRequestQueues.nap(req.getMailUser().getPop3Host());
        this.dispatch(req);
    }

    public void dispatch(Request req) {
        Preconditions.checkArgument(req != null);
        LogUtil.debug("POP3Server dispatch: " + req);

        this.multiPersistentRequestQueues.enQueue(req.getMailUser().getPop3Host(), req);
    }

    public void finish(String pop3Host, Response res) {
        LogUtil.debug("POP3Server finish: " + res.getMsg());
        if (!Response.isAsync(res)) {
            try {
                this.serverListener.sendResponse(res);
            } catch (IOException e) {
                LogUtil.error(LOG, e);
            }
            this.counter.dec(pop3Host);
        }

        this.scheduler.remove(pop3Host);
    }

    @Override
    public void run() {
        this.start();
    }
}
