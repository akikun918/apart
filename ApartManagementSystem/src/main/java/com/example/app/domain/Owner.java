package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.app.validation.OwnerSaveGroup;

import jakarta.validation.constraints.Max;
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
public class Owner {
	private Integer id;
	@Size(min = 1, max = 15, groups = { OwnerSaveGroup.class })
	private String name;
//	@NotBlank(groups = { OwnerSaveGroup.class })
	@NotNull(groups = { OwnerSaveGroup.class })
	@Past(groups = { OwnerSaveGroup.class })
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	@NotNull(groups = { OwnerSaveGroup.class })
	// @Min(value= 99999, groups = { OwnerSaveGroup.class })
	@Max(value=10, groups = { OwnerSaveGroup.class })
	private Double ownerBrokerageFee;
	@NotNull(groups = { OwnerSaveGroup.class })
	@Max(value=10, groups = { OwnerSaveGroup.class })
	private Double customerBrokerageFee;
	private LocalDateTime created; 


}
