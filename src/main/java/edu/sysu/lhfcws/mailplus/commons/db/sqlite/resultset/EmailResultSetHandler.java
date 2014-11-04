package edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author lhfcws
 * @time 14-11-1.
 */
public class EmailResultSetHandler implements ResultSetHandler<Email> {
    private static Gson gson = new Gson();

    @Override
    public Email handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Email email = new Email();

            email.setId(rs.getInt("id"));
            email.setFrom(rs.getString("from"));

            List<String> list = gson.fromJson(rs.getString("to"),
                    new TypeToken<List<String>>() {}.getType());
            email.setTo(list);

            list = gson.fromJson(rs.getString("cc"), new TypeToken<List<String>>(){}.getType());
            email.setCc(list);

            email.setSubject(rs.getString("subject"));
            email.setContent(rs.getString("content"));

            List<Attachment> attachments = gson.fromJson(rs.getString("attachment"),
                    new TypeToken<List<String>>() {}.getType());
            email.setAttachments(attachments);

            email.setStatus(Email.EmailStatus.fromValue(rs.getInt("status")));
            email.setDate(new Date(rs.getLong("timestamp")));

            return email;
        }
        return null;
    }
}