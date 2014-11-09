package client.preparation;

import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class MailPlusTest {
    protected TestPreparation preparation;

    public void prepareTest() {
        preparation = new TestPreparation();
    }

    public MailPlusTest() {
        prepareTest();
    }
}
