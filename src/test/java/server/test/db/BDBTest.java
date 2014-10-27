package server.test.db;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;

import java.util.HashMap;

/**
 * @author lhfcws
 * @time 14-10-25.
 */
public class BDBTest {

    private BDB bdb = BDB.getInstance(Consts.BDB_PATH + "test");

    public void start() {
        bdb.start();
        bdb.clear();
        bdb.set(System.currentTimeMillis() + "", "request serialized object");
        bdb.set(System.currentTimeMillis() + "", "163 test object String");
        bdb.set(System.currentTimeMillis() + "", "google dafahao");

        HashMap<String, String> res = bdb.selectAll();
        System.out.println("[Result] " + res);
        bdb.stop();
    }

    public static void main(String[] args) {
        new BDBTest().start();
    }
}
