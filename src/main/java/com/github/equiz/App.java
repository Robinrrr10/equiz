package com.github.equiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.equiz.questions")
public class App 
{
	public static void main(String[] args) {
		System.out.println("in app main======================");
		SpringApplication.run(App.class, args);
	}
}
