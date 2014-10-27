package edu.sysu.lhfcws.mailplus.commons.validate;

import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;

import java.util.regex.Pattern;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class PatternValidater {

    public static boolean validateMailAddress(String mailAddr) {
        if (mailAddr == null)
            return false;

        Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        return CommonUtil.regMatch(pattern, mailAddr);
    }

}
