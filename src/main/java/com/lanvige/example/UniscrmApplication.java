package com.lanvige.example;

import com.lanvige.example.hashedwheel.UniHashedWheelTimer;
import com.lanvige.example.hashedwheel.UniTimerTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniscrmApplication {

	public static void main(String[] args) {

		UniTimerTask uniTimerTask = new UniTimerTask("1");
		UniHashedWheelTimer.addTask(uniTimerTask, 30);

		// UniTimerTask uniTimerTask2 = new UniTimerTask("2");
		// UniHashedWheelTimer.addTask(uniTimerTask2, 10);
		//
		// UniTimerTask uniTimerTask3 = new UniTimerTask("3");
		// UniHashedWheelTimer.addTask(uniTimerTask3, 21);

		SpringApplication.run(UniscrmApplication.class, args);
	}
}
