package com.example.familia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.entities.PostComments;

public interface PostCommentsRepository extends JpaRepository<PostComments, Integer> {

}
