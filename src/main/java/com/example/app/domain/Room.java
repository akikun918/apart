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
public class Room {
	
	
	private Integer id;
	private Integer apartId;
	private Integer number;
	private Integer active;
	private Integer price;
	private Integer deposit;
	private Integer keyMoney;
	private Integer size;
	private Integer floor;
	private Integer sepToilet;
	private String point;
	private String other;	
	private List<RoomImage> roomImageList;

	
	
	
	
	
	
}
