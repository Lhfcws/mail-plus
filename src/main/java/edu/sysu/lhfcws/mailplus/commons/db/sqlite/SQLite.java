package edu.sysu.lhfcws.mailplus.commons.db.sqlite;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDaoFactory;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;


/**
 * SQLite factory.
 * @author lhfcws
 * @time 14-10-26.
 */
public class SQLite {
    private static Log LOG = LogFactory.getLog(SQLite.class);

    public static BaseDao getDao() {
        return BaseDaoFactory.getDaoBaseInstance(Consts.SQLITE_DB_STRING);
    }

    public static boolean pingTest() {
        String sql = "ANALYZE;";
        try {
            getDao().batchExecute(sql);
            return true;
        } catch (SQLException e) {
            LogUtil.error(LOG, e, "Cannot connect to sqlite in path " + Consts.SQLITE_DB_STRING);
            return false;
        }
    }
}
