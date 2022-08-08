package com.gocahum.userservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.getAll();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		User user = userService.getByUser(id);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);		
	}
	
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user){
		User userNew = userService.save(user);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userNew);		
	}
	
	@GetMapping("/cars/{userId}")
	public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId){
		User user = userService.getByUser(userId);
		if(user == null ) {
			return ResponseEntity.notFound().build();
		}
		List<Car> cars = userService.getCars(userId);
		return ResponseEntity.ok(cars);
	}
	
	@GetMapping("/bikes/{userId}")
	public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId){
		User user = userService.getByUser(userId);
		if(user == null ) {
			return ResponseEntity.notFound().build();
		}
		List<Bike> bikes = userService.getBikes(userId);
		return ResponseEntity.ok(bikes);
	}
	
	@PostMapping("savecar/{userid}")
	public ResponseEntity<Car> saveCar(@PathVariable("userid") int userId, @RequestBody Car car ){
		if(userService.getByUser(userId) == null) {
			return ResponseEntity.notFound().build();
		}
		Car carNew = userService.saveCar(car, userId);
		return ResponseEntity.ok(carNew);
	}
	
	@PostMapping("savebike/{userid}")
	public ResponseEntity<Bike> saveCar(@PathVariable("userid") int userId, @RequestBody Bike bike ){if(userService.getByUser(userId) == null) {
		return ResponseEntity.notFound().build();
	}
		Bike bikeNew = userService.saveBike(bike, userId);
		return ResponseEntity.ok(bikeNew);
	}
	
	@GetMapping("getall/{userid}")
	public ResponseEntity<Map<String, Object>> getAll(@PathVariable ("userid") int userId){
		Map<String, Object> result = userService.getUserAndVehicle(userId);
		return ResponseEntity.ok(result);
	}

}
