package com.basic.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDevApplication.class, args);
	}

    @Bean
    public String hello(){
        System.out.println("=======Spring Bean입니다.========");
        return "Hello Bean";
    }
}
