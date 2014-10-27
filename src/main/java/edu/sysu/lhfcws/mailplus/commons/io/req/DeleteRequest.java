package edu.sysu.lhfcws.mailplus.commons.io.req;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class DeleteRequest extends Request {

    private String mailID;
    private DeleteRequestType deleteRequestType;

    public DeleteRequest() {
        super(RequestType.DELETE);
        this.deleteRequestType = DeleteRequestType.DELETE_ONE;
    }

    public DeleteRequest(String mailID) {
        super(RequestType.DELETE);
        this.mailID = mailID;
        this.deleteRequestType = DeleteRequestType.DELETE_ONE;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
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
                "mailID='" + mailID + '\'' +
                ", mailUser=" + mailUser +
                '}';
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
