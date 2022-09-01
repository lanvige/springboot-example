package com.lanvige.example.hashedwheel;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UniTimerTask implements TimerTask {

    private String taskName;

    public UniTimerTask(String name) {
        this.taskName = name;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        log.info("TimerTask 任务开始执行：{} - {}", this.taskName, DateTime.now());
        log.info("timeout：{}", timeout);

        // Thread.sleep(17000);
        // log.info("TimerTask 任务结束执行：{} - {}", this.taskName, DateTime.now());
        //
        // boolean isSuccess = UniHashedWheelTimer.addTask(this, 4);

        // Integer curTimeInterval = RandomUtil.randomInt(10, 100);
        // UniHashedWheelTimer.addTask(this, curTimeInterval);
    }
}
