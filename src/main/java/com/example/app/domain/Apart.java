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
public class Apart {

	private Integer id;
	private String name;
	private String station1;
	private Integer far1;
	private String station2;
	private Integer far2;
	private String station3;
	private Integer far3;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate builtAt;
	private String address1;
	private String address2;
	private String material;
	private String point;
	private String other;
	private int ownerId;
	private String imgName;

	
}