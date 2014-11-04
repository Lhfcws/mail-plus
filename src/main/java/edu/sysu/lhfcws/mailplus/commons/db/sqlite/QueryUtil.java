package edu.sysu.lhfcws.mailplus.commons.db.sqlite;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.RemoteHostResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.UserResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.commons.model.RemoteHost;
import edu.sysu.lhfcws.mailplus.commons.validate.PatternValidater;

import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class QueryUtil {

    private static BaseDao dao = SQLite.getDao();

    /**
     * Query SMTPHost by a given email address.
     * @param emailAddr
     * @return String SMTPHost
     */
    public static String getSMTPHost(String emailAddr) throws SQLException {
        Preconditions.checkArgument(PatternValidater.validateMailAddress(emailAddr));

        String[] hosts = emailAddr.split(Consts.AT);
        String domain = hosts[1].trim();

        RemoteHost remoteHost = getRemoteHost(domain);
//        return "smtp." + domain;
        return remoteHost.getSmtpHost();
    }

    public static RemoteHost getRemoteHost(String domain) throws SQLException {
        String sql = String.format("SELECT * FROM '%s' WHERE domain='%s';", Consts.TBL_HOST, domain);
        RemoteHost remoteHost = dao.querySingleObj(sql, new RemoteHostResultSetHandler());
        return remoteHost;
    }

    public static MailUser getUser(String email) throws SQLException {
        String sql = String.format("SELECT * FROM '%s' WHERE email='%s';", Consts.TBL_USER, email);
        MailUser user = dao.querySingleObj(sql, new UserResultSetHandler());
        return user;
    }

    public static MailUser getFullMailUser(String email) throws SQLException {
        String[] hosts = email.split(Consts.AT);
        String domain = hosts[1].trim();

        MailUser mailUser = getUser(email);
        RemoteHost remoteHost = getRemoteHost(domain);
        mailUser.setRemoteHost(remoteHost);

        return mailUser;
    }
}
