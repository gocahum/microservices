package com.gocahum.userservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gocahum.userservice.entity.User;
import com.gocahum.userservice.feignClients.BikeFeignClient;
import com.gocahum.userservice.feignClients.CarFeignClient;
import com.gocahum.userservice.model.Bike;
import com.gocahum.userservice.model.Car;
import com.gocahum.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CarFeignClient carFeignClient;
	@Autowired
	BikeFeignClient bikeFeignClient;

	public List<User> getAll() {
		return repository.findAll();
	}

	public User getByUser(int id) {
		return repository.findById(id).orElse(null);
	}

	public User save(User user) {
		User userNew = repository.save(user);
		return userNew;
	}

	public List<Car> getCars(int userId) {
		List<Car> cars = restTemplate.getForObject("http://car-service/car/byuser/" + userId, List.class);
		return cars;
	}

	public List<Bike> getBikes(int userId) {
		List<Bike> bikes = restTemplate.getForObject("http://bike-service/bike/byuser/" + userId, List.class);
		return bikes;
	}

	public Car saveCar(Car car, int userId) {
		car.setUserId(userId);
		Car carNew = carFeignClient.save(car);
		return carNew;
	}

	public Bike saveBike(Bike bike, int userId) {
		bike.setUserId(userId);
		Bike bikeNew = bikeFeignClient.save(bike);
		return bikeNew;
	}

	public Map<String, Object> getUserAndVehicle(int userId) {
		log.info("########## getUserAndVehicle ##########");
		Map<String, Object> result = new HashMap<String, Object>();
		User user = repository.findById(userId).orElse(null);
		if (user == null) {
			result.put("Mensjae", "no Existe el usuario");
			return result;
		}
		result.put("User", user);
		List<Car> cars = carFeignClient.getCars(userId);
		if (cars.isEmpty()) {
			result.put("Cars", "El usuario no tiene carros");
		} else {
			result.put("Cars", cars);
		}
		List<Bike> bikes = bikeFeignClient.getBikes(userId);
		if (bikes.isEmpty()) {
			result.put("Bikes", "El usuario no tiene motos");
		} else {
			result.put("Bikes", bikes);
		}
		return result;

	}

}
