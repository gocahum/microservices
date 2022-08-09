package com.gocahum.userservice.feignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gocahum.userservice.model.Bike;

@FeignClient(name = "bike-service", path = "/bike")//, url = "http://localhost:8003/bike")
public interface BikeFeignClient {
	@PostMapping
	Bike save(@RequestBody Bike bike);

	@GetMapping("/byuser/{userid}")
	List<Bike> getBikes(@PathVariable("userid") int userId);
}
