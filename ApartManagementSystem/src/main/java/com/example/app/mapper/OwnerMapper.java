package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Owner;

@Mapper
public interface OwnerMapper {

	void add(Owner owner);

	List<Owner> selectAll();

	Owner selectById(int id);
	
	void edit(Owner owner);

}
