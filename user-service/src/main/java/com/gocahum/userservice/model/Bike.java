package com.gocahum.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
	private String brnad;
	private String model;
	private int userId;

}
