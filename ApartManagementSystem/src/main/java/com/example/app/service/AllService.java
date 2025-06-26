package com.example.app.service;

import java.util.List;

import com.example.app.domain.All;

public interface AllService {

	List<All> selectByRoomIdList(List<Integer> roomIdList);

}
