package com.example.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Room;
import com.example.app.domain.RoomImage;
import com.example.app.mapper.ImageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class RoomImageServiceImpl implements RoomImageService {

	private final ImageMapper imageMapper;
//	private final ApartMapper apartMapper;
	

	@Override
	public void add(Room room, String imageName) {
//		Integer roomId = apartMapper.findRoomIdByNumberAndApartId(room.getNumber(), room.getApartId());
//		RoomImage roomImage = new RoomImage(null, imageName, room.getApartId(), roomId);
		RoomImage roomImage = new RoomImage(null, imageName, room.getApartId(), room.getId());
		imageMapper.add(roomImage);

	}
}
