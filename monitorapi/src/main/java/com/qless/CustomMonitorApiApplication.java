package com.qless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//
@SpringBootApplication
public class CustomMonitorApiApplication extends SpringBootServletInitializer  {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomMonitorApiApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CustomMonitorApiApplication.class);
	}
	
    
}




