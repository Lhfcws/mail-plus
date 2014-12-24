package edu.sysu.lhfcws.mailplus.client.util;

import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.HTMLUtil;
import edu.sysu.lhfcws.mailplus.commons.util.StringTruncator;


/**
 * A simple proxy for transform a email to corresponding html.
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

    public String getEmailString() {
        return CommonUtil.GSON.toJson(email);
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
                HTMLUtil.rmTag(email.getFrom()), email.getDate(),
                HTMLUtil.rmTag(email.getSubject()), HTMLUtil.transSpChars(HTMLUtil.rmTag(email.getContent())));
    }

    public String toListItemHTML() {
        if (email == null)
            return null;

        String template = TemplateLoader.getInstance().get(ClientConsts.LIST_ITEM_TEMPLATE);
        try {
            return String.format(template,
                    HTMLUtil.rmTag(email.getFrom()), email.getDate(),
                    HTMLUtil.rmTag(StringTruncator.truncate(email.getSubject(), 30) + "..."));
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(template,
                    HTMLUtil.rmTag(email.getFrom()), email.getDate(),
                    HTMLUtil.rmTag(email.getSubject()));
        }
    }
}
