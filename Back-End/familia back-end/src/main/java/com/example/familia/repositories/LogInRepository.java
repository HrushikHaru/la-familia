package com.example.familia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.entities.LogIn;

public interface LogInRepository extends JpaRepository<LogIn,Integer> {
	
	Optional<LogIn> findByUsername(String username);

}
