package com.example.app.service;

import com.example.app.domain.Auth;

public interface AuthService {

	boolean isCorrectIdAndPassword(String loginId, String loginPass) throws Exception;

	boolean register(Auth auth) throws Exception;

	Auth selectByLoginId(String loginId) throws Exception;

	void favoriteAdd(String loginId, Integer roomId) throws Exception;

}
