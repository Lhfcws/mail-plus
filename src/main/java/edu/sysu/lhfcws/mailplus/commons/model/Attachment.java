package edu.sysu.lhfcws.mailplus.commons.model;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import org.apache.commons.codec.binary.Base64;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.Serializable;

/**
 * @author lhfcws
 * @time 14-10-28.
 */
public class Attachment implements Serializable {

    static MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap();

    private String filename;
    private String filepath;
    private String content;
    private String contentType;
    private String encoding;

    public Attachment() {
        this.contentType = "application/octet-stream;";
        this.encoding = "base64";
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
        this.setContentType(mimeTypes.getContentType(filepath));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentType(File f) {
        this.contentType = mimeTypes.getContentType(f);
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Content-Type: %s;name=\"%s\"",
                this.contentType, this.filename.split("\\.")[0])).append(Consts.CRLF);
        sb.append(String.format("Content-Disposition: attachment;filename=\"%s\"", this.filename))
                .append(Consts.CRLF);
        sb.append(String.format("Content-Transfer-Encoding: %s", this.encoding)).append(Consts.CRLF);
        sb.append(Consts.CRLF);
        sb.append(Base64.encodeBase64String(this.content.getBytes()));
//        sb.append(Consts.CRLF);

        return sb.toString();
    }
}
