package com.bilgedam.mvc.shopfinity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/locale", "/users/**", "/kayit", "/css/**", "/img/**", "/js/**",
						"/kategoriler/**", "/urunler/**", "/search/**")
				.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/", true))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());

		return http.build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
