package client.stub;

import client.preparation.TestPreparation;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.server.protocol.POP3JavaMailClient;

import java.util.List;

/**
 * @author lhfcws
 * @time 14-11-12.
 */
public class POP3ClientStub {

    public POP3ClientStub() {
    }

    public void start() {

        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() throws Exception {
        ReceiveRequest req = new ReceiveRequest();
        req.setMailUser(new TestPreparation().prepareMailUser());
        req.generateAuthCode();
        req.setMailID(2);
        POP3JavaMailClient client = new POP3JavaMailClient(req);
        Email email = client.receive(req.getMailID());
        System.out.println(email);
    }

    public void testList() throws Exception {
        ReceiveRequest req = new ReceiveRequest();
        req.setMailUser(new TestPreparation().prepareMailUser());
        req.generateAuthCode();
        POP3JavaMailClient client = new POP3JavaMailClient(req);
        List<Email> list = client.receiveLatest(req.getMailID());
        System.out.println(list.size());
    }

    public static void main(String[] args) {
        new POP3ClientStub().start();
    }
}
