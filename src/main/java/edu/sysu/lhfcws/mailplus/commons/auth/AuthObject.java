package edu.sysu.lhfcws.mailplus.commons.auth;

import java.io.Serializable;

/**
 * Abstract class for objects that needs authentication.
 * @author lhfcws
 * @time 14-10-22.
 */
public abstract class AuthObject implements Serializable {

    protected String authCode = null;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void generateAuthCode() {
        this.authCode = new AuthCodeGenerator().generateCode(this);
    }

    @Override
    /**
     * This should be overrided in children classes.
     */
    public String toString() {
        return "AuthObject{" +
                "authCode='" + authCode + '\'' +
                '}';
    }
}
