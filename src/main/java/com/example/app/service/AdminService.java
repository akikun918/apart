package com.example.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Room;

public interface AdminService {

	boolean sameRoomNumber(Room room);

	boolean noRoomImages(List<String> deleteImgNameList, MultipartFile upload, Integer roomImageListSize);

	  boolean isCorrectIdAndPassword(String loginId, String loginPass) ; 
	
}
