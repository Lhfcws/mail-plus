package edu.sysu.lhfcws.mailplus.server.protocols;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import edu.sysu.lhfcws.mailplus.commons.models.MailUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * The real object that sends the email.
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
                if (email.hasRemoteAttachment())
                    multiPartEmail.attach(email.getRemoteAttachment(),
                            email.getRemoteAttachment().getFile(),
                            email.getRemoteAttachment().toString());
                else {
                    EmailAttachment emailAttachment = new EmailAttachment();
                    emailAttachment.setPath(email.getLocalAttachment());
                    multiPartEmail.attach(emailAttachment);
                }
                multiPartEmail.send();
            }
            else {
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
