package server.test;

import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lhfcws
 * @time 14-11-15.
 */
public class CommonUtilTest {

    @Test
    public void testParseAddress() throws UnsupportedEncodingException {
        List<String> list = new LinkedList<String>();
        list.add("=?utf-8?b?5Lqs5LicSkQuY29t?= <newsletter1@jd.com>");
        list.add("=?UTF-8?B?5bCP5oi3?= <parvani@swallow.navebase.com>");

        for (String email : list) {
            System.out.println(CommonUtil.parseAddressName(email));
        }
    }
}
