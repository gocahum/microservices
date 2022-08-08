package com.gocahum.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gocahum.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
