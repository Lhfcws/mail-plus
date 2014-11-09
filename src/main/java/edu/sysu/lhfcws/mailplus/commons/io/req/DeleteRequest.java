package edu.sysu.lhfcws.mailplus.commons.io.req;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class DeleteRequest extends Request {

    private int mailID;
    private DeleteRequestType deleteRequestType;

    public DeleteRequest() {
        super(RequestType.DELETE);
        this.deleteRequestType = DeleteRequestType.DELETE_ONE;
    }

    public DeleteRequest(int mailID) {
        super(RequestType.DELETE);
        this.mailID = mailID;
        this.deleteRequestType = DeleteRequestType.DELETE_ONE;
    }

    public int getMailID() {
        return mailID;
    }

    public void setMailID(int mailID) {
        this.mailID = mailID;
    }

    public DeleteRequestType getDeleteRequestType() {
        return deleteRequestType;
    }

    public void setDeleteRequestType(DeleteRequestType deleteRequestType) {
        this.deleteRequestType = deleteRequestType;
    }

    @Override
    public String toString() {
        return "DeleteRequest{" +
                "mailID=" + mailID +
                ", deleteRequestType=" + deleteRequestType +
                "} " + super.toString();
    }

    /**
     * DELETE_ONE: delete one mail according to mailID.
     * DELETE_ALL: delete all the mails of a mail user.
     */
    public static enum DeleteRequestType {
        DELETE_ONE(0), DELETE_ALL(1);

        private int value;
        private DeleteRequestType(int v) {
            this.value = v;
        }
    }
}
