package edu.sysu.lhfcws.mailplus.client.ui.event.callback;

import edu.sysu.lhfcws.mailplus.client.util.ClientConsts;
import edu.sysu.lhfcws.mailplus.client.util.TemplateLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Menu about callback function.
 * @author lhfcws
 * @time 14-10-31.
 */
public class AboutCallback implements Callback {
    @Override
    public void callback(AWTEvent _event) {
        JOptionPane.showMessageDialog(null,
                TemplateLoader.getInstance().get(ClientConsts.ABOUT_MSG));
    }
}
