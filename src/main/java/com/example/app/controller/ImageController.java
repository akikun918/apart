package com.example.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Image;
import com.example.app.mapper.ImageMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {
	private final ImageMapper mapper;

	@GetMapping("/images")
	public String list(Model model) {
		//一覧取得
		List<Image> imgList = mapper.selectAll();
		model.addAttribute("imgList", imgList);
		return "images";
	}

	@PostMapping("/images")
	public String list(
			@RequestParam MultipartFile upload,
			Model model) throws IllegalStateException, IOException {
		if (upload.isEmpty()) {
			model.addAttribute("msg", "ファイルを指定してください");
		} else {
			//ファイルサイズ
			System.out.println(upload.getSize());
			//ファイル種類
			System.out.println(upload.getContentType());
			//ファイル名
			System.out.println(upload.getOriginalFilename());

			//ファイル名取得
			String imgName = upload.getOriginalFilename();
			//格納場所指定
			File dest = new File("C:/Users/zd3M01/uploads/" + imgName);
			upload.transferTo(dest);//フォルダに保存
	//		mapper.add(imgName);//DBに保存
		}
		return "redirect:/images";
	}

}