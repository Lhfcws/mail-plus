package client.unittest;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author lhfcws
 * @time 14-12-30.
 */
public class UtilUnitTest {
//    @Test
    public void berkeleyDBTest() throws InterruptedException {
        BDB bdb = BDB.getInstance(Consts.BDB_PATH + "test");
        bdb.start();
        bdb.clear();
        bdb.set(System.currentTimeMillis() + "", "12");
        Thread.sleep(1);
        bdb.set(System.currentTimeMillis() + "", "34");
        Thread.sleep(1);
        bdb.set(System.currentTimeMillis() + "", "56");

        TreeMap<String, String> res = new TreeMap<String, String>(bdb.selectAll());
        bdb.stop();

        StringBuilder sb = new StringBuilder();
        for (String v : res.values()) {
            sb.append(v);
        }

        assert sb.toString().equals("123456");
    }

    @Test
    public void parseEmailTest() throws UnsupportedEncodingException {
        List<String> list = new LinkedList<String>();
        list.add("=?utf-8?b?5Lqs5LicSkQuY29t?= <newsletter1@jd.com>");
        list.add("=?UTF-8?B?5bCP5oi3?= <parvani@swallow.navebase.com>");

        List<String> result = new LinkedList<String>();
        result.add("京东JD.com <newsletter1@jd.com>");
        result.add("小户 <parvani@swallow.navebase.com>");

        for (int i = 0; i < list.size(); i++) {
            String e = CommonUtil.parseAddressName(list.get(i));
            assert e.equals(result.get(i));
        }
    }
}
