package com.cathay.libraryManagement.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CathayLibrarySystemDemoApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(CathayLibrarySystemDemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CathayLibrarySystemDemoApplication.class, args);
		LOGGER.info("Hello World");
	}
}
