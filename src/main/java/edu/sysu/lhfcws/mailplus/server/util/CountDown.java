package edu.sysu.lhfcws.mailplus.server.util;

import java.util.concurrent.CountDownLatch;

/**
 * A wrapper of CountDown using CountDownLatch.
 * @author lhfcws
 * @time 14-10-25.
 */
public class CountDown {
    private CountDownLatch countDownLatch;

    public CountDown() {
        this.countDownLatch = new CountDownLatch(0);
    }

    public void countDown() {
        this.countDownLatch.countDown();
    }

    public void set(int i) {
        this.countDownLatch = new CountDownLatch(i);
    }

    public boolean available() {
        return (this.countDownLatch.getCount() == 0);
    }
}
