package edu.sysu.lhfcws.mailplus.commons.util;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class CommonUtil {

    public static boolean regMatch(Pattern pattern, String toMatch) {
        if (pattern == null || toMatch == null)
            return false;

        Matcher matcher = pattern.matcher(toMatch);
        return matcher.find()
                && matcher.group().equals(toMatch);
    }

    public static String getFormatNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    public static String protocolStr(String cmd, String content) {
        if (StringUtils.isEmpty(cmd))
            return String.format("%s%s", content, Consts.CRLF);

        if (StringUtils.isEmpty(content))
            return String.format("%s%s", cmd, Consts.CRLF);

        return String.format("%s %s%s", cmd, content, Consts.CRLF);
    }
}
