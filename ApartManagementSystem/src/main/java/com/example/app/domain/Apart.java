package com.example.app.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.app.validation.ApartAddGroup;
import com.example.app.validation.ApartEditGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
	@Size(min = 3, max = 20, groups = { ApartAddGroup.class, ApartEditGroup.class})
	private String name;
	private String station1;
	@NotNull(groups = {ApartAddGroup.class, ApartEditGroup.class})
	@Min(value= 1, groups = { ApartAddGroup.class, ApartEditGroup.class})
	@Max(value= 30, groups = { ApartAddGroup.class, ApartEditGroup.class})
	private Integer far1;
	private String station2;
	private Integer far2;
	private String station3;
	private Integer far3;
	@NotNull(groups = {ApartAddGroup.class, ApartEditGroup.class })
	@Past(groups = {ApartAddGroup.class, ApartEditGroup.class })
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate builtAt;
	@Size(min = 5, max = 45, groups = { ApartAddGroup.class, ApartEditGroup.class })
	private String address1;
	@Size(min = 1, max = 15, groups = { ApartAddGroup.class, ApartEditGroup.class })
	private String address2;
	private String material;
	@Size(min = 2, max = 25, groups = { ApartAddGroup.class, ApartEditGroup.class })
	private String point;
	@Size(min = 2, max = 100, groups = { ApartAddGroup.class, ApartEditGroup.class })
	private String other;
	private int ownerId;
	private String imgName;

	
}