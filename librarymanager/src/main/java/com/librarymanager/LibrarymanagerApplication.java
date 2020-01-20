package com.librarymanager;

import com.librarymanager.services.StorageService;
import com.librarymanager.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class LibrarymanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarymanagerApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}
}
