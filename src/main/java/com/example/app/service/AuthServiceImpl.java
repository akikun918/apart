package com.example.app.service;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Auth;
import com.example.app.mapper.AuthMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthMapper authMapper;

	@Override
	public boolean isCorrectIdAndPassword(String loginId, String loginPass) throws Exception {

		Auth auth = authMapper.selectByLoginId(loginId);
		// ログインIDが正しいかチェック
		// ⇒ ログインIDが正しくなければ、管理者データは取得されない
		if (auth == null) {
			return false;
		}

		//		 パスワードが正しいかチェック
		if (!BCrypt.checkpw(loginPass, auth.getLoginPass())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean register(Auth auth) throws Exception {
		if (auth.getLoginId().isBlank() || auth.getLoginPass().isBlank()) {
			return false;
		}

		String hashed = BCrypt.hashpw(auth.getLoginPass(), BCrypt.gensalt());
		auth.setLoginPass(hashed);
		auth.setFRoomId1(0);

		try {
			authMapper.register(auth);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public Auth selectByLoginId(String loginId) throws Exception {
		Auth auth = authMapper.selectByLoginId(loginId);
		auth.setFRoomIdList(new ArrayList<Integer>());

		if (auth.getFRoomId1() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId1());
		}
		if (auth.getFRoomId2() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId2());
		}

		if (auth.getFRoomId3() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId3());
		}
		if (auth.getFRoomId4() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId4());
		}
		if (auth.getFRoomId5() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId5());
		}
		if (auth.getFRoomId6() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId6());
		}
		if (auth.getFRoomId7() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId7());
		}
		if (auth.getFRoomId8() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId8());
		}
		if (auth.getFRoomId9() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId9());
		}
		if (auth.getFRoomId10() != 0) {
			auth.getFRoomIdList().add(auth.getFRoomId10());
		}

		return auth;
	}

	@Override
	public void favoriteAdd(String loginId, Integer roomId) throws Exception {
		Auth auth = authMapper.selectByLoginId(loginId);

		auth.setFRoomId10(auth.getFRoomId9());
		auth.setFRoomId9(auth.getFRoomId8());
		auth.setFRoomId8(auth.getFRoomId7());
		auth.setFRoomId7(auth.getFRoomId6());
		auth.setFRoomId6(auth.getFRoomId5());
		auth.setFRoomId5(auth.getFRoomId4());
		auth.setFRoomId4(auth.getFRoomId3());
		auth.setFRoomId3(auth.getFRoomId2());
		auth.setFRoomId2(auth.getFRoomId1());
		auth.setFRoomId1(roomId);

		authMapper.favoriteAdd(auth);
	}
}
