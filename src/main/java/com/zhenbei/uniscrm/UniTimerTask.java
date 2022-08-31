package com.zhenbei.uniscrm;

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

        // Integer curTimeInterval = RandomUtil.randomInt(10, 100);
        // UniHashedWheelTimer.addTask(this, curTimeInterval);
    }
}
