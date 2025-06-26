package com.example.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.All;
import com.example.app.mapper.AllMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AllServiceImpl implements AllService {

	private final AllMapper allMapper;

	@Override
	public List<All> selectByRoomIdList(List<Integer> roomIdList) {
		List<All> allList = new ArrayList<All>();
		System.out.println(allList);
		for (Integer roomId : roomIdList) {
			All all = allMapper.selectByRoomId(roomId);
			if (all != null) {
				allList.add(all);
			}
		}

		return allList;
	}
}
