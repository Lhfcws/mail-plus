package edu.sysu.lhfcws.mailplus.commons.util;

import org.apache.commons.logging.Log;

/**
 * @author lhfcws
 * @time 14-10-27.
 */
public class LogUtil {

    /**
     * Print info by Log and System.out
     * @param LOG
     * @param msg
     */
    public static void info(Log LOG, String msg) {
        LOG.info(msg);
        System.out.println(msg);
    }

    /**
     * Print error by Log and System.out
     * @param LOG
     * @param msg
     */
    public static void error(Log LOG, String msg) {
        LOG.error(msg);
        System.err.println("[ERROR] " + msg);
    }

    /**
     * Print throwable error by Log and System.out
     * @param LOG
     * @param e
     */
    public static void error(Log LOG, Throwable e) {
        LOG.error(e.getMessage(), e);
        System.err.println("[ERROR] " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * Print throwable error and error msg by Log and System.out
     * @param LOG
     * @param e
     */
    public static void error(Log LOG, Throwable e, String msg) {
        LOG.error(msg, e);
        System.err.println("[ERROR] " + msg);
        e.printStackTrace();
    }

    public static void debug(String msg) {
        System.out.println("[DEBUG] " + msg);
    }
}
