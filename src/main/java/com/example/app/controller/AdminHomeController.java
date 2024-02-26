package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Admin;
import com.example.app.service.AdminService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adminHome")
public class AdminHomeController {

	private final AdminService service;

	@GetMapping
	public String showHome(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminhome";
	}

	@PostMapping
	public String login(
			@Valid Admin admin,
			Errors errors,
			HttpSession session) throws Exception {
		// 入力に不備がある 
		if (errors.hasErrors()) {
			return "adminhome";
		}

		// パスワードが正しくない 
		if (!service.isCorrectIdAndPassword(admin.getLoginId(),
				admin.getLoginPass())) {
			errors.rejectValue("loginId", "error.incorrect_id_password");
			return "adminhome";
		}

		// 正しいログインIDとパスワード 
		// ⇒ セッションにログインIDを格納し、リダイレクト 
		session.setAttribute("adminLoginId", admin.getLoginId());
		return "redirect:/adminHome";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("adminLoginId");
		return "redirect:/adminHome";
	}

}