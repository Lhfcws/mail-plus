package edu.sysu.lhfcws.mailplus.commons.validate;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.base.ProtocolConsts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.QueryUtil;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Verify the user information including email and password.
 * @author lhfcws
 * @time 14-10-27.
 */
public class UserVerifier {

    private static Log LOG = LogFactory.getLog(UserVerifier.class);

    private CommonSocket socket;

    public UserVerifier() {
        this.socket = new CommonSocket();
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be invoked at the first login.
     * @param email
     * @param unencryptedPassword
     * @return
     */
    public boolean verifyRemoteUser(String email, String unencryptedPassword) {
        Preconditions.checkArgument(email != null);
        Preconditions.checkArgument(unencryptedPassword != null);

        try {
            String smtpHost = QueryUtil.getSMTPHost(email);

            this.socket.connect(smtpHost, ProtocolConsts.SMTP_PORT);

            this.socketSend(
                    ProtocolConsts.HELO, smtpHost
            );
            response();

            this.socketSend(
                    ProtocolConsts.AUTH_LOGIN, ""
            );
            response();

            this.socketSend(
                    "", Base64.encodeBase64String(email.getBytes())
            );
            response();

            this.socketSend(
                    "", Base64.encodeBase64String(unencryptedPassword.getBytes())
            );
            response();

            this.socket.close();
            return true;
        } catch (SQLException e) {
            LogUtil.error(LOG, e, "Cannot get smtp host from sqlite.");
            SQLite.pingTest();
            return false;
        } catch (IOException e) {
            LogUtil.error(LOG, e, "Please check your network.");
            return false;
        } catch (Exception e) {
            LogUtil.error(LOG, e);
            return false;
        }
    }

    /**
     * Verify the user according to local sqlite db.
     * @param email
     * @param unencryptedPassword
     * @return
     */
    public boolean verifyLocalUser(String email, String unencryptedPassword) {
        Preconditions.checkArgument(email != null);

        if (unencryptedPassword == null)
            return false;

        try {
            MailUser user = QueryUtil.getUser(email);
            if (user == null)
                return false;
            return (unencryptedPassword.equals(user.getPassword()));
        } catch (SQLException e) {
            LogUtil.error(LOG, e);
            SQLite.pingTest();
            return false;
        }
    }

    /**
     * Wrapper for socket.send in protocol format
     *
     * @param cmd
     * @param content
     * @throws IOException
     */
    private void socketSend(String cmd, String content) throws IOException {
        socket.send(CommonUtil.protocolStr(cmd, content));
        System.out.print(CommonUtil.protocolStr(cmd, content));
    }

    /**
     * @throws IOException
     */
    private void response() throws Exception {
        String msg = socket.receive();
        if (msg.startsWith("2") || msg.startsWith("3"))
            return;

        throw new Exception(msg);
    }
}
