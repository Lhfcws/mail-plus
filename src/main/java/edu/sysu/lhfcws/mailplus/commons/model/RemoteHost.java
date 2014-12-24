package edu.sysu.lhfcws.mailplus.commons.model;

import java.io.Serializable;

/**
 * Remote host information model.
 * @author lhfcws
 * @time 14-10-21.
 */
public class RemoteHost implements Serializable {

    private long id;
    private String smtpHost;
    private String imapHost;
    private String pop3Host;

    public RemoteHost() {
        this.id = -1;
    }

    public RemoteHost(long id, String smtpHost, String imapHost, String pop3Host) {
        this();
        this.id = id;
        this.smtpHost = smtpHost;
        this.imapHost = imapHost;
        this.pop3Host = pop3Host;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public void setPop3Host(String pop3Host) {
        this.pop3Host = pop3Host;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getImapHost() {
        return imapHost;
    }

    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    @Override
    public String toString() {
        return "ExternalMailHost{" +
                "id=" + id +
                ", smtpHost='" + smtpHost + '\'' +
                ", imapHost='" + imapHost + '\'' +
                ", pop3Host='" + pop3Host + '\'' +
                '}';
    }
}
