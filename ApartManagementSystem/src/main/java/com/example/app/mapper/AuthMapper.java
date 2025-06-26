package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Auth;

@Mapper
public interface AuthMapper {

	Auth selectByLoginId(String loginId) throws Exception;
	
	void register(Auth auth);
	
	void favoriteAdd(Auth auth );


	
	
//	
//	List<Apart> selectAll();
//
//	void add(Apart apart);
//	
//	Apart selectById(int id);
//
//	void edit(Apart apart);
//	
//	void roomAdd(Room room);
//
//	Room selectRoomById(int id);
//	
//	void roomEdit(Room room);


}
