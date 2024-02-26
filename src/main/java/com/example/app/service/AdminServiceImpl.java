package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Admin;
import com.example.app.domain.All;
import com.example.app.domain.Room;
import com.example.app.mapper.AdminMapper;
import com.example.app.mapper.AllMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AllMapper allMapper;
	private final AdminMapper adminMapper;

	@Override
	public boolean sameRoomNumber(Room room) {
		All all = allMapper.selectByApartId(room.getApartId());
		if (all != null) {
			for (Room room1 : all.getRoomList()) {
				if (room1.getNumber().equals(room.getNumber())) {
					if (!room1.getId().equals(room.getId())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean noRoomImages(List<String> deleteImgNameList, MultipartFile upload, Integer roomImageListSize) {

		if (deleteImgNameList != null) {
			if (upload.isEmpty() && roomImageListSize == deleteImgNameList.size()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isCorrectIdAndPassword(String loginId, String loginPass) {
		Admin admin = adminMapper.selectByLoginId(loginId);

		// ログインIDが正しいかチェック
		// ⇒ ログインIDが正しくなければ、管理者データは取得されない
		if (admin == null) {
			return false;
		}

		// パスワードが正しいかチェック
		if (!BCrypt.checkpw(loginPass, admin.getLoginPass())) {
			return false;
		}

		return true;
	}
}
