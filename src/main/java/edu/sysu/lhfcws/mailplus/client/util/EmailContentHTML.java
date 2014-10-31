package edu.sysu.lhfcws.mailplus.client.util;

import edu.sysu.lhfcws.mailplus.commons.model.Email;


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

    public String toListItem() {
        if (email == null)
            return null;

        String template = TemplateLoader.getInstance().get(ClientConsts.LIST_ITEM_TEMPLATE);
        return String.format(template,
                email.getFrom(), email.getDate(),
                email.getSubject(), email.getContent());
    }
}
