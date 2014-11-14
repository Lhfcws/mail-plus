package edu.sysu.lhfcws.mailplus.commons.util;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import org.apache.commons.lang.StringUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class CommonUtil {

    public static Gson GSON = new Gson();

    public static String toUTF8(String raw) throws UnsupportedEncodingException {
        return new String(raw.getBytes("UTF-8"), "UTF-8");
    }

    public static String inputStream2String(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        List<Byte> list = new LinkedList<Byte>();
        while (dataInputStream.available() > 0) {
            list.add(dataInputStream.readByte());
        }

        byte[] bytes = new byte[list.size()];
        int i = 0;
        for (byte b : list) {
            bytes[i] = b;
            i++;
        }

        String result = new String(bytes, "utf-8");
        return result;
    }

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
