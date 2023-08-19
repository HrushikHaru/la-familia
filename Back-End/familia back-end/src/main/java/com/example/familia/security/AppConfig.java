package com.example.familia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {
	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		
		http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST,"/signup").permitAll().requestMatchers(HttpMethod.GET,"/long-polling/**").permitAll()
            .anyRequest().authenticated()
        );
		
		http.cors(Customizer.withDefaults());
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		//You need to disable this or else you won't be able to make post requests
		http.csrf(csrf->csrf.disable());
		
		http.addFilterAfter(new JWTTokenGenerator(), BasicAuthenticationFilter.class);
		
		http.addFilterBefore(new JWTTokenValidator(), BasicAuthenticationFilter.class);
		
		http.formLogin(Customizer.withDefaults());
		
		http.httpBasic(Customizer.withDefaults());
		
		
		return http.build();
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
