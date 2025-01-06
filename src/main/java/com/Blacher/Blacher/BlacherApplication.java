package com.Blacher.Blacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.Blacher.Blacher")  // Make sure to scan the correct package

public class BlacherApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlacherApplication.class, args);
	}

}
