package edu.sysu.lhfcws.mailplus.server.protocols;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.base.ProtocolConsts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import edu.sysu.lhfcws.mailplus.commons.models.MailUser;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * SMTP protocol implementation.
 *
 * @author lhfcws
 * @time 14-10-23.
 */
public class SMTPProtocolSender implements SMTPSender {

    private static Log LOG = LogFactory.getLog(SMTPProtocolSender.class);

    private Email email;
    private MailUser mailUser;
    private CommonSocket socket;

    public SMTPProtocolSender(Email email, MailUser mailUser) {
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
        socketSend(
                ProtocolConsts.DATA, ""
        );
        response();

        StringBuilder sb = new StringBuilder();

        socketSend(
                "", String.format("subject:%s", email.getTitle())
        );

        socketSend(
                "", String.format("from:<%s>", email.getFrom())
        );

        for (String receiver : email.getTo()) {
            socketSend(
                    "", String.format("to:<%s>", receiver)
            );
        }
//        for (String cc : email.getCc()) {
//            sb.append(CommonUtil.protocolStr(
//                    "", String.format("Cc:<%s>", cc)
//            ));
//        }

//        sb.append(String.format("Date:%s", getDateString()));
//        sb.append(Consts.CRLF);
        socketSend(
                "", String.format("Content-Type: text/plain;charset=\"utf-8\"")
        );

        socketSend(
                "", ""
        );

        socketSend(
                "", email.getContent()
        );

        socketSend(
                "", "."
        );

        socketSend(
                "", ""
        );

//        sb.append(String.format("%s.%s", Consts.CRLF, Consts.CRLF));

//        System.out.println(sb.toString());
//        socket.send(sb.toString());
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
