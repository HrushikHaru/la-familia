package com.example.familia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.entities.SignUp;

public interface SignUpRepository extends JpaRepository<SignUp,Integer> {

}
