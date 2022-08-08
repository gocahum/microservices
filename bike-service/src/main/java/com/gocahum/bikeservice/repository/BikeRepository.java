package com.gocahum.bikeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gocahum.bikeservice.entity.Bike;

public interface BikeRepository extends JpaRepository<Bike, Integer> {

	List<Bike> findByUserId(int userId);

}
