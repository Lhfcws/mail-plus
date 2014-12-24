package edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset;

import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The resultset handler of MailUser.
 * @author lhfcws
 * @time 14-10-27.
 */
public class MailUserResultSetHandler implements ResultSetHandler<MailUser> {
    @Override
    public MailUser handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            MailUser mailUser = new MailUser();

            mailUser.setId(rs.getInt("id"));
            mailUser.setMailAddr(rs.getString("email"));
            mailUser.setNickName(rs.getString("nickname"));
            mailUser.setPassword(new String(Base64.decodeBase64(rs.getString("password"))));
            mailUser.setImapHost(rs.getString("imap_host"));
            mailUser.setSmtpHost(rs.getString("smtp_host"));
            mailUser.setPop3Host(rs.getString("pop3_host"));

            return mailUser;
        }
        else
            return null;
    }
}
