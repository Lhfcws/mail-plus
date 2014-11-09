package edu.sysu.lhfcws.mailplus.server.protocol;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.ProtocolConsts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;

import javax.mail.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author lhfcws
 * @time 14-10-28.
 */
public class POP3JavaMailClient implements POP3Client {

    private Properties props;
    private Request req;
    private CommonSocket socket;

    public POP3JavaMailClient(Request req) {
        this.req = req;
        this.socket = new CommonSocket();
        this.props = new Properties();
        this.props.setProperty("mail.store.protocol", "pop3");
        this.props.setProperty("mail.pop3.host", req.getMailUser().getPop3Host());
    }

    @Override
    public List<Email> receiveLatest(int latestMailID) throws Exception {
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        store.connect(this.req.getMailUser().getMailAddr(), this.req.getMailUser().getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Message[] messages = folder.getMessages();
        List<Email> list = new LinkedList<Email>();
        for (Message msg : messages) {
            if (msg.getMessageNumber() > latestMailID) {
                Email email = new Email();
                email.setMailID(msg.getMessageNumber());
                email.setFrom(parseAddress(msg.getFrom()).get(0));
                email.setTo(parseAddress(msg.getRecipients(Message.RecipientType.TO)));
                email.setCc(parseAddress(msg.getRecipients(Message.RecipientType.CC)));
                email.setSubject(msg.getSubject());
                email.setContent(msg.getContent().toString());
                email.setDate(msg.getSentDate());
                email.setStatus(Email.EmailStatus.UNREAD);

                list.add(email);
            }
        }

        folder.close(true);
        store.close();

        return list;
    }

    @Override
    public Email receive(int mailID) throws Exception {
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        store.connect(this.req.getMailUser().getMailAddr(), this.req.getMailUser().getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Email email = new Email();
        Message msg = folder.getMessage(mailID);

        email.setMailID(msg.getMessageNumber());
        email.setFrom(parseAddress(msg.getFrom()).get(0));
        email.setTo(parseAddress(msg.getRecipients(Message.RecipientType.TO)));
        email.setCc(parseAddress(msg.getRecipients(Message.RecipientType.CC)));
        email.setSubject(msg.getSubject());
        email.setContent(msg.getContent().toString());
        email.setDate(msg.getSentDate());
        email.setStatus(Email.EmailStatus.READED);

        folder.close(true);
        store.close();

        return email;
    }

    @Override
    public void delete(int mailID) throws Exception {
        socket.connect(req.getMailUser().getPop3Host(), ProtocolConsts.POP3_PORT);
        response();

        socket.send(CommonUtil.protocolStr(
                ProtocolConsts.USER, req.getMailUser().getMailAddr()
        ));
        response();

        socket.send(CommonUtil.protocolStr(
                ProtocolConsts.PASS, req.getMailUser().getPassword()
        ));
        response();

        socket.send(CommonUtil.protocolStr(
                ProtocolConsts.DELE, String.valueOf(mailID)
        ));
        response();

        socket.close();

        Thread.sleep(5000);
    }

    // =======Utils
    private List<String> parseAddress(Address[] addresses) {
        List<String> list = new LinkedList<String>();

        if (addresses != null)
            for (Address address : addresses) {
                list.add(address.toString());
            }

        return list;
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
}
