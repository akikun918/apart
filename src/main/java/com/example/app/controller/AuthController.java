package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Auth;
import com.example.app.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final HttpSession session;
	private final AuthService authService;

	@GetMapping("/register")
	public String register(Model model) {
		if (session.getAttribute("loginId") != null) {
			return "redirect:/";
		}

		model.addAttribute("auth", new Auth());
		return "auth/register";
	}

	@PostMapping("/register")
	public String register(Model model, @Valid Auth auth, Errors errors) throws Exception {
		// 下のauthService.register(auth)は必ず実行される
		if (!authService.register(auth) || errors.hasErrors()) {
			if (!authService.register(auth)) {
				//hasErrors()のloginIdのエラーに追加される
				errors.rejectValue("loginId", "authSameId");
			}
			return "auth/register";
		}

		session.setAttribute("loginId", auth.getLoginId());

		model.addAttribute("title", "会員登録完了");
		return "auth/authDone";

	}

	@GetMapping("/login")
	public String menu(Model model) {
		if (session.getAttribute("loginId") != null) {
			model.addAttribute("title", "ログイン済みです");
			return "auth/login";
		}

		model.addAttribute("title", "ログイン");
		model.addAttribute("auth", new Auth());

		return "auth/login";
	}

	@PostMapping("/login")
	public String login(Model model, @Valid Auth auth, Errors errors) throws Exception {
		// !authService.isCorrectIdAndPassword(auth)は必ず実行される
		if (!authService.isCorrectIdAndPassword(auth.getLoginId(), auth.getLoginPass()) || errors.hasErrors()) {
			// パスワードが正しくない
			if (!authService.isCorrectIdAndPassword(auth.getLoginId(), auth.getLoginPass())) {
				//hasErrors()のloginIdのエラーに追加される
				errors.rejectValue("loginId", "error.incorrect_id_password");
			}
			model.addAttribute("title", "ログイン");
			return "auth/login";
		}
//		// パスワードが正しくない
//		if (!authService.isCorrectIdAndPassword(auth)) {
//			errors.rejectValue("loginId", "error.incorrect_id_password");
//			model.addAttribute("title", "ログイン");
//			return "auth/login";
//		}
		// 正しいログインIDとパスワード
		// ⇒ セッションにログインIDを格納し、リダイレクト
		session.setAttribute("loginId", auth.getLoginId());
		model.addAttribute("title", "ログインしました");

		return "auth/authDone";
	}

}
