package edu.sysu.lhfcws.mailplus.server.protocol;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.base.ProtocolConsts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SMTP protocol implementation.
 *
 * @author lhfcws
 * @time 14-10-23.
 */
public class SMTPProtocolClient implements SMTPClient {

    private static Log LOG = LogFactory.getLog(SMTPProtocolClient.class);

    private Email email;
    private MailUser mailUser;
    private CommonSocket socket;

    public SMTPProtocolClient(Email email, MailUser mailUser) {
        this.email = email;
        this.mailUser = mailUser;
        socket = new CommonSocket();
    }

    @Override
    public boolean send() throws Exception {
        this.socket.connect(
                this.mailUser.getSmtpHost(), ProtocolConsts.SMTP_PORT
//                    this.mailUser.getSmtpHost(), 52001
        );
        System.out.println("Connect smtp finished. smtp is " + this.mailUser.getSmtpHost());

        helo();
        authLogin();
        setUsers();
        setData();
        quit();
        return true;
    }

    private void helo() throws Exception {
        socketSend(
                ProtocolConsts.HELO, this.mailUser.getSmtpHost()
        );
        System.out.println("HELO sended.");
        response();
        System.out.println("HELO responsed.");
    }

    private void authLogin() throws Exception {
        socketSend(
                ProtocolConsts.AUTH_LOGIN, ""
        );
        response();

        socketSend(
                "", Base64.encodeBase64String(mailUser.getMailAddr().getBytes())
        );
        response();

        socketSend(
                "", Base64.encodeBase64String(mailUser.getPassword().getBytes())
        );
        response();
    }

    private void setUsers() throws Exception {
        socketSend(
                ProtocolConsts.MAIL, String.format("FROM:<%s>", email.getFrom())
        );
        response();

        for (String receiver : email.getTo()) {
            socketSend(
                    ProtocolConsts.RCPT, String.format("TO:<%s>", email.getFrom())
            );
            response();
        }
    }

    private void setData() throws Exception {
        StringBuilder sb;

        // BEGIN with DATA
        socketSend(
                ProtocolConsts.DATA, ""
        );
        response();

        // Send subject
        socketSend(
                "", String.format("subject:%s", email.getSubject())
        );

        // Send from
        socketSend(
                "", String.format("from:<%s>", email.getFrom())
        );

        // Send To
        sb = new StringBuilder("To:");
        for (int i = 0; i < email.getTo().size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append(email.getTo().get(i));
        }
        socketSend(
                "", sb.toString()
        );

        // Send cc
        sb = new StringBuilder("Cc:");
        for (int i = 0; i < email.getCc().size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append(email.getCc().get(i));
        }
        socketSend(
                "", sb.toString()
        );

        // Send date
        socketSend(
                "", String.format("Date:%s", getDateString())
        );

        // Send MIME-Version
        socketSend(
                "", "MIME-Version: 1.0"
        );

        // Send content-type
        socketSend(
                "", String.format("Content-Type: text/%s;charset=\"%s\"",
                        email.getEmailType(), email.getEncoding())
        );

        socketSend(
                "", String.format("boundary=\"%s\"", email.getBoundary())
        );

        // Send content
        socketSend(
                "", ""
        );

        socketSend(
                "", email.getContent()
        );

        // Send attachments
        if (email.hasAttachment()) {
            for (Attachment attachment : email.getAttachments()) {
                socketSend(
                        "", "--" + email.getBoundary()
                );

                socketSend(
                        "", attachment.generate()
                );

            }

            socketSend(
                    Consts.CRLF, "--" + email.getBoundary() + "--"
            );
        }

        // END
        socketSend(
                "", "."
        );

        socketSend(
                "", ""
        );

        // Response
        response();
    }

    private void quit() throws Exception {
        socketSend(
                ProtocolConsts.QUIT, ""
        );

        try {
            response();
        } catch (Exception e) {
            LOG.warn("SMTP Connection is finished, so quit error does not affect the result.");

            LOG.error(e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }

    // ========== Util

    /**
     * @throws IOException
     */
    private void response() throws Exception {
        String msg = socket.receive();
        if (msg.startsWith("2") || msg.startsWith("3"))
            return;

        throw new Exception(msg);
    }

    /**
     * Wrapper for socket.send in protocol format
     *
     * @param cmd
     * @param content
     * @throws IOException
     */
    private void socketSend(String cmd, String content) throws IOException {
        socket.send(CommonUtil.protocolStr(cmd, content));
        System.out.print(CommonUtil.protocolStr(cmd, content));
    }

    private String getDateString() {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z (z)", Locale.US).format(new Date());
    }
}
