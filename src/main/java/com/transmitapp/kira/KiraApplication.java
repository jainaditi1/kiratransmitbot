package com.transmitapp.kira;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.transmitapp.kira.controller.TransmitController;

@SpringBootApplication
public class KiraApplication {

	public static void main(String[] args) {

		SpringApplication.run(KiraApplication.class, args);
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				TransmitController controller = new TransmitController();
				controller.getBusStatus();
			}
		}, 60000, 60000);

	}

}
