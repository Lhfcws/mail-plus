package edu.sysu.lhfcws.mailplus.commons.io.res;

import edu.sysu.lhfcws.mailplus.commons.models.Email;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class EmailResponse extends Response {

    private Email email;

    public EmailResponse() {
        super();
        this.resID = -1;
        this.email = null;
    }

    public EmailResponse(Response response) {
        this();
        this.resID = response.resID;
        this.authCode = response.getAuthCode();
        this.status = response.status;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailResponse{" +
                "email=" + email +
                '}';
    }
}
