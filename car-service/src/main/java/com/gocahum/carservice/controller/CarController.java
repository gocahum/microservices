package com.gocahum.carservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gocahum.carservice.entity.Car;
import com.gocahum.carservice.service.CarService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

	@Autowired
	CarService carService;

	@GetMapping
	public ResponseEntity<List<Car>> getAll() {
		log.info("getAll");
		List<Car> users = carService.getAll();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getById(@PathVariable("id") int id) {
		log.info("getById");
		Car car = carService.getByCarId(id);
		if (car == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(car);
	}

	@PostMapping
	public ResponseEntity<Car> save(@RequestBody Car user) {
		log.info("save");
		Car carNew = carService.save(user);
		if (carNew == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(carNew);
	}
	
	@GetMapping("/byuser/{userid}")
	public ResponseEntity<List<Car>> getUserId(@PathVariable("userid") int userId) {
		log.info("getUserId");
		List<Car> cars = carService.byUserId(userId);
		if (cars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cars);
	}

}
