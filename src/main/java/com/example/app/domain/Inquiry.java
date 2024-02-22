package com.example.app.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Inquiry {
	
	@Size(min=2, max=15   )
	private String name;
	@Email
	@NotBlank
	private String mail;
	@NotNull
	@Min(99999)
//	@Max(999999999)
	private Integer tel;
	@NotBlank
	private String movetime;
	@NotBlank
	private String about;
	@NotBlank
	private String detail;

	
	
	
	
	
	
	
	
}
