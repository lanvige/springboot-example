package com.zhenbei.uniscrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniscrmApplication {

	public static void main(String[] args) {

		UniTimerTask uniTimerTask = new UniTimerTask("1");
		UniHashedWheelTimer.addTask(uniTimerTask, 10);

		UniTimerTask uniTimerTask2 = new UniTimerTask("2");
		UniHashedWheelTimer.addTask(uniTimerTask2, 4);

		UniTimerTask uniTimerTask3 = new UniTimerTask("3");
		UniHashedWheelTimer.addTask(uniTimerTask3, 2);

		SpringApplication.run(UniscrmApplication.class, args);
	}

}
