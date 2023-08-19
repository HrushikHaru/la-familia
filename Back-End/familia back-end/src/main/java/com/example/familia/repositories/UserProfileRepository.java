package com.example.familia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.familia.entities.LogIn;
import com.example.familia.entities.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

}
