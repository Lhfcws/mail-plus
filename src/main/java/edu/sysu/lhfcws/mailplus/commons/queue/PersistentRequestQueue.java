package edu.sysu.lhfcws.mailplus.commons.queue;


import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.sun.tools.javac.util.Pair;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;

import java.util.HashMap;

/**
 * Persistent queue for request, the acronym for RQ.
 *
 * @author lhfcws
 * @time 14-10-25.
 */
public class PersistentRequestQueue {
    private static Gson gson = new Gson();
    private static HashMap<String, PersistentRequestQueue> cache = new HashMap<String, PersistentRequestQueue>();

    private BDB bdb;

    public static PersistentRequestQueue getRQ(String name) {
        if (!cache.containsKey(name)) {
            synchronized (cache) {
                if (!cache.containsKey(name)) {
                    cache.put(name, new PersistentRequestQueue(name));
                }
            }
        }
        return cache.get(name);
    }

    private PersistentRequestQueue(String name) {
        bdb = BDB.getInstance(Consts.BDB_PATH + name);
        bdb.start();
    }

    public void enQueue(Request req) {
        Preconditions.checkArgument(bdb != null);

        String key = Long.toString(System.currentTimeMillis());
        bdb.set(key, gson.toJson(req));
    }

    public Request deQueue() {
        Preconditions.checkArgument(bdb != null);

        Pair<String, String> pair = bdb.getFirst();
        if (pair == null)
            return null;

        String reqJson = pair.snd;
        if (reqJson == null)
            return null;

        return gson.fromJson(reqJson, Request.class);
    }
}
