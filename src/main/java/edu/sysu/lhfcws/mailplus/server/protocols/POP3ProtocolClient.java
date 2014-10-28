package edu.sysu.lhfcws.mailplus.server.protocols;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.base.ProtocolConsts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class POP3ProtocolClient implements POP3Client {

    private static Log LOG = LogFactory.getLog(POP3ProtocolClient.class);
    private CommonSocket socket;
    private Request req;

    public POP3ProtocolClient(Request req) {
        this.socket = new CommonSocket();
        this.req = req;
    }

    @Override
    public List<Email> receiveLatest(String latestMailID) throws Exception {
        this.socket.connect(req.getMailUser().getPop3Host(), ProtocolConsts.POP3_PORT);

        login();
        List<Email> list = receiveLatestData(latestMailID);
        quit();

        return list;
    }

    @Override
    public Email receive(String mailID) throws Exception {
        this.socket.connect(req.getMailUser().getPop3Host(), ProtocolConsts.POP3_PORT);

        login();
        Email email = receiveData(mailID);
        quit();

        return email;
    }

    @Override
    public void delete(String mailID) throws Exception {
        this.socket.connect(req.getMailUser().getPop3Host(), ProtocolConsts.POP3_PORT);

        login();
        dele(mailID);
        quit();
    }


    // =========== Commands

    private void login() throws Exception {
        socketSend(
                ProtocolConsts.USER, req.getMailUser().getMailAddr()
        );
        response();

        socketSend(
                ProtocolConsts.PASS, req.getMailUser().getPassword()
        );
        response();
    }

    private void stat() throws Exception {
        socketSend(
                ProtocolConsts.STAT, ""
        );
        response();
    }

    private void dele(String mailID) throws Exception {
        socketSend(
                ProtocolConsts.DELE, mailID
        );
        response();
    }

    private List<Email> receiveLatestData(String latestMailID) throws Exception {
        socketSend(
                ProtocolConsts.LIST, ""
        );

        // Get mailIDs those needs to retrieve mails
        List<String> retrMailIDs = new LinkedList<String>();
        List<String> mailInfos = getResponseInLines();
        for (String mailInfo : mailInfos) {
            String[] mailInfoArr = mailInfo.split(" ");
            String mID = mailInfoArr[0];
            if (latestMailID == null || mID.compareTo(latestMailID) == 1) {
                retrMailIDs.add(mID);
            }
        }

        // Retrieve mails
        List<Email> result = new LinkedList<Email>();
        for (String mailID : retrMailIDs) {
            Email email = receiveData(mailID);
            result.add(email);
        }

        return result;
    }

    private Email receiveData(String mailID) throws Exception {
        socketSend(
                ProtocolConsts.RETR, mailID
        );
        response();

        List<String> buffer = getResponseInLines();
        return decodeProtocolToEmail(buffer);
    }

    private void quit() throws Exception {
        socketSend(
                ProtocolConsts.QUIT, ""
        );

        try {
            response();
        } catch (Exception e) {
            LOG.warn("POP3 Connection is finished, so quit error does not affect the result.");

            LOG.error(e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }

    // ========== Util

    private Email decodeProtocolToEmail(List<String> buffer) {
        return null;
    }

    /**
     * @throws java.io.IOException
     */
    private void response() throws Exception {
        String msg = this.getResponse();
        if (msg.startsWith("2") || msg.startsWith("3")
                || msg.startsWith(ProtocolConsts.RET_OK))
            return;

        throw new Exception(msg);
    }

    /**
     * Return response message.
     *
     * @return String reponse message
     * @throws Exception
     */
    private String getResponse() throws Exception {
        return this.socket.receive();
    }

    /**
     * Return response message in lines format.
     *
     * @return List<String> response message
     * @throws Exception
     */
    private List<String> getResponseInLines() throws Exception {
        String raw = this.getResponse();
        return Arrays.asList(raw.split(Consts.CRLF));
    }

    /**
     * Wrapper for socket.send in protocol format
     *
     * @param cmd
     * @param content
     * @throws java.io.IOException
     */
    private void socketSend(String cmd, String content) throws IOException {
        socket.send(CommonUtil.protocolStr(cmd, content));
    }
}
