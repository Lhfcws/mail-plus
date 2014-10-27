package edu.sysu.lhfcws.mailplus.commons.base;

import java.util.HashMap;

/**
 * Refer to <a href="http://tools.ietf.org/html/rfc821">RFC821</a>
 * @author lhfcws
 * @time 14-10-21.
 */
public class ProtocolConsts {

    public static final int SMTP_PORT = 25;
    public static final int POP3_PORT = 110;
    public static final int IMAP_PORT = 143;

    public static final int SMTP_STUCK_INTERVAL = 5000; // 5000ms

    public static HashMap<Integer, String> RETURN_CODE = new HashMap<Integer, String>();
    public static final String HELO = "HELO";
    public static final String MAIL = "MAIL";
    public static final String RCPT = "RCPT";
    public static final String RETR = "RETR";
    public static final String SEND = "SEND";
    public static final String USER = "USER";
    public static final String PASS = "PASS";
    public static final String STAT = "STAT";
    public static final String LIST = "LIST";
    public static final String RSET = "RSET";
    public static final String DELE = "DELE";
    public static final String LAST = "LAST";
    public static final String EXIT = "EXIT";
    public static final String SAML = "SAML";
    public static final String SOML = "SOML";
    public static final String AUTH_LOGIN = "AUTH LOGIN";
    public static final String QUIT = "QUIT";
    public static final String DATA = "DATA";

    public static final String RET_OK = "+OK";



    static {
        RETURN_CODE.put(500, "Syntax error, command unrecognized");
        RETURN_CODE.put(501, "Syntax error in parameters or arguments");
        RETURN_CODE.put(502, "Command not implemented");
        RETURN_CODE.put(503, "Bad sequence of commands");
        RETURN_CODE.put(504, "Command parameter not implemented");
        RETURN_CODE.put(211, "System status, or system help reply");
        RETURN_CODE.put(214, "Help message");
        RETURN_CODE.put(220, "< domain > Service ready");
        RETURN_CODE.put(221, "< domain > Service closing transmission channel");
        RETURN_CODE.put(421, "< domain > Service not available, closing transmission channel");
        RETURN_CODE.put(250, "Requested mail action okay, completed");
        RETURN_CODE.put(251, "User not local; will forward to < forward-path >");
        RETURN_CODE.put(450, "Requested mail action not taken: mailbox unavailable [E.g., mailbox busy]");
        RETURN_CODE.put(550, "Requested action not taken:mailbox unavailable [E.g., mailbox not found, no access]");
        RETURN_CODE.put(451, "Requested action aborted: error in processing");
        RETURN_CODE.put(551, "User not local; please try < forward-path >");
        RETURN_CODE.put(452, "Requested action not taken:insufficient system storage");
        RETURN_CODE.put(552, "Requested mail action aborted:exceeded storage allocation");
        RETURN_CODE.put(553, "Requested action not taken:mailbox name not allowed [E.g., mailbox syntax incorrect]");
        RETURN_CODE.put(354, "Start mail input; end with <CRLF>.<CRLF>");
        RETURN_CODE.put(554, "Transaction failed");
    }
}
