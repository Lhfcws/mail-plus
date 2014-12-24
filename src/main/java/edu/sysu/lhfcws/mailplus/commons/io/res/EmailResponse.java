package edu.sysu.lhfcws.mailplus.commons.io.res;

import edu.sysu.lhfcws.mailplus.commons.model.Email;

import java.util.LinkedList;
import java.util.List;

/**
 * Response that carries emails.
 * @author lhfcws
 * @time 14-10-23.
 */
public class EmailResponse extends Response {

    private List<Email> emails;

    public EmailResponse() {
        super();
        this.resID = -1;
        this.emails = new LinkedList<Email>();
        this.setResponseType(ResponseType.EMAIL);
    }

    public EmailResponse(Response response) {
        this();
        this.resID = response.resID;
        this.authCode = response.getAuthCode();
        this.status = response.status;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public void addEmail(Email email) {
        this.emails.add(email);
    }

    @Override
    public String toString() {
        return "EmailResponse{" +
                "emails=" + emails +
                "} " + super.toString();
    }
}
