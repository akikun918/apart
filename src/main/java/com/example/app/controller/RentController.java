package com.example.app.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.All;
import com.example.app.domain.Auth;
import com.example.app.domain.Inquiry;
import com.example.app.mapper.AllMapper;
import com.example.app.service.AllService;
import com.example.app.service.AuthService;
import com.example.app.validation.InquiryGroup;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rent")
public class RentController {

	private final AllMapper allMapper;
	private final HttpSession session;
	private final AuthService authService;
	private final AllService allService;
	private static final int NUM_PER_PAGE = 10;

	@GetMapping
	public String index() {
		session.removeAttribute("address1List");
		session.removeAttribute("all");
		session.removeAttribute("stationList");

		return "rent/index";
	}

	@GetMapping("/stationShow")
	public String areaShow(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "station", required = false) List<String> stationList, Model model) {
		if (stationList == null && session.getAttribute("stationList") == null) {
			return "redirect:/rent";
		}
		session.removeAttribute("address1List");
		session.removeAttribute("all");

		if (stationList != null) {
			session.setAttribute("stationList", stationList);
		} else {
			stationList = (List<String>) session.getAttribute("stationList");
		}

		int offset = NUM_PER_PAGE * (page - 1);
		List<All> filteredAlls = allMapper.selectAllsByStation(stationList, offset, NUM_PER_PAGE);
		model.addAttribute("filteredAlls", filteredAlls);

		double totalNum = (double) allMapper.countSelectAllsByStation(stationList);
		int totalPage = (int) Math.ceil(totalNum / NUM_PER_PAGE);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page);

		model.addAttribute("all", new All());
		// model.addAttribute("station", "1");

		return "rent/show2";
	}

	@GetMapping("/areaShow")
	public String show(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "address1", required = false) List<String> address1List, Model model) {
		if (address1List == null && session.getAttribute("address1List") == null) {
			return "redirect:/rent";
		}
		session.removeAttribute("stationList");
		session.removeAttribute("all");

		if (address1List != null) {
			session.setAttribute("address1List", address1List);
		} else {
			address1List = (List<String>) session.getAttribute("address1List");
		}

		int offset = NUM_PER_PAGE * (page - 1);
		List<All> filteredAlls = allMapper.selectAllsByAddress1(address1List, offset, NUM_PER_PAGE);
		model.addAttribute("filteredAlls", filteredAlls);

		double totalNum = (double) allMapper.countSelectAllsByAddress1(address1List);
		System.out.println(totalNum);
		int totalPage = (int) Math.ceil(totalNum / NUM_PER_PAGE);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page);

		model.addAttribute("all", new All());
		// model.addAttribute("address1", "1");

		return "rent/show2";
	}

	@GetMapping("/filteredShow")
	public String filteredShow(@RequestParam(name = "pressForm", required = false) Integer pressForm,
			@RequestParam(name = "page", defaultValue = "1") Integer page, All all, Model model) {
		if (session.getAttribute("stationList") == null && session.getAttribute("address1List") == null) {
			return "redirect:/rent";
		}

		List<String> stationList = (List<String>) session.getAttribute("stationList");
		List<String> address1List = (List<String>) session.getAttribute("address1List");

		if (pressForm == null) {
			if (session.getAttribute("all") != null) {
				all = (All) session.getAttribute("all");
				//////////////////////////////////////////////////

				model.addAttribute("all", all);
				//////////////////////////////////////////////////

				int offset = NUM_PER_PAGE * (page - 1);
				List<All> filteredAlls = allMapper.selectAllsByFilter(stationList, address1List,
						all.getApartFilter().getMaterialList(), all, offset, NUM_PER_PAGE);
				model.addAttribute("filteredAlls", filteredAlls);

				double totalNum = (double) allMapper.countSelectAllsByFilter(stationList, address1List,
						all.getApartFilter().getMaterialList(), all);

				System.out.println("4");

				int totalPage = (int) Math.ceil(totalNum / NUM_PER_PAGE);
				model.addAttribute("totalPage", totalPage);
				model.addAttribute("page", page);

				return "rent/show2";
			}
		}

		if (all.getApartFilter().getDeposit() == null) {
			all.getApartFilter().setDeposit(1000000);
		}
		if (all.getApartFilter().getKeyMoney() == null) {
			all.getApartFilter().setKeyMoney(1000000);
		}

		session.setAttribute("all", all);

		int offset = NUM_PER_PAGE * (page - 1);
		List<All> filteredAlls = allMapper.selectAllsByFilter(stationList, address1List,
				all.getApartFilter().getMaterialList(), all, offset, NUM_PER_PAGE);
		model.addAttribute("filteredAlls", filteredAlls);

		System.out.println("1");
		System.out.println(filteredAlls);
		System.out.println("2");

		
		

		double totalNum = (double) allMapper.countSelectAllsByFilter(stationList, address1List,
				all.getApartFilter().getMaterialList(), all);
		int totalPage = (int) Math.ceil(totalNum / NUM_PER_PAGE);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page);

		return "rent/show2";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "fav", required = false) Integer fav, Model model) throws Exception {
		if (id == null) {
			return "redirect:/rent";
		}

		if (fav != null) {
			if (session.getAttribute("loginId") == null) {
				model.addAttribute("fav", "ログインしてください");
				model.addAttribute("title", "ログイン");
				model.addAttribute("auth", new Auth());
				return "auth/login";
			}
			String loginId = (String) session.getAttribute("loginId");
			authService.favoriteAdd(loginId, id);
		}

		if (session.getAttribute("loginId") != null) {
			String loginId = (String) session.getAttribute("loginId");
			Auth auth = authService.selectByLoginId(loginId);
			for (Integer roomId : auth.getFRoomIdList()) {
				if (roomId == id) {
					model.addAttribute("favoriteAdded", "favoriteAdded");
				}

			}

		}

		All all = allMapper.selectByRoomId(id);

		// List<Apart> aparts = apartMapper.selectByRoomId(id);
		model.addAttribute("all", all);

		return "rent/detail2";
	}

	@GetMapping("/inquiry")
	public String inquiry(@RequestParam(name = "id", required = false) Integer id, Model model) {
		if (id == null) {
			return "redirect:/rent";
		}

		All all = allMapper.selectByRoomId(id);

		model.addAttribute("all", all);
		model.addAttribute("inquiry", new Inquiry());

		// Event event = service.getEventById(id);
		// model.addAttribute("event", event);
		return "rent/inquiry";
	}

	@PostMapping("/inquiry")
	public String inquiry(RedirectAttributes ra, All all, @RequestParam(name = "name", required = false) String name,
			Model model,
			@Validated(InquiryGroup.class) Inquiry inquiry, Errors errors) {
		if (errors.hasErrors()) {
			List<ObjectError> objList = errors.getAllErrors();
			for(ObjectError obj : objList) {
			System.out.println(obj.toString());
			}
			// エラーがあったときの処理
			all = allMapper.selectByApartId(all.getApart().getId());
			model.addAttribute("all", all);
			return "rent/inquiry";
		}

		ra.addFlashAttribute("inquiry", inquiry);

		return "redirect:/rent/inquiryDone";
	}

	@GetMapping("/inquiryDone")
	public String inquiryDone(Model model) {
		if (model.getAttribute("inquiry") == null) {
			return "redirect:/rent";
		}

		Inquiry inquiry = (Inquiry) model.getAttribute("inquiry");

		model.addAttribute("inquiry", inquiry);
		
		return "rent/inquiryDone";
	}

	@GetMapping("/favoriteShow")
	public String favoriteShow(RedirectAttributes ra, Model model) throws Exception {
		if (session.getAttribute("loginId") == null) {
			ra.addFlashAttribute("fav", "ログインしてください");
			return "redirect:/auth/login";
		}

		String loginId = (String) session.getAttribute("loginId");
		Auth auth = authService.selectByLoginId(loginId);
		List<All> allList = allService.selectByRoomIdList(auth.getFRoomIdList());

		if (allList.size() == 0) {
			model.addAttribute("noFavRegistered", "まだお気に入りが登録されていません");
		}

		model.addAttribute("allList", allList);

		return "rent/favoriteShow";
	}
}