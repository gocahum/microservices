package com.gocahum.bikeservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gocahum.bikeservice.entity.Bike;
import com.gocahum.bikeservice.repository.BikeRepository;

@Service
public class BikeService {
	@Autowired
	BikeRepository repository;
	
	public List<Bike> getAll(){
		return repository.findAll();
	}
	
	public Bike getByBikeId(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public Bike save(Bike bike) {
		Bike userNew = repository.save(bike);
		return userNew;
	}
	
	public List<Bike> byUserId(int userId){
		return repository.findByUserId(userId);
	}

}
