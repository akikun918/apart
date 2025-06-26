package com.example.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomImage {

	private Integer id;
	private String name;
	private Integer apartId;
	private Integer roomId;

}
