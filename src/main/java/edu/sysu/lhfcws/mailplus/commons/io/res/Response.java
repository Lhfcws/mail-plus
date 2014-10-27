package edu.sysu.lhfcws.mailplus.commons.io.res;

import edu.sysu.lhfcws.mailplus.commons.auth.AuthObject;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class Response extends AuthObject {

    protected long resID;     // equals to reqID if it is a requested response.
    protected ResponseStatus status;

    public Response() {
    }

    public Response(ResponseStatus status, String authCode) {
        this.authCode = authCode;
        this.status = status;
        this.resID = -1;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public long getResID() {
        return resID;
    }

    public void setResID(long resID) {
        this.resID = resID;
    }

    @Override
    public String toString() {
        return "Response{" +
                "resID=" + resID +
                ", status=" + status +
                '}';
    }

    public static boolean isAsync(Response res) {
        return res.status == ResponseStatus.WAITING;
    }

    /**
     * Response status.
     */
    public static enum ResponseStatus {
        SUCCEED(0), UNKNOWN_REQUEST(1), FAIL(-1), WAITING(2);

        private int value;
        private ResponseStatus(int value) {
            this.value = value;
        }
    }
}
