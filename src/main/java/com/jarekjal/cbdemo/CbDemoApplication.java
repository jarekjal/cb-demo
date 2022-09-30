package com.jarekjal.cbdemo;

import com.jarekjal.cbdemo.service.SampleDataInitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CbDemoApplication {

	@Autowired
	SampleDataInitializerService sampleDataInitializerService;

	public static void main(String[] args) {
		SpringApplication.run(CbDemoApplication.class, args);
	}

	@PostConstruct
	void initSampleCouchbaseData(){
		sampleDataInitializerService.init();
	}
}
