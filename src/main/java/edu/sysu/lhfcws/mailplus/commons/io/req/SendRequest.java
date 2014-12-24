package edu.sysu.lhfcws.mailplus.commons.io.req;

import edu.sysu.lhfcws.mailplus.commons.model.Email;

/**
 * Request to send emails.
 * @author lhfcws
 * @time 14-10-23.
 */
public class SendRequest extends Request {

    private Email email;

    public SendRequest() {
        super(RequestType.SEND);
    }

    public SendRequest(Email email) {
        super(RequestType.SEND);
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SendRequest{" +
                "email=" + email +
                "} " + super.toString();
    }
}
