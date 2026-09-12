package com.daejeongwang.uoscrazydaejeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UosCrazyDaejeonApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UosCrazyDaejeonApiApplication.class, args);
	}

}
