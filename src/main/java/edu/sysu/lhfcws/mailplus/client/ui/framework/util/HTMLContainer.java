package edu.sysu.lhfcws.mailplus.client.ui.framework.util;


import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-10-29.
 */
public class HTMLContainer extends JEditorPane {

    protected int id;
    protected String information;
    protected String html;

    /**
     * Only for inheritence.
     */
    protected HTMLContainer() {}

    public HTMLContainer(String html) {
        super("text/html", html);
        this.id = -1;
        this.html = html;
        this.setEditable(false);
        this.setAutoscrolls(false);
        this.setVisible(true);
    }

    public String getHtml() {
        return html;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HTMLContainer{" +
                "id=" + id +
                ", information='" + information + '\'' +
                ", html='" + html + '\'' +
                "} " + super.toString();
    }
}
