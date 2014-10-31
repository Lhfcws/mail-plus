package edu.sysu.lhfcws.mailplus.client.ui.framework.util;

import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-10-29.
 */
public class HTMLContainer extends JEditorPane {

    private String information;

    public HTMLContainer(String html) {
        super("text/html", html);
        this.setEditable(false);
        this.setAutoscrolls(false);
        this.setVisible(true);
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
