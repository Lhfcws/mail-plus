package edu.sysu.lhfcws.mailplus.server.protocols;

import edu.sysu.lhfcws.mailplus.commons.models.Email;

import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public interface POP3Receiver {

    /**
     * Receive mails from remote POP3 server.
     * @return
     */
    public List<Email> receive();

    /**
     * Receive the specific mail by mailID from remote POP3 server.
     * @param mailID
     * @return
     */
    public Email receive(String mailID);
}
