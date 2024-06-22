package com.userlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginUserApplication.class, args);
		System.out.println("Login User !!!");
	}

}
