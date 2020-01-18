package com.github.equiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Robin
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.github.equiz.questions")
public class App 
{
	/**
	 * Main method of spring boot app
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
