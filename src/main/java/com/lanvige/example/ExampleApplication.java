package com.lanvige.example;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.lanvige.example.hashedwheel.UniHashedWheelTimer;
import com.lanvige.example.hashedwheel.UniTimerTask;
import com.lanvige.example.util.JacksonUtils;
import com.lanvige.example.util.ToolCommandClientsBO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {

        // UniTimerTask uniTimerTask = new UniTimerTask("1");
        // UniHashedWheelTimer.addTask(uniTimerTask, 30);

        // UniTimerTask uniTimerTask2 = new UniTimerTask("2");
        // UniHashedWheelTimer.addTask(uniTimerTask2, 10);
        //
        // UniTimerTask uniTimerTask3 = new UniTimerTask("3");
        // UniHashedWheelTimer.addTask(uniTimerTask3, 21);

        String lastHeartInfo = "[{\"username\":\"蒋小明\",\"account_id\":\"JiangZhiMing\",\"user_id\":\"1688851157464256\",\"corp_id\":\"1970325131200076\"}]";
        List<ToolCommandClientsBO> lastClients = JacksonUtils.parseList(lastHeartInfo, ToolCommandClientsBO.class);


        SpringApplication.run(ExampleApplication.class, args);
    }
}
