package edu.sysu.lhfcws.mailplus.commons.io.req;

import edu.sysu.lhfcws.mailplus.commons.auth.AuthObject;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class Request extends AuthObject {

    protected long reqID;
    protected RequestType requestType;
    protected MailUser mailUser;
    protected boolean async;

    public Request() {
        super();
        this.reqID = -1;
        this.async = false;
    }

    public Request(RequestType requestType) {
        this();
        this.requestType = requestType;
        this.reqID = System.currentTimeMillis();
    }

    public Request(long reqID, RequestType requestType) {
        this();
        this.reqID = reqID;
        this.requestType = requestType;
    }

    public long getReqID() {
        return reqID;
    }

    public void setReqID(long reqID) {
        this.reqID = reqID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public MailUser getMailUser() {
        return mailUser;
    }

    public void setMailUser(MailUser mailUser) {
        this.mailUser = mailUser;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    public String toString() {
        return "Request{" +
                "reqID=" + reqID +
                ", requestType=" + requestType +
                ", mailUser=" + mailUser +
                ", async=" + async +
                '}';
    }

    /**
     * Request Type
     */
    public static enum RequestType {
        SEND(0), RECEIVE(1), DELETE(2);

        private int value;
        private RequestType(int v) {
            this.value = v;
        }
    }
}
