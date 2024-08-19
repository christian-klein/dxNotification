package com.hcl.dxnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DxNotificationApplication extends SpringBootServletInitializer {
	//Running the Spring Boot app as a standalone app
	public static void main(String[] args) {
		SpringApplication.run(DxNotificationApplication.class, args);
	}

}
