package com.example.demo;

import com.example.demo.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//import com.example.demo.storage.StorageService;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import com.example.demo.storage.StorageService;

//@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
@RestController
public class DemoApplication {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// part 1
	@GetMapping("/")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// part 2
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// part 3
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			while (true) {
				Quote quote = restTemplate.getForObject(
						"https://quoters.apps.pcfone.io/api/random", Quote.class);
				log.info(quote.toString());
			}
		};
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}

}
