package edu.sysu.lhfcws.mailplus.commons.queue;


import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.Pair;


/**
 * Persistent queue for request, the acronym for RQ.
 *
 * @author lhfcws
 * @time 14-10-25.
 */
public class PersistentRequestQueue {

    private BDB bdb;

    PersistentRequestQueue(String name) {
        bdb = BDB.getInstance(Consts.BDB_PATH + name);
        bdb.start();
    }

    public void enQueue(Request req) {
        Preconditions.checkArgument(bdb != null);

        String key = Long.toString(System.currentTimeMillis());
        bdb.set(key, CommonUtil.GSON.toJson(req));
//        LogUtil.debug("enqueue: " + req.getReqID());
    }

    public Request deQueue() {
        Preconditions.checkArgument(bdb != null);

        Pair<String, String> pair = bdb.getFirst();
        if (pair == null)
            return null;

        bdb.delete(pair.first);

        String reqJson = pair.second;
        if (reqJson == null)
            return null;

//        LogUtil.debug("Dequeue: " + reqJson);
        return Request.deserialize(reqJson);
    }
}
