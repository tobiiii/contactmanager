package com.ps.contactmanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title ="Contact manager", version = "1.0",description = "An application to manage list contact over many company"))
public class ContactmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactmanagerApplication.class, args);
	}

}
