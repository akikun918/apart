package com.example.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
import com.example.app.service.AdminService;
import com.example.app.service.RoomImageService;
import com.example.app.validation.ApartAddGroup;
import com.example.app.validation.ApartEditGroup;
import com.example.app.validation.OwnerSaveGroup;
import com.example.app.validation.RoomSaveGroup;

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
	private final RoomImageService roomImageService;
	private final AdminService adminService;
	private final HttpSession session;

//	@GetMapping("/menu")
//	public String menu(Model model) {
//
//		return "admin/menu";
//	}

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
	public String add(@Validated(OwnerSaveGroup.class) Owner owner, Errors errors, Model model, RedirectAttributes rd) {
		if (errors.hasErrors()) {
			List<ObjectError> objList = errors.getAllErrors();
			for (ObjectError obj : objList) {
				System.out.println(obj.toString());
			}
			model.addAttribute("title", "オーナー新規登録");

			return "admin/ownerSave";
		}

		System.out.println(owner);
		ownerMapper.add(owner);
		rd.addFlashAttribute("statusMessage", "オーナーを新規登録しました。");

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
	public String ownerEdit(@Validated(OwnerSaveGroup.class) Owner owner, Errors errors, Model model,
			RedirectAttributes rd) {
		if (errors.hasErrors()) {
			List<ObjectError> objList = errors.getAllErrors();
			for (ObjectError obj : objList) {
				System.out.println(obj.toString());
			}
			model.addAttribute("title", "オーナー情報編集");
			return "admin/ownerSave";
		}
		System.out.println(owner);
		ownerMapper.edit(owner);
		rd.addFlashAttribute("statusMessage", "オーナー情報を編集しました。");

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
		model.addAttribute("owners", ownerMapper.selectAll());
		model.addAttribute("title", "アパート新規登録");

		return "admin/apartSave";

	}

	@PostMapping("/apartAdd")
	public String apartAdd(Model model, @Validated(ApartAddGroup.class) Apart apart, Errors errors,
			@RequestParam MultipartFile upload, RedirectAttributes rd) throws IllegalStateException, IOException {
		// if (upload.isEmpty()) {
		// errors.rejectValue("imgName", "test");
		// // model.addAttribute("msg", "ファイルを指定してください");
		// // return "admin/apartSave";
		// }
		if (upload.isEmpty()) {
			errors.reject("noUpload");
		}
		if (errors.hasErrors()) {
			model.addAttribute("owners", ownerMapper.selectAll());
			model.addAttribute("title", "アパート新規登録");
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
		rd.addFlashAttribute("statusMessage", "アパートを新規登録しました。");

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
	public String apartEdit(@Validated(ApartEditGroup.class) Apart apart, Errors errors, Model model,
			@RequestParam MultipartFile upload, RedirectAttributes rd) throws IllegalStateException, IOException {
		if (errors.hasErrors()) {
			model.addAttribute("owners", ownerMapper.selectAll());
			model.addAttribute("title", "アパート情報編集");
			return "admin/apartSave";
		}

		if (!upload.isEmpty()) {
			String imgName = upload.getOriginalFilename();
			apart.setImgName(imgName);
			// //格納場所指定
			File dest = new File("C:/Users/zd3M01/uploads/" + apart.getImgName());
			upload.transferTo(dest);// フォルダに保存
		}

		apartMapper.edit(apart);
		rd.addFlashAttribute("statusMessage", "アパート情報を編集しました。");
		return "redirect:/admin/apartList";
	}

	////////////////////// アパート編集終わり///////////////////////////////////////////////

	////////////////////// 部屋編集///////////////////////////////////////////////
	@GetMapping("/roomList")
	public String roomList(@RequestParam(name = "id", required = false) Integer id, Model model) {
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
	public String roomAdd(Model model, @Validated(RoomSaveGroup.class) Room room, Errors errors, MultipartFile upload,
			RedirectAttributes rd) throws IllegalStateException, IOException {
		All all = allMapper.selectByApartId(room.getApartId());

		// エラー処理
		// 写真をアップロードしてないケース、エラーがあるケース、部屋番号が被ったケース
		if (upload.isEmpty() || errors.hasErrors() || adminService.sameRoomNumber(room)) {
			model.addAttribute("all", all);
			model.addAttribute("title", "お部屋の新規登録");

			// 入力した部屋番号がすでに存在するか確認
			if (adminService.sameRoomNumber(room)) {
				errors.rejectValue("number", "sameNumber");
			}

			// 写真をアップロードしてない場合
			if (upload.isEmpty()) {
				errors.reject("noUpload");
			}
			return "admin/roomSave";
		}

		apartMapper.roomAdd(room);

		// 写真をCドライブとデータベースに保存
		try {
			String name = upload.getOriginalFilename();
			File dest = new File("C:/Users/zd3M01/uploads/" + name);
			upload.transferTo(dest);

			roomImageService.add(room, name);
			// ↓いらなくなった
			// Integer roomId = apartMapper.findRoomIdByNumberAndApartId(room.getNumber(),
			// room.getApartId());
			// RoomImage roomImage = new RoomImage(null, name, room.getApartId(), roomId);
			// imageMapper.add(roomImage);
		} catch (Exception e) {
			apartMapper.roomDeleteByNumberAndApartId(room.getNumber(), room.getApartId());
		}
		rd.addFlashAttribute("statusMessage", "部屋を新規登録しました。");

		return "redirect:/admin/roomList?id=" + room.getApartId();
	}

	@GetMapping("/roomEdit")
	public String roomEdit(@RequestParam(name = "id", required = false) Integer id, Model model) {
		if (id == null) {
			return "redirect:/admin/roomList";
		}

		Room room = apartMapper.selectRoomById(id);
		All all = allMapper.selectByApartId(room.getApartId());

		// ↓RoomのroomImageListをPostに飛ばすためsessionを使う
		///////////////////////////////////////////////////////////////

		session.setAttribute("exRoom", room);

		///////////////////////////////////////////////////////////////

		model.addAttribute("title", "お部屋の編集");
		model.addAttribute("all", all);
		model.addAttribute("room", room);
		System.out.println(room);

		return "admin/roomSave";
	}

	@PostMapping("/roomEdit")
	public String roomEdit(@RequestParam(name = "deleteImgNameList", required = false) List<String> deleteImgNameList,
			Model model, @Validated(RoomSaveGroup.class) Room room, Errors errors, MultipartFile upload,
			RedirectAttributes rd) throws IllegalStateException, IOException {
		All all = allMapper.selectByApartId(room.getApartId());
		// ↓Getからsessionで飛ばしたroomImageListを入手、セット
		Room exRoom = (Room) session.getAttribute("exRoom");
		room.setRoomImageList(exRoom.getRoomImageList());

		// エラー処理
		// エラーがあるケース、部屋番号が被ったケース、写真を全部消したケース
		if (errors.hasErrors() || adminService.sameRoomNumber(room)
				|| adminService.noRoomImages(deleteImgNameList, upload, room.getRoomImageList().size())) {
			model.addAttribute("title", "お部屋の編集");
			model.addAttribute("all", all);

			// 入力した部屋番号がすでに存在するか確認
			if (adminService.sameRoomNumber(room)) {
				errors.rejectValue("number", "sameNumber");
			}
			// 写真を全部消去かつ新規アップロードしていない場合
			if (adminService.noRoomImages(deleteImgNameList, upload, room.getRoomImageList().size())) {
				errors.reject("noPicture");
			}
			return "admin/roomSave";
		}

		apartMapper.roomEdit(room);

		// 写真の削除を選んだ場合のコード
		if (deleteImgNameList != null) {
			// 写真の削除
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
			// RoomImage roomImage = new RoomImage(null, name, room.getApartId(),
			// room.getId());
			// imageMapper.add(roomImage);

			roomImageService.add(room, name);
		}
		rd.addFlashAttribute("statusMessage", "部屋情報を編集しました。");
		session.removeAttribute("exRoom");

		return "redirect:/admin/roomList?id=" + room.getApartId();
	}
}