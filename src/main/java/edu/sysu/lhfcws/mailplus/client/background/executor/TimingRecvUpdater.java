package edu.sysu.lhfcws.mailplus.client.background.executor;

import edu.sysu.lhfcws.mailplus.client.background.running.MailOperator;
import edu.sysu.lhfcws.mailplus.client.background.running.Token;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.NewMailResponseCallback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;

/**
 * Remind the RecvServer to receive new mails on a period.
 * @author lhfcws
 * @time 14-10-23.
 */
public class TimingRecvUpdater extends AdvRunnable {
    private static Log LOG = LogFactory.getLog(TimingRecvUpdater.class);

    public static final String NAME = "TimingRecvUpdater";
    public static final int UPDATE_INTERVAL = 20 * 1000;    // 20s

    public TimingRecvUpdater() {
        super(NAME);
    }


    @Override
    public void run() {
        MailOperator mailOperator = new MailOperator();
        Token token = MainWindow.getInstance().getToken();

        while (true) {
            mailOperator.receiveLatest(token, new NewMailResponseCallback());

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                LogUtil.error(LOG, e);
            }
        }
    }
}
