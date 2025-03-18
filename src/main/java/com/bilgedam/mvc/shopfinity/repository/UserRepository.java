package com.bilgedam.mvc.shopfinity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bilgedam.mvc.shopfinity.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

}
