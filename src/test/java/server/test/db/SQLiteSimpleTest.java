package server.test.db;

import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteJDBCLoader;


/**
 * @author lhfcws
 * @time 14-10-26.
 */
public class SQLiteSimpleTest {

    public static void main(String[] args) throws Exception {
//        Class.forName("org.sqlite.JDBC");
//        Connection connection = DriverManager.getConnection(
//                String.format("jdbc:sqlite:./.sqlite/%s.db", Consts.DB_NAME)
//        );
//        System.out.println("Connected to SQLite " + String.format("jdbc:sqlite:%s.db", Consts.DB_NAME));
        boolean initialize = SQLiteJDBCLoader.initialize();
        System.out.println(initialize);
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:./.sqlite/mailplus.db");

        BaseDao dao = new BaseDao(ds);
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
        dao.batchExecute(sql);

//        Statement stmt = connection.createStatement();
//        String sql = "CREATE TABLE COMPANY " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " AGE            INT     NOT NULL, " +
//                " ADDRESS        CHAR(50), " +
//                " SALARY         REAL)";
//        stmt.executeUpdate(sql);
//        stmt.close();
//        connection.close();
    }
}
