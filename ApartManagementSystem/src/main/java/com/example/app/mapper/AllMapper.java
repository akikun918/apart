package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.All;

@Mapper
public interface AllMapper {

	List<All> selectAll();

	// List<Apart> selectByFilter(@Param("list") List<String> materialList,
	// @Param("apart") Apart apart,
	// @Param("apartFilter") ApartFilter apartFilter);

	All selectByRoomId(int id);

	All selectByApartId(int id);

	//	List<Apart> selectByFilter(@Param("station") List<String> stationList, @Param("address1") List<String> address1List,
	//			@Param("material") List<String> materialList, @Param("apart") Apart apart,
	//			@Param("apartFilter") ApartFilter apartFilter, @Param("offset") int offset,
	//			@Param("limit") int limit);

	List<All> selectAllsByFilter(@Param("station") List<String> stationList,
			@Param("address1") List<String> address1List,
			@Param("material") List<String> materialList, @Param("all") All all, @Param("offset") int offset,
			@Param("limit") int limit);

	long countSelectAllsByFilter(@Param("station") List<String> stationList,
			@Param("address1") List<String> address1List,
			@Param("material") List<String> materialList, @Param("all") All all);

	//	long countSelectByFilter(@Param("station") List<String> stationList, @Param("address1") List<String> address1List,
	//			@Param("material") List<String> materialList, @Param("apart") Apart apart,
	//			@Param("apartFilter") ApartFilter apartFilter);

	List<All> selectAllsByStation(@Param("stationList") List<String> stationList, @Param("offset") int offset,
			@Param("limit") int limit);

	List<All> selectAllsByAddress1(@Param("address1List") List<String> address1List, @Param("offset") int offset,
			@Param("limit") int limit);

	long countSelectAllsByStation(List<String> stationList);
	//	List<Apart> countSelectByStation(@Param("stationList") List<String> stationList);

	long countSelectAllsByAddress1(List<String> address1List);

}
