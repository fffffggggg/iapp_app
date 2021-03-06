package com.convenient.iapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.convenient.iapp.dao")
public class IappApplication {

	public static void main(String[] args) {
		SpringApplication.run(IappApplication.class, args);
	}

}
