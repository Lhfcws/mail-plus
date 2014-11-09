package edu.sysu.lhfcws.mailplus.server.protocol;

import edu.sysu.lhfcws.mailplus.commons.model.Email;

import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public interface POP3Client {

    /**
     * Receive mails from remote POP3 server.
     * @return
     */
    public List<Email> receiveLatest(int latestMailID) throws Exception;

    /**
     * Receive the specific mail by mailID from remote POP3 server.
     * @param mailID
     * @return
     */
    public Email receive(int mailID) throws Exception;

    /**
     * Delete a mail on the remote server by a given mailID.
     * @param mailID
     * @return
     */
    public void delete(int mailID) throws Exception;
}
