package edu.sysu.lhfcws.mailplus.client.util;

import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.StringTruncator;


/**
 * @author lhfcws
 * @time 14-10-29.
 */
public class EmailContentHTML {
    private Email email = null;

    public EmailContentHTML(Email email) {
        this.email = email;
    }

    public EmailContentHTML() {
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String toHTML() {
        if (email == null)
            return null;

        String template = TemplateLoader.getInstance().get(ClientConsts.EMAIL_CONTENT_TEMPLATE);
        return String.format(template,
                email.getFrom(), email.getDate(),
                email.getSubject(), email.getContent());
    }

    public String toListItemHTML() {
        if (email == null)
            return null;

        String template = TemplateLoader.getInstance().get(ClientConsts.LIST_ITEM_TEMPLATE);
        try {
            return String.format(template,
                    email.getFrom(), email.getDate(),
                    StringTruncator.truncate(email.getSubject(), 30),
                    StringTruncator.truncate(email.getContent(), 60));
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(template,
                    email.getFrom(), email.getDate(),
                    email.getSubject(), email.getContent());
        }
    }
}
