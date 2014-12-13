package edu.sysu.lhfcws.mailplus.commons.model;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.validate.PatternValidater;
import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Model to describe a email object.
 *
 * @author lhfcws
 * @time 14-10-21.
 */
public class Email implements Serializable {

    private int id;     // id is identified by local db.
    private int mailID; // MailID is identified by mail server, it's not stable.
    private String from;
    private List<String> to;
    private List<String> cc;
    private String subject;
    private String content;
    private Date date;
    private List<Attachment> attachments;
    private EmailType emailType;
    private String encoding;
    private EmailStatus status;
    private String signature;


    public Email() {
        this.id = -1;
        this.to = new LinkedList<String>();
        this.cc = new LinkedList<String>();
        this.attachments = new LinkedList<Attachment>();
        this.emailType = EmailType.PLAIN;
        this.encoding = "utf-8";
    }

    public static Email clone(Email email) {
        String json = CommonUtil.GSON.toJson(email);
        return CommonUtil.GSON.fromJson(json, Email.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
//        Preconditions.checkArgument(PatternValidater.validateMailAddress(from));

        this.from = from;
    }

    public String getFromString() {
        return this.from + ";";
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void addTo(String toAddr) {
//        Preconditions.checkArgument(PatternValidater.validateMailAddress(toAddr));
        Preconditions.checkArgument(this.to != null);

        this.to.add(toAddr);
    }

    public String getToString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.to) {
            sb.append(s).append(";");
        }
        return sb.toString();
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void addCc(String ccAddr) {
//        Preconditions.checkArgument(PatternValidater.validateMailAddress(ccAddr));
        Preconditions.checkArgument(this.cc != null);

        this.cc.add(ccAddr);
    }

    public String getCcString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.cc) {
            sb.append(s).append(";");
        }
        return sb.toString();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean hasAttachment() {
        return !this.attachments.isEmpty();
    }

    public int getMailID() {
        return mailID;
    }

    public void setMailID(int mailID) {
        this.mailID = mailID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBoundary() {
        return "===" + Base64.encodeBase64String(Consts.RESET_AUTH_CODE.getBytes()) + "===";
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", mailID=" + mailID +
                ", from='" + from + '\'' +
                ", to=" + to +
                ", cc=" + cc +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", attachments=" + attachments +
                ", emailType=" + emailType +
                ", encoding='" + encoding + '\'' +
                ", status=" + status +
                ", signature='" + signature + '\'' +
                '}';
    }

    // ==== EmailType
    public static enum EmailType {

        PLAIN("plain"), HTML("html");

        private String value;

        private EmailType(String v) {
            this.value = v;
        }

        public String getValue() {
            return this.value;
        }
    }

    // ==== EmailStatus
    public static enum EmailStatus {
        ERROR(-2), DELETED(-1),
        UNREAD(0), READED(1),   // Received emails
        WRITINIG(2), DRAFT(3), SENDING(4), SENDED(5);   // Written emails

        private int value;

        private EmailStatus(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

        public static EmailStatus fromValue(int v) {
            switch (v) {
                case 0:
                    return UNREAD;
                case 1:
                    return READED;
                case 2:
                    return WRITINIG;
                case 3:
                    return DRAFT;
                case 4:
                    return SENDING;
                case 5:
                    return SENDED;
                default:
                    return ERROR;
            }
        }
    }
}
