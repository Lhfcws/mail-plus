package edu.sysu.lhfcws.mailplus.server.protocol;

import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

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

    public POP3JavaMailClient(Request req) {
        this.req = req;
        props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", req.getMailUser().getPop3Host());
    }

    @Override
    public List<Email> receiveLatest(String latestMailID) throws Exception {
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        store.connect(this.req.getMailUser().getMailAddr(), this.req.getMailUser().getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Message[] messages = folder.getMessages();
        List<Email> list = new LinkedList<Email>();
        for (Message msg : messages) {
            if (latestMailID == null ||
                    msg.getMessageNumber() > Integer.valueOf(latestMailID)) {
                Email email = new Email();
                email.setFrom(parseAddress(msg.getFrom()).get(0));
                email.setTo(parseAddress(msg.getRecipients(Message.RecipientType.TO)));
                email.setCc(parseAddress(msg.getRecipients(Message.RecipientType.CC)));
                email.setSubject(msg.getSubject());
                email.setContent(msg.getContent().toString());
                email.setDate(msg.getReceivedDate());

                list.add(email);
            }
        }

        folder.close(true);
        store.close();

        return list;
    }

    @Override
    public Email receive(String mailID) throws Exception {
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        store.connect(this.req.getMailUser().getMailAddr(), this.req.getMailUser().getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Email email = new Email();
        Message msg = folder.getMessage(Integer.valueOf(mailID));

        email.setFrom(parseAddress(msg.getFrom()).get(0));
        email.setTo(parseAddress(msg.getRecipients(Message.RecipientType.TO)));
        email.setCc(parseAddress(msg.getRecipients(Message.RecipientType.CC)));
        email.setSubject(msg.getSubject());
        email.setContent(msg.getContent().toString());
        email.setDate(msg.getReceivedDate());

        folder.close(true);
        store.close();

        return email;
    }

    @Override
    public void delete(String mailID) throws Exception {
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        store.connect(this.req.getMailUser().getMailAddr(), this.req.getMailUser().getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Message message = folder.getMessage(Integer.valueOf(mailID));
        message.setFlag(Flags.Flag.DELETED, true);
        message.saveChanges();

        folder.close(true);
        store.close();
    }

    // =======Utils
    private List<String> parseAddress(Address[] addresses) {
        List<String> list = new LinkedList<String>();

        for (Address address : addresses) {
            list.add(address.toString());
        }

        return list;
    }
}
