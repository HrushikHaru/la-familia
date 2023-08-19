package com.example.familia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.entities.Followers;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {

}
