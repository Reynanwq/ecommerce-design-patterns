package com.ecommerce.ecommerce_gof_patterns;

import org.springframework.boot.SpringApplication;

public class TestECommerceGofDesignPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ECommerceGofDesignPatternsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
