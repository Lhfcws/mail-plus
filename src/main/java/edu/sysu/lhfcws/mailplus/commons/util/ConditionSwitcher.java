package edu.sysu.lhfcws.mailplus.commons.util;

import java.util.HashMap;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class ConditionSwitcher {

    private HashMap<Object, Runnable> map;

    public ConditionSwitcher() {
        map = new HashMap<Object, Runnable>();
    }

    public void addBranch(Object object, Runnable runnable) {
        map.put(object, runnable);
    }

    public void decide(Object obj) {
        if (map.containsKey(obj)) {
            map.get(obj).run();
        }
    }
}
