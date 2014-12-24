package edu.sysu.lhfcws.mailplus.commons.util;

/**
 * HTML util.
 * @author lhfcws
 * @time 14-11-16.
 */
public class HTMLUtil {

    public static String rmTag(String raw) {
        return raw.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    public static String transSpChars(String raw) {
        return raw.replaceAll("\n", "<br/>").replaceAll("\t", "    ").replaceAll(" ", "&nbsp;")
                .replaceAll("\"", "&quot;");
    }
}
