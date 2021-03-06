package edu.sysu.lhfcws.mailplus.server.protocol;

import edu.sysu.lhfcws.mailplus.commons.base.ProtocolConsts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.codec.binary.Base64;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.ParseException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * POP3 client JavaMail implement.
 *
 * @author lhfcws
 * @time 14-10-28.
 */
public class POP3JavaMailClient implements POP3Client {

    private Properties props;
    private Request req;
    private CommonSocket socket;
    private String signature;

    public POP3JavaMailClient(Request req) {
        this.req = req;
        this.signature = req.getMailUser().getMailAddr();
        this.socket = new CommonSocket();
        this.props = new Properties();
        this.props.setProperty("mail.store.protocol", "pop3");
        this.props.setProperty("mail.pop3.socketFactory.fallback", "false");
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
            Email email = new Email();
            try {
                if (msg.getMessageNumber() > latestMailID && !msg.isSet(Flags.Flag.DELETED)) {
                    email.setMailID(msg.getMessageNumber());
                    email.setFrom(parseAddress(msg.getFrom()).get(0));
                    email.setTo(parseAddress(msg.getRecipients(Message.RecipientType.TO)));
                    email.setCc(parseAddress(msg.getRecipients(Message.RecipientType.CC)));
                    email.setSubject(msg.getSubject());
                    email.setDate(msg.getSentDate());
                    email.setSignature(this.signature);
                    email.setStatus(Email.EmailStatus.UNREAD);
                    parseBody(email, msg);

                    list.add(email);
                }
            } catch (MessageRemovedException e) {
                // 138
                System.out.println("[ERROR] Removed:" + msg.getMessageNumber());
            } catch (UnsupportedEncodingException e) {
                // 265
                // 2488
                // 2701
                System.out.println("[ERROR] Encoded:" + msg.getMessageNumber());
                email.setContent(CommonUtil.inputStream2String(msg.getInputStream()));
                list.add(email);
            } catch (AddressException e) {
                // 2439
                System.out.println("[ERROR] Address:" + msg.getMessageNumber() + ": " + e.getMessage());
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
        if (msg.isExpunged()) return null;

        try {
            email.setMailID(msg.getMessageNumber());
            email.setFrom(parseAddress(msg.getFrom()).get(0));
            email.setTo(parseAddress(msg.getRecipients(Message.RecipientType.TO)));
            email.setCc(parseAddress(msg.getRecipients(Message.RecipientType.CC)));
            email.setSubject(msg.getSubject());
            email.setDate(msg.getSentDate());
            email.setSignature(this.signature);
            email.setStatus(Email.EmailStatus.READED);
            parseBody(email, msg);
        } catch (UnsupportedEncodingException e) {
            email.setContent(CommonUtil.inputStream2String(msg.getInputStream()));
        }

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
                String addr = address.toString();
                try {
                    addr = CommonUtil.parseAddressName(addr);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                list.add(addr);
            }

        return list;
    }

    private Email parseBody(Email email, Message message) throws MessagingException, IOException {
        if (message.isMimeType("multipart/*")) {
            BodyPart part;
            String disposition;
            StringBuilder sb = new StringBuilder();

            Multipart mp = (Multipart) message.getContent();
            int mpCount = mp.getCount();

            for (int m = 0; m < mpCount; m++) {
                part = mp.getBodyPart(m);
                try {
                    disposition = part.getDisposition();
                } catch (ParseException e) {
                    continue;
                }

                if (disposition != null) {
                    Attachment attachment = new Attachment();
                    String s = part.getFileName();
                    try {
                        s = s.substring(8, s.indexOf("?="));
                    } catch (StringIndexOutOfBoundsException e) {
                        if (s.startsWith("=?")) {
                            s = s.substring(3, s.length() - 2);
                            String encode = s.substring(0, s.indexOf("Q?"));
                            s = s.substring(encode.length() + 2);
                        }
                    }
                    if (s.endsWith("="))
                        s = new String(Base64.decodeBase64(s));
                    attachment.setFilename(s);
                    byte[] content = CommonUtil.inputStream2Bytes(part.getInputStream());
                    attachment.setContent(content);

                    email.addAttachment(attachment);
                } else
                    sb.append(CommonUtil.toUTF8(part.getContent().toString())).append("\n");
            }

            email.setContent(sb.toString());
        } else if (message.isMimeType("text/*")) {
            email.setContent((String) message.getContent());
        }

        return email;
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
