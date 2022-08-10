package com.gocahum.userservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gocahum.userservice.entity.User;
import com.gocahum.userservice.model.Bike;
import com.gocahum.userservice.model.Car;
import com.gocahum.userservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		log.info("########## getAll ##########");
		List<User> users = userService.getAll();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id) {
		log.info("########## getById ##########");
		log.info("id -> " + id);
		User user = userService.getByUser(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		log.info("########## save ##########");
		log.info("user: " + user.toString());
		User userNew = userService.save(user);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userNew);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
	@GetMapping("/cars/{userId}")
	public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
		log.info("########## getCars ##########");
		log.info("userId: " + userId);
		User user = userService.getByUser(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		List<Car> cars = userService.getCars(userId);
		return ResponseEntity.ok(cars);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
	@PostMapping("savecar/{userid}")
	public ResponseEntity<Car> saveCar(@PathVariable("userid") int userId, @RequestBody Car car) {
		log.info("########## saveCar ##########");
		log.info("Car: " + car.toString());
		if (userService.getByUser(userId) == null) {
			return ResponseEntity.notFound().build();
		}
		Car carNew = userService.saveCar(car, userId);
		return ResponseEntity.ok(carNew);
	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
	@GetMapping("/bikes/{userId}")
	public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {
		log.info("########## getBikes ##########");
		log.info("uerId: " + userId);
		User user = userService.getByUser(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		List<Bike> bikes = userService.getBikes(userId);
		return ResponseEntity.ok(bikes);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveBike")
	@PostMapping("savebike/{userid}")
	public ResponseEntity<Bike> saveBike(@PathVariable("userid") int userId, @RequestBody Bike bike) {
		log.info("########## saveBike ##########");
		log.info("Bike: " + bike.toString());
		if (userService.getByUser(userId) == null) {
			return ResponseEntity.notFound().build();
		}
		Bike bikeNew = userService.saveBike(bike, userId);
		return ResponseEntity.ok(bikeNew);
	}

	@CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
	@GetMapping("getall/{userid}")
	public ResponseEntity<Map<String, Object>> getAll(@PathVariable("userid") int userId) {
		log.info("########## getAll ##########");
		log.info("userId: " + userId);
		Map<String, Object> result = userService.getUserAndVehicle(userId);
		return ResponseEntity.ok(result);
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " tine los coches en el taller", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userid") int userId, @RequestBody Car car,
			RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " no tiene dinero para coches", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " tine las motos en el taller", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userid") int userId, @RequestBody Bike car,
			RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " no tiene dinero para motos", HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userid") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " tiene los vehiculos en el taller", HttpStatus.OK);
	}

}
