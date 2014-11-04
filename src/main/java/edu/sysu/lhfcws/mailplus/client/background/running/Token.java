package edu.sysu.lhfcws.mailplus.client.background.running;


/**
 * @author lhfcws
 * @time 14-11-1.
 */
public class Token {
    private String email;
    private String password;
    private long timestamp;

    public Token(String email, String password, long timestamp) {
        this.email = email;
        this.password = password;
        this.timestamp = timestamp;
    }

    public Token() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
