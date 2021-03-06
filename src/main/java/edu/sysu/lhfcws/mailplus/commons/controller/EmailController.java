package edu.sysu.lhfcws.mailplus.commons.controller;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.EmailListResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.EmailResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * The databast controller of email.
 *
 * @author lhfcws
 * @time 14-10-31.
 */
public class EmailController {

    private static Gson gson = CommonUtil.GSON;
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

        long now = System.currentTimeMillis();
        int id = dao.insertWithKey(sql, email.getFrom(),
                gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                email.getSubject(), email.getContent(),
                gson.toJson(email.getAttachments()), Email.EmailStatus.DRAFT.getValue(),
                now, email.getSignature());
        email.setId(id);
    }

    public void saveSendEmail(Email email) throws SQLException {
        if (email.getSignature() == null)
            email.setSignature(email.getFrom());

        if (email.getId() == -1) {
            String sql = String.format("INSERT INTO '%s' " +
                    "('from','to','cc','subject','content','attachment','status','timestamp', 'signature') " +
                    "VALUES (?,?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
            long now = System.currentTimeMillis();
            int id = dao.insertWithKey(sql, email.getFrom(),
                    gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                    email.getSubject(), email.getContent(),
                    gson.toJson(email.getAttachments()), Email.EmailStatus.SENDING.getValue(),
                    now, email.getSignature());
            email.setId(id);
        } else {
            changeEmailStatus(email, Email.EmailStatus.SENDING);
        }
    }

    public void saveUnreadEmail(Email email) throws SQLException {
        String sql = String.format("INSERT INTO '%s' " +
                "('mail_id', 'from','to','cc','subject','content','attachment','status','timestamp', 'signature') " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
        long now = System.currentTimeMillis();
        int id = dao.insertWithKey(sql, email.getMailID(), email.getFrom(),
                gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                email.getSubject(), email.getContent(),
                gson.toJson(email.getAttachments()), Email.EmailStatus.UNREAD.getValue(),
                now, email.getSignature());
        email.setId(id);
    }

    public void changeEmailStatus(Email email, Email.EmailStatus status) throws SQLException {
        String sql = String.format("UPDATE %s SET status=%s WHERE id=%s",
                Consts.TBL_EMAIL, status.getValue(), email.getId());

        dao.batchExecute(sql);
        email.setStatus(status);
    }

    public Email getEmailByID(int id) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id=%s", Consts.TBL_EMAIL, id);

        Email email = dao.querySingleObj(sql, new EmailResultSetHandler());
        return email;
    }

    public List<Email> getEmailListByStatus(Collection<Email.EmailStatus> conditions) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (!conditions.isEmpty()) {
            sb.append("WHERE ");

            boolean notFirst = false;
            for (Email.EmailStatus emailStatus : conditions) {
                if (notFirst)
                    sb.append(" OR ");
                sb.append("status=").append(emailStatus.getValue());
                notFirst = true;
            }
        }

        String sql = String.format("SELECT * FROM %s %s ORDER BY timestamp DESC",
                Consts.TBL_EMAIL, sb.toString()).trim();
        List<Email> list = dao.queryObjects(sql, new EmailListResultSetHandler());
        LogUtil.debug("List size: " + list.size());
        LogUtil.debug(sql);
        return list;
    }

    public void deleteEmail(Email email) throws SQLException {
        String deleteSQL = String.format("DELETE FROM %s WHERE id=%d", Consts.TBL_EMAIL, email.getId());
        int affectedRows = dao.batchExecute(deleteSQL);

        if (email.getMailID() > 0 && affectedRows == 1) {
            String updateSQL = String.format("UPDATE %s SET mail_id = mail_id - 1 " +
                            "WHERE signature='%s' ANDmail_id>%d",
                    Consts.TBL_EMAIL, email.getSignature(), email.getMailID());
            dao.batchExecute(updateSQL);
        }
    }

    public int getLatestMailID(String signature) throws SQLException {
        String sql = String.format("SELECT MAX(mail_id) FROM %s " +
                        "WHERE signature='%s' AND (status=%d OR status=%d)",
                Consts.TBL_EMAIL, signature, Email.EmailStatus.READED.getValue(),
                Email.EmailStatus.UNREAD.getValue());

        return dao.querySingleObj(sql, new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getInt(1);
                } else
                    return -1;
            }
        });
    }
}
