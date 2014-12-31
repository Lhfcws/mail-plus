package unittest;

import client.preparation.MailPlusStub;
import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.RequestFactory;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.commons.queue.MultiPersistentRequestQueues;
import edu.sysu.lhfcws.mailplus.commons.queue.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.protocol.SMTPProtocolClient;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-12-30.
 */
public class ServerUnitTest {
    private MailPlusStub mailPlusStub;
    private Email email;
    private MailUser mailUser;

    @Before
    public void before() {
        this.mailPlusStub = new MailPlusStub();
        this.mailPlusStub.start();
        this.email = this.mailPlusStub.preparation.prepareEmail();
        this.mailUser = this.mailPlusStub.preparation.prepareMailUser();
    }

    @Test
    public void smtpClientTest() throws Exception {
        SMTPProtocolClient smtpProtocolClient = new SMTPProtocolClient(
                this.email, this.mailUser
        );
        smtpProtocolClient.send();
    }

    @Test
    public void requestQueueTest() {
        Gson gson = new Gson();
        ReceiveRequest req = RequestFactory.createLatestReceiveRequest();

        PersistentRequestQueue rq = RQCenter.getRQ("__testRQ");
        rq.enQueue(req);
        Request r = rq.deQueue();
        LogUtil.debug("" + r);
    }

    @Test
    public void multiRequestQueueTest() throws InterruptedException {
        Gson gson = new Gson();
        ReceiveRequest req = RequestFactory.createLatestReceiveRequest();


        MultiPersistentRequestQueues mrq = RQCenter.getMultiRQ("__testMRQ");
        String key = "test";
        assert mrq.availableKeys().size() == 0;
        mrq.enQueue(key, req);
        Thread.sleep(100);
        assert mrq.availableKeys().size() == 1;
        assert mrq.availableKeys().get(0).equals(key);

        ReceiveRequest req_ = (ReceiveRequest) mrq.deQueue(key);
        String auth1 = req.generateAuthCode();
        String auth2 = req_.generateAuthCode();
        assert auth1.equals(auth2);
    }

    @Test
    public void EmailSQLTest() throws SQLException {
        SQLite.getDao().batchExecute("DELETE FROM email WHERE status=3");
        EmailController emailController = new EmailController();
        email.setId(-1);
        emailController.saveDraft(email);
        assert email.getId() != -1;

        Email e = emailController.getEmailByID(501);
        assert e != null;
    }
}
