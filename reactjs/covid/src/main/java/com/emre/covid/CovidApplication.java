package com.emre.covid;

import com.emre.covid.service.CovidApplicationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CovidApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =  SpringApplication.run(CovidApplication.class, args);
		CovidApplicationService covidApplicationService =  configurableApplicationContext.getBean(CovidApplicationService.class);
		covidApplicationService.parse();
	}

}
