package edu.sysu.lhfcws.mailplus.server.protocol;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public interface SMTPClient {
    /**
     * Send email api.
     * @return boolean suuccess or not
     */
    public boolean send() throws Exception;
}
