package com.gocahum.userservice.feignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gocahum.userservice.model.Car;



@FeignClient(name ="car-service", url = "localhost:8002/car")
//@RequestMapping("/car")
public interface CarFeignClient {
	@PostMapping
	Car save(@RequestBody Car user);
	
	@GetMapping("/byuser/{userid}")
	List<Car> getCars(@PathVariable("userid") int userId);

}
