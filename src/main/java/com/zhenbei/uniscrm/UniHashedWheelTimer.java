package com.zhenbei.uniscrm;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;

import java.util.concurrent.TimeUnit;

public class UniHashedWheelTimer {

    private static Timer timer = new HashedWheelTimer(7L, TimeUnit.SECONDS, 1);

    public static boolean addTask(UniTimerTask task, long delay) {

        timer.newTimeout(task, delay, TimeUnit.SECONDS);
        return  true;
    }
}
