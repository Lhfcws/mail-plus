package edu.sysu.lhfcws.mailplus.commons.model;


import java.io.Serializable;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class MailUser implements Serializable {

    private long id;
    private String mailAddr;
    private String smtpHost;
    private String pop3Host;
    private String imapHost;
    private String nickName;
    private String password;

    public MailUser() {
        this.id = -1;
    }

    public MailUser(long id, String mailAddr, String smtpHost, String pop3Host,
                    String imapHost, String nickName, String password) {
        this();
        this.id = id;
        this.mailAddr = mailAddr;
        this.smtpHost = smtpHost;
        this.pop3Host = pop3Host;
        this.imapHost = imapHost;
        this.nickName = nickName;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMailAddr() {
        return mailAddr;
    }

    public void setMailAddr(String mailAddr) {
        this.mailAddr = mailAddr;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public void setPop3Host(String pop3Host) {
        this.pop3Host = pop3Host;
    }

    public String getImapHost() {
        return imapHost;
    }

    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    public boolean hasNoSMTP() {
        return this.getSmtpHost() == null;
    }

    public void setRemoteHost(RemoteHost remoteHost) {
        this.setImapHost(remoteHost.getImapHost());
        this.setPop3Host(remoteHost.getPop3Host());
        this.setSmtpHost(remoteHost.getSmtpHost());
    }

    @Override
    public String toString() {
        return "MailUser{" +
                "id=" + id +
                ", mailAddr='" + mailAddr + '\'' +
                ", smtpHost='" + smtpHost + '\'' +
                ", pop3Host='" + pop3Host + '\'' +
                ", imapHost='" + imapHost + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
