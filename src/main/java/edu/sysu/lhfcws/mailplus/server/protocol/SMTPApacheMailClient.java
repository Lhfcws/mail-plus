package edu.sysu.lhfcws.mailplus.server.protocol;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * Apache mail client for test.
 *
 * @author lhfcws
 * @time 14-10-21.
 */
public class SMTPApacheMailClient implements SMTPClient {
    private static Log LOG = LogFactory.getLog(SMTPApacheMailClient.class);
    private org.apache.commons.mail.Email emailSender;
    private Email email;
    private MailUser mailUser;

    public SMTPApacheMailClient(Email email, MailUser mailUser) {
        Preconditions.checkArgument(email != null);

        this.email = email;
        this.mailUser = mailUser;
        if (email.hasAttachment())
            this.emailSender = new MultiPartEmail();
        else
            this.emailSender = new SimpleEmail();
    }

    @Override
    public boolean send() {
        try {
            emailSender.setFrom(email.getFrom());
            emailSender.setTo(email.getTo());
            emailSender.setCc(email.getCc());
            emailSender.setAuthentication(mailUser.getMailAddr(), mailUser.getPassword());

            if (email.hasAttachment()) {
                MultiPartEmail multiPartEmail = (MultiPartEmail) emailSender;
                for (Attachment a : email.getAttachments()) {
                    EmailAttachment emailAttachment = new EmailAttachment();
                    emailAttachment.setPath(a.getFilepath());
                    multiPartEmail.attach(emailAttachment);
                }
                multiPartEmail.send();
            } else {
                emailSender.send();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}
