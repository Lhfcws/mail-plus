package edu.sysu.lhfcws.mailplus.commons.db.bdb;

/**
 * Persistent and atomic counter.
 * @author lhfcws
 * @time 14-10-25.
 */
public class BDBCounter {

    private BDB bdb;

    public BDBCounter(String path) {
        this.bdb = BDB.getInstance(path);
        this.bdb.start();
    }

    public void inc(String key) {
        synchronized (this) {
            long cnt = 1;
            String v = this.bdb.get(key);
            if (v != null) {
                cnt = Long.valueOf(v);
                cnt++;
            }
            this.bdb.set(key, String.valueOf(cnt));
        }
    }

    public void dec(String key) {
        synchronized (this) {
            String v = this.bdb.get(key);
            if (v != null) {
                long cnt = Long.valueOf(v);
                cnt--;
                if (cnt > 0)
                    this.bdb.set(key, String.valueOf(cnt));
                else
                    this.bdb.delete(key);
            }
        }
    }

    public Long get(String key) {
        synchronized (this) {
            String v = this.bdb.get(key);
            if (v == null)
                return null;

            Long cnt = Long.valueOf(v);
            return cnt;
        }
    }

    public boolean exists(String key) {
        synchronized (this) {
            String v = this.bdb.get(key);
            return (v != null);
        }
    }
}
