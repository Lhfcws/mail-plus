package edu.sysu.lhfcws.mailplus.commons.queue;

import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;

import java.util.HashMap;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class RQCenter {
    private static HashMap<String, PersistentRequestQueue> cache = new HashMap<String, PersistentRequestQueue>();

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

    private static HashMap<String, MultiPersistentRequestQueues> cacheMulti = new HashMap<String, MultiPersistentRequestQueues>();

    public static MultiPersistentRequestQueues getMultiRQ(String name) {
        if (!cacheMulti.containsKey(name)) {
            synchronized (cacheMulti) {
                if (!cacheMulti.containsKey(name)) {
                    cacheMulti.put(name, new MultiPersistentRequestQueues());
                }
            }
        }
        return cacheMulti.get(name);
    }
}
