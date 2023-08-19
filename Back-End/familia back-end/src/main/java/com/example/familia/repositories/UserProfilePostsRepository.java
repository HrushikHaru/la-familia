package com.example.familia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.entities.UserProfilePosts;

public interface UserProfilePostsRepository extends JpaRepository<UserProfilePosts, Integer> {

}
