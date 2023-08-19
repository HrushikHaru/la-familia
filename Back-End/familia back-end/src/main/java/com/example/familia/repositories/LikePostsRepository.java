package com.example.familia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.entities.LikePosts;

public interface LikePostsRepository extends JpaRepository<LikePosts, Integer> {

}
