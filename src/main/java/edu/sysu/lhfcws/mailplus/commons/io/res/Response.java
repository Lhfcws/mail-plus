package edu.sysu.lhfcws.mailplus.commons.io.res;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.auth.AuthObject;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class Response extends AuthObject {

    protected long resID;     // equals to reqID if it is a requested response.
    protected ResponseStatus status;
    protected ResponseType responseType;
    protected String msg;

    public Response() {
        this.msg = "";
        this.setResponseType(ResponseType.COMMON);
    }

    public Response(ResponseStatus status, String authCode) {
        this();
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return "Response{" +
                "resID=" + resID +
                ", status=" + status +
                ", responseType=" + responseType +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }

    public static boolean isAsync(Response res) {
        return res.status == ResponseStatus.WAITING;
    }

    public static Response deserialize(String json) {
        Gson gson = new Gson();
        Response rawRes = gson.fromJson(json, Response.class);
        if (rawRes.getResponseType().equals(ResponseType.EMAIL))
            return gson.fromJson(json, EmailResponse.class);

        return rawRes;
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

    /**
     * Response type.
     */
    public static enum ResponseType {
        COMMON(0), EMAIL(1);

        private int value;
        private ResponseType(int value) {
            this.value = value;
        }
    }
}
