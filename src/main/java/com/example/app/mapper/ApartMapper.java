package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Apart;
import com.example.app.domain.Room;

@Mapper
public interface ApartMapper {

	List<Apart> selectAll();

	void add(Apart apart);
	
	Apart selectById(int id);

	void edit(Apart apart);
	
	void roomAdd(Room room);

	Room selectRoomById(int id);
	
	void roomEdit(Room room);

	Integer findRoomIdByNumberAndApartId(int number, int apartId);

	void roomDeleteByNumberAndApartId(int number, int apartId);

	
}
