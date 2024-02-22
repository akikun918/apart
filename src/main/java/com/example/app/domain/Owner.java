package com.example.app.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Owner {


	private Integer id;
	private String name;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;
	private double ownerBrokerageFee;
	private double customerBrokerageFee;
	
	
	
}
