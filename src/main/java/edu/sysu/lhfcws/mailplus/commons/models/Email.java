package edu.sysu.lhfcws.mailplus.commons.models;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.validate.PatternValidater;

import java.io.Serializable;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Model to describe a email object.
 * @author lhfcws
 * @time 14-10-21.
 */
public class Email implements Serializable {

    private int id;     // id is identified by local db.
    private String mailID; // MailID is identified by mail server.
    private String from;
    private List<String> to;
    private List<String> cc;
    private String title;
    private String content;
    private String localAttachment;
    private URL remoteAttachment;


    /**
     * This constructor is for RPC.
     */
    public Email() {
        this.id = -1;
        this.to = new LinkedList<String>();
        this.cc = new LinkedList<String>();
        this.localAttachment = null;
        this.remoteAttachment = null;
    }

    public Email(int id, String from, List<String> to, List<String> cc, String title, String content) {
        this();
        this.id = id;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.title = title;
        this.content = content;
    }

    public Email(int id, String from, List<String> to, List<String> cc, String title, String content, String localAttachment) {
        this();
        this.id = id;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.title = title;
        this.content = content;
        this.localAttachment = localAttachment;
    }

    public Email(int id, String from, List<String> to, List<String> cc, String title, String content, URL remoteAttachment) {
        this();
        this.id = id;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.title = title;
        this.content = content;
        this.remoteAttachment = remoteAttachment;
    }

    public Email(int id, String from, String to, List<String> cc, String title, String content, String localAttachment, URL remoteAttachment) {
        this();
        this.id = id;
        this.from = from;
        this.to = new LinkedList<String>();
        this.to.add(to);
        this.cc = cc;
        this.title = title;
        this.content = content;
        this.localAttachment = localAttachment;
        this.remoteAttachment = remoteAttachment;
    }

    public Email(int id, String from, String to, String title, String content, URL remoteAttachment) {
        this();
        Preconditions.checkArgument(from != null);
        Preconditions.checkArgument(to != null);
        Preconditions.checkArgument(title != null);
        Preconditions.checkArgument(content != null);
        Preconditions.checkArgument(PatternValidater.validateMailAddress(from));
        Preconditions.checkArgument(PatternValidater.validateMailAddress(to));

        this.id = id;
        this.to.add(to);
        this.from = from;
        this.title = title;
        this.content = content;
        this.remoteAttachment = remoteAttachment;
    }

    public Email(int id, String from, String to, String title, String content, String localAttachment) {
        this();
        Preconditions.checkArgument(from != null);
        Preconditions.checkArgument(to != null);
        Preconditions.checkArgument(title != null);
        Preconditions.checkArgument(content != null);
        Preconditions.checkArgument(PatternValidater.validateMailAddress(from));
        Preconditions.checkArgument(PatternValidater.validateMailAddress(to));

        this.id = id;
        this.to.add(to);
        this.from = from;
        this.title = title;
        this.content = content;
        this.localAttachment = localAttachment;
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
        Preconditions.checkArgument(PatternValidater.validateMailAddress(from));

        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void addTo(String toAddr) {
        Preconditions.checkArgument(PatternValidater.validateMailAddress(toAddr));
        Preconditions.checkArgument(this.to != null);

        this.to.add(toAddr);
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void addCc(String ccAddr) {
        Preconditions.checkArgument(PatternValidater.validateMailAddress(ccAddr));
        Preconditions.checkArgument(this.cc != null);

        this.cc.add(ccAddr);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocalAttachment() {
        return localAttachment;
    }

    public void setLocalAttachment(String localAttachment) {
        this.localAttachment = localAttachment;
    }

    public URL getRemoteAttachment() {
        return remoteAttachment;
    }

    public void setRemoteAttachment(URL remoteAttachment) {
        this.remoteAttachment = remoteAttachment;
    }

    public boolean hasAttachment() {
        return this.localAttachment!= null
                || this.remoteAttachment != null;
    }

    public boolean hasLocalAttachement() {
        return this.localAttachment != null;
    }

    public boolean hasRemoteAttachment() {
        return this.remoteAttachment != null;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", mailID=" + mailID +
                ", from='" + from + '\'' +
                ", to=" + to +
                ", cc=" + cc +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", localAttachment='" + localAttachment + '\'' +
                ", remoteAttachment=" + remoteAttachment +
                '}';
    }
}
