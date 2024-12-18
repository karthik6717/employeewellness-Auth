package com.gk.ms.employeewellness_Auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableDiscoveryClient
@CrossOrigin("*")
@EntityScan("com.gk.ms.employeewellness_Auth.entity")
public class EmployeewellnessAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeewellnessAuthApplication.class, args);
	}

}
