package edu.sysu.lhfcws.mailplus.server.serv;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDBCounter;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.ThreadMonitor;
import edu.sysu.lhfcws.mailplus.server.serv.execute.SMTPRQsWatcher;
import edu.sysu.lhfcws.mailplus.server.serv.execute.SRQWatcher;
import edu.sysu.lhfcws.mailplus.server.serv.execute.SendExecutor;
import edu.sysu.lhfcws.mailplus.server.serv.execute.ServerListener;
import edu.sysu.lhfcws.mailplus.server.util.SMTPRequestQueues;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * As sending emails may not stable or fail, we need a SendServer to schedule the sending activities.
 * <ul>Sending steps:
 * <li>RequestHandler</li>
 * <li>SendServer</li>
 * <li>put request into a queue</li>
 * <li>start thread according to req from queue</li>
 * <li>thread invoke smtpsender to send email</li>
 * <li>if stuck then reput the request back to the queue, sleep for 5s</li>
 * <li>return the response</li>
 * </ul>
 *
 * @author lhfcws
 * @time 14-10-24.
 */
public class SendServer {
    // key: smtpHost ; value: execute thread
    private ConcurrentHashMap<String, AdvRunnable> scheduler;
    private ExecutorService threadPool;
    private BDBCounter counter;
    private ThreadMonitor threadMonitor;
    private ServerListener serverListener;
    private SMTPRequestQueues smtpRequestQueues;

    private static final String SRQ_WATCHER = "SRQWatcher";
    private static final String SMTP_RQs_WATCHER = "SMTPRQsWatcher";



    public SendServer(ServerListener serverListener) {
        this.serverListener = serverListener;
        this.smtpRequestQueues = new SMTPRequestQueues();
        this.scheduler = new ConcurrentHashMap<String, AdvRunnable>();

        this.threadMonitor = new ThreadMonitor();
        this.threadMonitor.register(new SRQWatcher(SRQ_WATCHER, this));
        this.threadMonitor.register(new SMTPRQsWatcher(SMTP_RQs_WATCHER, this));

        this.threadPool = Executors.newCachedThreadPool();
        this.counter = new BDBCounter(Consts.BDB_PATH + "activeSmtpCounter");
    }

    /**
     * Start SendServer.
     */
    public void start() {
        this.threadMonitor.start();
        this.threadMonitor.monitor();
    }

    /**
     * This function will schedule when and how to send the emails.
     * @param req
     */
    public void send(SendRequest req) {
        String smtpHost = req.getMailUser().getSmtpHost();
        this.counter.inc(smtpHost);

        if (scheduler.containsKey(smtpHost)) {
            // put into corresponding smtp queue
            this.smtpRequestQueues.enQueue(req);
        } else {
            SendExecutor sendExecutor = new SendExecutor(smtpHost, this);
            this.scheduler.put(smtpHost, sendExecutor);
            // start the sending thread
            this.threadPool.execute(sendExecutor);
        }
    }

    /**
     * Callback function no matter the sending is successful or not.
     * @param smtpHost
     * @param res
     */
    public void finishSend(String smtpHost, Response res) {
        if (!Response.isAsync(res)) {
            try {
                this.serverListener.sendResponse(res);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.counter.dec(smtpHost);
        }

        this.scheduler.remove(smtpHost);
    }

    /**
     * Push SendRequest back to the smtp RQ, as SMTP servers are not so stable.
     * We have to try several times.
     * @param req
     */
    public void repushRequest(SendRequest req) {
        this.smtpRequestQueues.nap(req.getMailUser().getSmtpHost());
        this.dispatch(req);
    }

    /**
     * Dispatch SendRequest to SMTPRequestQueues
     * @param req
     */
    public void dispatch(SendRequest req) {
        this.smtpRequestQueues.enQueue(req);
    }
}
