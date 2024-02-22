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
public class All {
	private Integer id;
	//↓データベースにoldはなく、ApartのbuiltAt(建設年月日)からApartMapper.xmlで計算して代入している
	private Integer old;
	private Apart apart;
	private List<Room> roomList;
	private Owner owner;
	private Image image;
	private ApartFilter apartFilter;
	
//	private Integer id;
//	private String name;
//	private String station1;
//	private Integer far1;
//	private String station2;
//	private Integer far2;
//	private String station3;
//	//	private List<String> stationList;
//	private Integer far3;
//
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private LocalDate builtAt;
//	//データベースにoldはなく、builtAtからApartMapper.xmlで計算して代入している
//	private Integer old;
//	private String address1;
//	private String address2;
//	private String material;
//	//	private List<String> materialList;
//	private String point;
//	private String other;
//	private int ownerId;
//	//	private Room room;
//	private List<Room> roomList;
//	private Owner owner;
//	//	private List<Image> imageList;
//	private Image image;
//
//	private ApartFilter apartFilter;

}
