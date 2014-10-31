package edu.sysu.lhfcws.mailplus.commons.controller;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class EmailController {

    private BaseDao dao;

    public EmailController() {
        dao = SQLite.getDao();
    }

    public void saveDraft(Email email) throws SQLException {
        String sql = String.format("INSERT INTO '%s' (?,?,?)", Consts.TBL_EMAIL);
        dao.batchExecute(sql);
    }

    public void sendEmail(Email email) throws SQLException {
        if (email.getId() == -1) {
            String sql = String.format("INSERT INTO '%s' (?,?,?)", Consts.TBL_EMAIL);
            dao.batchExecute(sql);
        } else {
            changeEmailStatus(email, Email.EmailStatus.SENDING);
        }
    }


    public void changeEmailStatus(Email email, Email.EmailStatus status) throws SQLException {
        String sql = String.format("UPDATE FROM %s SET status=? WHERE email_id=?",
                Consts.TBL_EMAIL_STATUS);

        dao.batchExecute(sql, status, email.getId());
    }
}
