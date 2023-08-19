package com.example.familia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.familia.entities.Following;

public interface FollowingRepository extends JpaRepository<Following, Integer> {
	

}
