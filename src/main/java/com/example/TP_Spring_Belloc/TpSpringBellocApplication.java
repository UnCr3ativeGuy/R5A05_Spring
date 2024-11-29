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

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String password, @RequestParam Role role) {

		User n = new User();
		n.setName(name);
		n.setPassword(password);
		n.setRole(role);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
}