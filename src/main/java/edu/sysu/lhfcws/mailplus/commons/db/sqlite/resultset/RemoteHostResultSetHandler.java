package edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset;

import edu.sysu.lhfcws.mailplus.commons.model.RemoteHost;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-10-27.
 */
public class RemoteHostResultSetHandler implements ResultSetHandler<RemoteHost> {

    @Override
    public RemoteHost handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            RemoteHost remoteHost = new RemoteHost();
            remoteHost.setId(rs.getInt("id"));
            remoteHost.setImapHost(rs.getString("imap"));
            remoteHost.setSmtpHost(rs.getString("smtp"));
            remoteHost.setPop3Host(rs.getString("pop3"));
            return remoteHost;
        }
        else
            return null;
    }
}
