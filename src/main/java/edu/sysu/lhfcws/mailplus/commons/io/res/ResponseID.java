package edu.sysu.lhfcws.mailplus.commons.io.res;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class ResponseID {
    private long resID;
    private String authCode;

    public ResponseID() {
    }

    public ResponseID(long resID, String authCode) {
        this.resID = resID;
        this.authCode = authCode;
    }

    public ResponseID(Response response) {
        this.resID = response.getResID();
        this.authCode = response.getAuthCode();
    }

    public ResponseID(Request req) {
        this.resID = req.getReqID();
        this.authCode = req.getAuthCode();
    }

    public long getResID() {
        return resID;
    }

    public void setResID(long resID) {
        this.resID = resID;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseID)) return false;

        ResponseID that = (ResponseID) o;

        if (resID != that.resID) return false;
        if (!authCode.equals(that.authCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (resID ^ (resID >>> 32));
        result = 31 * result + authCode.hashCode();
        return result;
    }
}
