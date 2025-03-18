package com.bilgedam.mvc.shopfinity;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bilgedam.mvc.shopfinity.enums.Role;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.UserRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder) {

		UserEntity admin = new UserEntity();
		admin.setName("Dilara");
		admin.setSurname("Kısa");
		admin.setBirthDate(LocalDate.of(1998, 7, 11));
		admin.setEmail("dilara@gmail.com");
		String parola = passwordEncoder.encode("123");
		admin.setPassword(parola);
		admin.setRole(Role.ROLE_ADMIN);

		UserEntity user = new UserEntity();
		user.setName("Murat");
		user.setSurname("Kısa");
		user.setBirthDate(LocalDate.of(2000, 1, 2));
		user.setEmail("murat@gmail.com");
		parola = passwordEncoder.encode("123");
		user.setPassword(parola);
		user.setRole(Role.ROLE_USER);

		return args -> {
			if (!userRepository.existsByEmail(admin.getEmail()))
				userRepository.save(admin);

			if (!userRepository.existsByEmail(user.getEmail()))
				userRepository.save(user);

		};
	}

}
