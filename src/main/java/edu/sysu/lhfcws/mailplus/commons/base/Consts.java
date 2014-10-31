package edu.sysu.lhfcws.mailplus.commons.base;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class Consts {
    public static final String MAILPLUS_CONFIG = "mailplus.config";

    // Config param
    public static final String SERVER_PORT = "SERVER_PORT";
    public static final String HTTP_PORT = "HTTP_PORT";

    // Commons config
    public static final String CRLF = "\r\n";
    public static final String AT = "@";
    public static final String RESET_AUTH_CODE = "mailplus";

    // DB
    public static final String DB_NAME = "mailplus";

    public static final String BDB_PATH = "./.bdb/";
    public static final String SRQ = "SendRequestQueue";

    public static final String SQLITE_PATH = "./.sqlite/";
    public static final String SQLITE_CLASS = "org.sqlite.JDBC";
    public static final String SQLITE_DB_STRING =
            String.format("jdbc:sqlite:%s%s.db", SQLITE_PATH, DB_NAME);

    public static final String TBL_USER = "user";
    public static final String TBL_HOST = "remote_hosts";
    public static final String TBL_EMAIL = "email";
    public static final String TBL_EMAIL_STATUS = "email_status";
}
