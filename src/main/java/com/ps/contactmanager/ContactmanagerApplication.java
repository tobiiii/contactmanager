package com.ps.contactmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
public class ContactmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactmanagerApplication.class, args);
	}

}
