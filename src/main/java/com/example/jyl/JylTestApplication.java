package com.example.jyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.jyl.*dao*")
public class JylTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JylTestApplication.class, args);
	}
}
