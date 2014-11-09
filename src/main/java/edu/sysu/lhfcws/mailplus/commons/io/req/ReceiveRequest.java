package edu.sysu.lhfcws.mailplus.commons.io.req;

/**
 * Receive request.
 * @author lhfcws
 * @time 14-10-23.
 */
public class ReceiveRequest extends Request {

    private int mailID;
    private ReceiveRequestType receiveRequestType;

    public ReceiveRequest() {
        super(RequestType.RECEIVE);
        this.mailID = -1;
    }

    public int getMailID() {
        return mailID;
    }

    public void setMailID(int mailID) {
        this.mailID = mailID;
    }

    public ReceiveRequestType getReceiveRequestType() {
        return receiveRequestType;
    }

    public void setReceiveRequestType(ReceiveRequestType receiveRequestType) {
        this.receiveRequestType = receiveRequestType;
    }

    @Override
    public String toString() {
        return "ReceiveRequest{" +
                "mailID='" + mailID + '\'' +
                ", mailUser=" + mailUser +
                ", receiveRequestType=" + receiveRequestType +
                '}';
    }

    /**
     * MAIL: retrieve a mail according to mailID.
     * ALL: retrieve all the mails.
     * LATEST: retrieve newest unread mail. POP3's latest mailID refers to DB.
     */
    public static enum ReceiveRequestType {
        MAIL(0), ALL(1), LATEST(2);

        private int value;
        private ReceiveRequestType(int v) {
            this.value = v;
        }
    }
}
