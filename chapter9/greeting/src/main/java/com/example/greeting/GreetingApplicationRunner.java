package com.example.greeting;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class GreetingApplicationRunner implements ApplicationRunner {

	public GreetingApplicationRunner(){
		System.out.println("Initializing GreetingApplicationRunner.");
	}
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Hello everyone! We all like Spring! ");
	}
}
