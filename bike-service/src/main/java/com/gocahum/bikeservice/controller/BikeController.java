package com.gocahum.bikeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gocahum.bikeservice.entity.Bike;
import com.gocahum.bikeservice.service.BikeService;

@RestController
@RequestMapping("/bike")
public class BikeController {

	@Autowired
	BikeService bikeService;

	@GetMapping
	public ResponseEntity<List<Bike>> getAll() {
		List<Bike> users = bikeService.getAll();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bike> getById(@PathVariable("id") int id) {
		Bike bike = bikeService.getByBikeId(id);
		if (bike == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bike);
	}

	@PostMapping
	public ResponseEntity<Bike> save(@RequestBody Bike user) {
		Bike bikeNew = bikeService.save(user);
		if (bikeNew == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bikeNew);
	}
	
	@GetMapping("/byuser/{userid}")
	public ResponseEntity<List<Bike>> getUserId(@PathVariable("userid") int userId) {
		List<Bike> bikes = bikeService.byUserId(userId);
		if (bikes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bikes);
	}

}
