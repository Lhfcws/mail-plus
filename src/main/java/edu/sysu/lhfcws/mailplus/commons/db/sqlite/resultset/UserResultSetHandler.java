package edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset;

import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The resultset handler of User.
 * @author lhfcws
 * @time 14-10-27.
 */
public class UserResultSetHandler implements ResultSetHandler<MailUser> {
    @Override
    public MailUser handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            MailUser mailUser = new MailUser();

            mailUser.setId(rs.getInt("id"));
            mailUser.setMailAddr(rs.getString("email"));
            mailUser.setNickName(rs.getString("nickname"));
            mailUser.setPassword(new String(Base64.decodeBase64(rs.getString("password"))));

            return mailUser;
        }
        else
            return null;
    }
}
