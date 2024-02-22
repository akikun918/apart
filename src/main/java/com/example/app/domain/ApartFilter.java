package com.example.app.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApartFilter {

	private Integer sizeB;
	private Integer sizeT;
	private Integer rentB;
	private Integer rentT;
	private Integer deposit;
	private Integer keyMoney;
	private String free;
	
	private List<String> materialList;
	
	
	
}
