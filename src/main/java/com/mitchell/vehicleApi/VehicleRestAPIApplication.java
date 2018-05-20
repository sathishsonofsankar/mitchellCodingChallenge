package com.mitchell.vehicleApi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VehicleRestAPIApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		new VehicleRestAPIApplication().configure(new SpringApplicationBuilder(VehicleRestAPIApplication.class)).run(args);
	}
}
