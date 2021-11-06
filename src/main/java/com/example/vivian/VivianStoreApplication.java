package com.example.vivian;

import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VivianStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(VivianStoreApplication.class, args);
		
		System.out.println(Paths.get("src/main/resources/static/img/productos")+"/1.jpg");

	}

}
