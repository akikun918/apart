package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

	@GetMapping
	public String menu() {

		return "menu";
	}

	@GetMapping("/1")
	public String a() {

		return "menu1";
	}

	@GetMapping("/mainta")
	public String b() {

		return "mainta";
	}

	@GetMapping("/2")
	public String c() {

		return "menu2";
	}
}
