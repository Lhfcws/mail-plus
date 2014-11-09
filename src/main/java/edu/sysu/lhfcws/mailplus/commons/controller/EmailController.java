package edu.sysu.lhfcws.mailplus.commons.controller;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.EmailListResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.EmailResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class EmailController {

    private static Gson gson = new Gson();
    private BaseDao dao;

    public EmailController() {
        dao = SQLite.getDao();
    }

    public void saveDraft(Email email) throws SQLException {
        String sql = String.format("INSERT INTO '%s' " +
                "('from','to','cc','subject','content','attachment','status','timestamp','signature') " +
                "VALUES (?,?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
        if (email.getSignature() == null)
            email.setSignature(email.getFrom());

        dao.batchExecute(sql, email.getFrom(),
                gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                email.getSubject(), email.getContent(),
                gson.toJson(email.getAttachments()), Email.EmailStatus.DRAFT.getValue(),
                System.currentTimeMillis(), email.getSignature());
        Email e = getEmailByEmail(email);
        email.setId(e.getId());
    }

    public void sendEmail(Email email) throws SQLException {
        if (email.getSignature() == null)
            email.setSignature(email.getFrom());

        if (email.getId() == -1) {
            String sql = String.format("INSERT INTO '%s' " +
                    "('from','to','cc','subject','content','attachment','status','timestamp', 'signature') " +
                    "VALUES (?,?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
            dao.batchExecute(sql, email.getFrom(),
                    gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                    email.getSubject(), email.getContent(),
                    gson.toJson(email.getAttachments()), Email.EmailStatus.SENDING.getValue(),
                    System.currentTimeMillis(), email.getSignature());
            Email e = getEmailByEmail(email);
            email.setId(e.getId());
        } else {
            changeEmailStatus(email, Email.EmailStatus.SENDING);
        }
    }

    public void saveUnreadEmail(Email email) throws SQLException {
        String sql = String.format("INSERT INTO '%s' " +
                "('mail_id', 'from','to','cc','subject','content','attachment','status','timestamp', 'signature') " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
        dao.batchExecute(sql, email.getMailID(), email.getFrom(),
                gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                email.getSubject(), email.getContent(),
                gson.toJson(email.getAttachments()), Email.EmailStatus.UNREAD.getValue(),
                System.currentTimeMillis(), email.getSignature());
        Email e = getEmailByEmail(email);
        email.setId(e.getId());
    }

    public void changeEmailStatus(Email email, Email.EmailStatus status) throws SQLException {
        String sql = String.format("UPDATE FROM %s SET status=%s WHERE id=%s",
                Consts.TBL_EMAIL, status.getValue(), email.getId());

        dao.batchExecute(sql, status.getValue(), email.getId());
    }

    public Email getEmailByID(int id) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id=%s", Consts.TBL_EMAIL, id);

        Email email = dao.querySingleObj(sql, new EmailResultSetHandler());
        return email;
    }

    public List<Email> getEmailListByStatus(Collection<Email.EmailStatus> conditions) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (!conditions.isEmpty()) {
            sb.append(" WHERE ");

            boolean notFirst = false;
            for (Email.EmailStatus emailStatus : conditions) {
                if (notFirst)
                    sb.append(" OR ");
                sb.append("status=").append(emailStatus.getValue());
                notFirst = true;
            }
        }

        String sql = String.format("SELECT * FROM %s %s ORDER BY timestamp DESC", Consts.TBL_EMAIL, sb.toString()).trim();
        List<Email> list = dao.queryObjects(sql, new EmailListResultSetHandler());
        return list;
    }

    public void deleteEmail(int mailID) throws SQLException {
        String deleteSQL = String.format("DELETE FROM %s WHERE mail_id=%d", Consts.TBL_EMAIL, mailID);
        int affectedRows = dao.batchExecute(deleteSQL);

        if (affectedRows == 1) {
            String updateSQL = String.format("UPDATE %s SET mail_id = mail_id - 1 WHERE mail_id>%d",
                    Consts.TBL_EMAIL, mailID);
            dao.batchExecute(updateSQL);
        }
    }

    public Email getEmailByEmail(Email email) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(Consts.TBL_EMAIL).append(" WHERE ");

        sb.append("'from'=").append(email.getFrom()).append(" AND ");
        sb.append("'to'=").append(gson.toJson(email.getTo())).append(" AND ");
        sb.append("'cc'=").append(gson.toJson(email.getCc())).append(" AND ");
        sb.append("subject=").append(email.getSubject()).append(" AND ");
        sb.append("content=").append(email.getContent());

        String sql = sb.toString();
        Email e = dao.querySingleObj(sql, new EmailResultSetHandler());
        return e;
    }

    public int getLatestMailID(String signature) throws SQLException {
        String sql = String.format("SELECT MAX(mail_id) FROM %s " +
                        "WHERE signature='%s' AND (status=%d OR status=%d)",
                Consts.TBL_EMAIL, signature, Email.EmailStatus.READED.getValue(),
                Email.EmailStatus.UNREAD.getValue());

        int result = dao.querySingleObj(sql, new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getInt(0);
                }
                return -1;
            }
        });

        return result;
    }
}
