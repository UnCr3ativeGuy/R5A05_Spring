package com.example.TP_Spring_Belloc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class TpSpringBellocApplication {
	public static void main(String[] args) {
		SpringApplication.run(TpSpringBellocApplication.class, args);
	}
	@GetMapping("/bonjour")
	public String hello(@RequestParam(value = "name", defaultValue = "monde") String name) {
		return String.format("Bonjour le %s!", name);
	}
}