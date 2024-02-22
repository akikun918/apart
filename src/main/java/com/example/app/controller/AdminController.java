package com.example.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.All;
import com.example.app.domain.Apart;
import com.example.app.domain.Owner;
import com.example.app.domain.Room;
import com.example.app.domain.RoomImage;
import com.example.app.mapper.AllMapper;
import com.example.app.mapper.ApartMapper;
import com.example.app.mapper.ImageMapper;
import com.example.app.mapper.OwnerMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final AllMapper allMapper;
	private final OwnerMapper ownerMapper;
	private final ApartMapper apartMapper;
	private final ImageMapper imageMapper;
	private final HttpSession session;

	@GetMapping("/menu")
	public String menu(Model model) {

		return "admin/menu";
	}

	////////////////////// オーナー編集//////////////////////////////////
	@GetMapping("/ownerList")
	public String ownerList(Model model) {

		List<Owner> owners = ownerMapper.selectAll();
		model.addAttribute("owners", owners);

		return "admin/ownerList";
	}

	@GetMapping("/ownerAdd")
	public String add(Model model) {

		model.addAttribute("owner", new Owner());
		model.addAttribute("title", "オーナー新規登録");

		return "admin/ownerSave";
	}

	@PostMapping("/ownerAdd")
	public String add(
			// @Valid Player player,
			Owner owner,
			// Errors errors,
			Model model) {
		// if(errors.hasErrors()) {
		// return "admin/ownerSave";
		// }
		System.out.println(owner);
		ownerMapper.add(owner);
		return "redirect:/admin/ownerList";

	}

	@GetMapping("/ownerEdit")
	public String ownerEdit(@RequestParam(name = "id", required = false) Integer id, Model model) {
		if (id == null) {
			return "redirect:/admin/menu";
		}
		Owner owner = ownerMapper.selectById(id);
		System.out.println(owner);
		model.addAttribute("owner", owner);
		model.addAttribute("title", "オーナー情報編集");

		return "admin/ownerSave";
	}

	@PostMapping("/ownerEdit")
	public String ownerEdit(
			// @Valid
			Owner owner,
			// Errors errors,
			Model model) {
		// if (errors.hasErrors()) {
		// return "admin/ownerSave";
		// }
		System.out.println(owner);
		ownerMapper.edit(owner);
		return "redirect:/admin/ownerList";
	}
	//////////////////////// オーナー編集終わり////////////////////////////////////

	////////////////////// アパート編集//////////////////////////////////

	@GetMapping("/apartList")
	public String apartList(Model model) {

		List<Apart> aparts = apartMapper.selectAll();
		model.addAttribute("aparts", aparts);

		return "admin/apartList";
	}

	@GetMapping("/apartAdd")
	public String apartAdd(Model model) {

		model.addAttribute("apart", new Apart());
		// model.addAttribute("image", new Image());
		model.addAttribute("owners", ownerMapper.selectAll());
		model.addAttribute("title", "アパート新規登録");

		return "admin/apartSave";

	}

	@PostMapping("/apartAdd")
	public String apartAdd(Model model, Apart apart, @RequestParam MultipartFile upload)
			throws IllegalStateException, IOException {
		if (upload.isEmpty()) {
			model.addAttribute("msg", "ファイルを指定してください");
			model.addAttribute("owners", ownerMapper.selectAll());
			return "admin/apartSave";
		}
		// ファイルサイズ
		System.out.println(upload.getSize());
		// ファイル種類
		System.out.println(upload.getContentType());
		// ファイル名
		System.out.println(upload.getOriginalFilename());

		// ファイル名取得
		String imgName = upload.getOriginalFilename();
		apart.setImgName(imgName);
		// //格納場所指定
		File dest = new File("C:/Users/zd3M01/uploads/" + apart.getImgName());
		upload.transferTo(dest);// フォルダに保存
		// imageMapper.add(image);//DBに保存

		apartMapper.add(apart);

		return "redirect:/admin/apartList";

	}

	@GetMapping("/apartEdit")
	public String apartEdit(@RequestParam(name = "id", required = false) Integer id, Model model) {
		if (id == null) {
			return "redirect:/admin/menu";
		}
		Apart apart = apartMapper.selectById(id);
		model.addAttribute("apart", apart);
		model.addAttribute("owners", ownerMapper.selectAll());
		model.addAttribute("title", "アパート情報編集");
		return "admin/apartSave";
	}

	@PostMapping("/apartEdit")
	public String apartEdit(
			// @Valid
			Apart apart, Model model,
			// Errors errors,
			@RequestParam(name = "upload", required = false) MultipartFile upload)
			throws IllegalStateException, IOException {

		if (!upload.isEmpty()) {
			String imgName = upload.getOriginalFilename();
			apart.setImgName(imgName);
			// //格納場所指定
			File dest = new File("C:/Users/zd3M01/uploads/" + apart.getImgName());
			upload.transferTo(dest);// フォルダに保存
		}

		apartMapper.edit(apart);
		return "redirect:/admin/apartList";
	}

	////////////////////// アパート編集終わり///////////////////////////////////////////////

	////////////////////// 部屋編集///////////////////////////////////////////////
	@GetMapping("/roomList")
	public String roomList(@RequestParam(name = "id", required = false) Integer id, Model model
	) {
		if (id == null) {
			return "redirect:/admin/apartList";
		}

		All all = allMapper.selectByApartId(id);
		
		model.addAttribute("all", all);
		model.addAttribute("id", id);

		return "admin/roomList";
	}

	@GetMapping("/roomAdd")
	public String roomAdd(@RequestParam(name = "id", required = false) Integer id, Model model) {
		if (id == null) {
			return "redirect:/admin/menu";
		}
		All all = allMapper.selectByApartId(id);
		model.addAttribute("all", all);

		Room room = new Room();
		room.setApartId(id);
		room.setActive(1);
		room.setSepToilet(0);
		model.addAttribute("title", "お部屋の新規登録");
		model.addAttribute("room", room);
		return "admin/roomSave";
	}

	@PostMapping("/roomAdd")
	public String roomAdd(Model model, Room room, MultipartFile upload) throws IllegalStateException, IOException {
		if (upload.isEmpty()) {
			All all = allMapper.selectByApartId(room.getApartId());
			model.addAttribute("all", all);
			model.addAttribute("noPicture", "ファイルを指定してください");
			return "admin/roomSave";
		}

		All all = allMapper.selectByApartId(room.getApartId());
		if (all != null) {
			for (Room room1 : all.getRoomList()) {
				if (room1.getNumber() == room.getNumber()) {
					model.addAttribute("all", all);
					model.addAttribute("noPicture", "部屋番号がすでに登録されています");
					return "admin/roomSave";
				}
			}
		}

		apartMapper.roomAdd(room);

		// 写真をCドライブとデータベースに保存
		try {
			String name = upload.getOriginalFilename();
			File dest = new File("C:/Users/zd3M01/uploads/" + name);
			upload.transferTo(dest);

			// ↓写真を登録する際、roomを登録した際にデータベースで作られたidが必要
			Integer roomId = apartMapper.findRoomIdByNumberAndApartId(room.getNumber(), room.getApartId());
			RoomImage roomImage = new RoomImage(null, name, room.getApartId(), roomId);
			imageMapper.add(roomImage);
		} catch (Exception e) {
			apartMapper.roomDeleteByNumberAndApartId(room.getNumber(), room.getApartId());
		}

		return "redirect:/admin/roomList?id=" + room.getApartId();
	}

	@GetMapping("/roomEdit")
	public String roomEdit(@RequestParam(name = "id", required = false) Integer id, Model model) {
		if (id == null) {
			return "redirect:/admin/roomList";
		}

		// Postからraで飛ばした
		String noPicture = (String) model.getAttribute("noPicture");
		if (noPicture != null) {
			model.addAttribute("noPicture", noPicture);
		}

		Room room = apartMapper.selectRoomById(id);
		System.out.println(room);
		All all = allMapper.selectByApartId(room.getApartId());

		model.addAttribute("roomImageListSize", room.getRoomImageList().size());
		model.addAttribute("title", "お部屋の編集");
		model.addAttribute("all", all);
		model.addAttribute("room", room);
		return "admin/roomSave";
	}

	@PostMapping("/roomEdit")
	public String roomEdit(RedirectAttributes ra,
			@RequestParam(name = "roomImageListSize", required = false) Integer roomImageListSize,
			@RequestParam(name = "deleteImgNameList", required = false) List<String> deleteImgNameList, Model model,
			Room room, MultipartFile upload) throws IllegalStateException, IOException {
		
		apartMapper.roomEdit(room);

		// 写真の削除を選んだ場合、削除
		if (deleteImgNameList != null) {
			//写真を全部消さないように
			if (upload.isEmpty() && roomImageListSize == deleteImgNameList.size()) {
				ra.addFlashAttribute("noPicture", "写真をすべて消す場合は新たにアップロードしてください");
				return "redirect:/admin/roomEdit?id=" + room.getId();
			}
			//写真の削除
			for (String deleteImgName : deleteImgNameList) {
				// 写真を削除するコード。違う部屋で被ってる写真があるから、コメントアウト
				// File dest = new File("C:/Users/zd3M01/uploads/" + deleteImgName);
				// dest.delete();
				RoomImage roomImage = new RoomImage(null, deleteImgName, room.getApartId(), room.getId());
				imageMapper.delete(roomImage);
			}
		}
		
		if (!upload.isEmpty()) {
			String name = upload.getOriginalFilename();
			File dest = new File("C:/Users/zd3M01/uploads/" + name);
			upload.transferTo(dest);
			RoomImage roomImage = new RoomImage(null, name, room.getApartId(), room.getId());
			imageMapper.add(roomImage);
		}

		return "redirect:/admin/roomList?id=" + room.getApartId();
	}
}