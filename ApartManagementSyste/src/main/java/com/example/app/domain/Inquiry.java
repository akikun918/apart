package com.example.app.domain;

import com.example.app.validation.InquiryGroup;

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

	@Size(min = 2, max = 15, groups = { InquiryGroup.class })
	private String name;
	@Email(groups = { InquiryGroup.class })
	@NotBlank(groups = { InquiryGroup.class })
	@Email(groups = { InquiryGroup.class })
	private String mail;
	@NotNull(groups = { InquiryGroup.class })
	@Min(value = 99999, groups = { InquiryGroup.class })
	//	@Max(999999999)
	private Integer tel;
	@NotBlank(groups = { InquiryGroup.class })
	private String movetime;
	@NotBlank(groups = { InquiryGroup.class })
	private String about;
	@NotBlank(groups = { InquiryGroup.class })
	private String detail;

}
