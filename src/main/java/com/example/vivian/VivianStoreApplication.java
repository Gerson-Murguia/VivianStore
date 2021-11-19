package com.example.vivian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class VivianStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(VivianStoreApplication.class, args);
	}

}
