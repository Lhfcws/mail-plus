package edu.sysu.lhfcws.mailplus.client.util;

import edu.sysu.lhfcws.mailplus.commons.model.RemoteHost;

import java.util.HashMap;

/**
 * A local storage of RemoteHost, synced with SQLite.
 * @author lhfcws
 * @time 14-12-27.
 */
@Deprecated
public class RemoteHostStore {
    public static HashMap<String, RemoteHost> store = new HashMap<String, RemoteHost>();

    static {
        init();
    }

    public static void init() {
        put("163.com", "smtp.163.com", "imap.163.com", "pop3.163.com");
        put("126.com", "smtp.126.com", "imap.126.com", "pop3.126.com");
        put("sina.com", "smtp.sina.com", "imap.sina.com", "pop.sina.com");
        put("yeah.net", "smtp.yeah.net", "imap.yeah.net", "pop3.yeah.net");
        // not supported now because of the port is not defined.
        put("qq.com", "smtp.qq.com", "imap.qq.com", "pop.qq.com");
        put("mail.sysu.edu.cn", "smtp.exmail.qq.com", "imap.exmail.qq.com", "pop.exmail.qq.com");
        put("mail2.sysu.edu.cn", "smtp.exmail.qq.com", "imap.exmail.qq.com", "pop.exmail.qq.com");
        put("gmail.com", "smtp.gmail.com", "imap.gmail.com", "pop.gmail.com");
    }

    private static void put(String domain, String smtp, String imap, String pop3) {
        store.put(domain, new RemoteHost(smtp, imap, pop3));
    }
}
