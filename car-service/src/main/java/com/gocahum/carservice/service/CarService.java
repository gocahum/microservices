package com.gocahum.carservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gocahum.carservice.entity.Car;
import com.gocahum.carservice.repository.CarRepository;

@Service
public class CarService {
	@Autowired
	CarRepository repository;
	
	public List<Car> getAll(){
		return repository.findAll();
	}
	
	public Car getByCarId(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public Car save(Car car) {
		Car userNew = repository.save(car);
		return userNew;
	}
	
	public List<Car> byUserId(int userId){
		return repository.findByUserId(userId);
	}

}
