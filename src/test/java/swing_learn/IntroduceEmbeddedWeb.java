package swing_learn;


import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class IntroduceEmbeddedWeb {

    public static void start() {
        JFrame frame = new JFrame("WebTest");

        frame.setLayout(new BorderLayout());
        JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.navigate("http://www.baidu.com");
        webBrowser.setMenuBarVisible(false);
        frame.add(webBrowser, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.show();
    }

    public static void main(String[] args) {
//        NativeSwing.initialize();
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });

        NativeInterface.runEventPump();
    }
}
