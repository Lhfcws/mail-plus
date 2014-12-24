package edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * The resultset handler of Email.
 * @author lhfcws
 * @time 14-11-1.
 */
public class EmailResultSetHandler implements ResultSetHandler<Email> {
    private static Gson gson = CommonUtil.GSON;

    @Override
    public Email handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Email email = new Email();

            email.setId(rs.getInt("id"));
            email.setFrom(rs.getString("from"));
            email.setMailID(rs.getInt("mail_id"));

            List<String> list = gson.fromJson(rs.getString("to"),
                    new TypeToken<List<String>>() {}.getType());
            email.setTo(list);

            list = gson.fromJson(rs.getString("cc"), new TypeToken<List<String>>(){}.getType());
            email.setCc(list);

            email.setSubject(rs.getString("subject"));
            email.setContent(rs.getString("content"));

            List<Attachment> attachments = gson.fromJson(rs.getString("attachment"),
                    new TypeToken<List<Attachment>>() {}.getType());
            email.setAttachments(attachments);

            email.setStatus(Email.EmailStatus.fromValue(rs.getInt("status")));
            email.setDate(new Date(rs.getLong("timestamp")));
            email.setSignature(rs.getString("signature"));

            return email;
        }
        return null;
    }
}
