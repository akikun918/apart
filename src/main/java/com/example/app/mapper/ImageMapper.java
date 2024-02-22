package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Image;
import com.example.app.domain.RoomImage;

@Mapper
public interface ImageMapper {
	//一覧
	List<Image> selectAll();

	//新規登録
	void add(RoomImage roomImage);

//	void roomNumberEdit(@Param("roomImage")RoomImage roomImage, @Param("number") Integer number );
	
	void delete(RoomImage roomImage);
	
	
//	void roomNumberEdit(@Param("stationList") List<String> stationList, @Param("offset") int offset,
//			@Param("limit") int limit);
//
//	
//	
//	List<Image> aaa(@Param("stationList") List<String> stationList, @Param("offset") int offset,
//			@Param("limit") int limit);
//
//	
	
}