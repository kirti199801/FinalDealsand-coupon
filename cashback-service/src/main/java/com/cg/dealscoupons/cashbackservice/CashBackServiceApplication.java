package com.cg.dealscoupons.cashbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CashBackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashBackServiceApplication.class, args);
	}

}
