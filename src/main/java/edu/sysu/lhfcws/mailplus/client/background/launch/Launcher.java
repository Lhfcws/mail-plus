package edu.sysu.lhfcws.mailplus.client.background.launch;

import edu.sysu.lhfcws.mailplus.client.background.running.Token;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.DownloadEmailsWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.LoginWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.UserController;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.QueryUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.validate.UserVerifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Main launcher of whole Mailplus.
 *
 * @author lhfcws
 * @time 14-11-1.
 */
public class Launcher {
    private static Log LOG = LogFactory.getLog(Launcher.class);

    public boolean launch() {
        updateGlobalFont();
        LoginWindow.getInstance().start();
        return true;
    }

    public boolean loginInit(Token token) {
        try {
            // verify user
            UserVerifier userVerifier = new UserVerifier();
            boolean verify = userVerifier.verifyRemoteUser(token.getEmail(), token.getPassword());
            if (!verify) {
                JOptionPane.showMessageDialog(null,
                        "Your email and password are dismatched.");
                return false;
            }

            // update local db
            UserController userController = new UserController();
            userController.updateUser(token.getEmail(), token.getPassword());

            // pass the token to main window
            MainWindow.getInstance().addMailbox(token);
            new ServerLauncher().launch();

            DownloadEmailsWindow.getInstance().start(QueryUtil.getFullMailUser(token.getEmail()));

            return true;
        } catch (SQLException e) {
            LogUtil.error(LOG, e);
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    private static void updateGlobalFont() {
        FontUIResource fontRes = new FontUIResource(Font.getFont("sans-serif"));
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }
}
