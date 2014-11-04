package edu.sysu.lhfcws.mailplus.commons.controller;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.EmailListResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.EmailResultSetHandler;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

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
                "('from','to','cc','subject','content','attachment','status','timestamp') " +
                "VALUES (?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
        int id = dao.batchExecute(sql, email.getFrom(),
                gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                email.getSubject(), email.getContent(),
                gson.toJson(email.getAttachments()), Email.EmailStatus.DRAFT.getValue(),
                System.currentTimeMillis());
        email.setId(id);
    }

    public void sendEmail(Email email) throws SQLException {
        if (email.getId() == -1) {
            String sql = String.format("INSERT INTO '%s' " +
                    "('from','to','cc','subject','content','attachment','status','timestamp') " +
                    "VALUES (?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
            int id = dao.batchExecute(sql, email.getFrom(),
                    gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                    email.getSubject(), email.getContent(),
                    gson.toJson(email.getAttachments()), Email.EmailStatus.SENDING.getValue(),
                    System.currentTimeMillis());
            email.setId(id);
        } else {
            changeEmailStatus(email, Email.EmailStatus.SENDING);
        }
    }

    public void saveUnreadEmail(Email email) throws SQLException {
        String sql = String.format("INSERT INTO '%s' " +
                "('from','to','cc','subject','content','attachment','status','timestamp') " +
                "VALUES (?,?,?,?,?,?,?,?)", Consts.TBL_EMAIL);
        int id = dao.batchExecute(sql, email.getFrom(),
                gson.toJson(email.getTo()), gson.toJson(email.getCc()),
                email.getSubject(), email.getContent(),
                gson.toJson(email.getAttachments()), Email.EmailStatus.UNREAD.getValue(),
                System.currentTimeMillis());
        email.setId(id);
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
}
