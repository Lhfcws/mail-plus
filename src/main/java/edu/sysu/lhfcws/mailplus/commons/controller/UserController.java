package edu.sysu.lhfcws.mailplus.commons.controller;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.QueryUtil;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import org.apache.commons.codec.binary.Base64;

import java.sql.SQLException;

/**
 * The database controller of user.
 * @author lhfcws
 * @time 14-11-2.
 */
public class UserController {
    private BaseDao dao;

    public UserController() {
        dao = SQLite.getDao();
    }

    public void updateUser(String email, String uncryptedPassword) throws SQLException {
        MailUser mailUser = QueryUtil.getUser(email);
        if (mailUser == null || mailUser.getMailAddr() == null) {
            String nickname = email.split(Consts.AT)[0];
            String sql = String.format("INSERT INTO %s ('nickname','email','password') VALUES (?,?,?)",
                    Consts.TBL_USER);

            dao.batchExecute(sql, nickname, email,
                    Base64.encodeBase64String(uncryptedPassword.getBytes()));
        } else {
            String sql = String.format("UPDATE %s SET password='%s' WHERE email='%s'",
                    Consts.TBL_USER, Base64.encodeBase64String(uncryptedPassword.getBytes()), email);

            dao.batchExecute(sql);
        }
    }

    public MailUser getUser(String email) throws SQLException {
        return QueryUtil.getUser(email);
    }

    public MailUser getFullUser(String email) throws SQLException {
        return QueryUtil.getFullMailUser(email);
    }
}
