package com.example.app.domain;

import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Auth {
	private Integer id;
	@NotBlank
	private String loginId;
	private String loginPass;
	@AssertTrue
	private Boolean agreement;
	private Integer fRoomId1;
	private Integer fRoomId2;
	private Integer fRoomId3;
	private Integer fRoomId4;
	private Integer fRoomId5;
	private Integer fRoomId6;
	private Integer fRoomId7;
	private Integer fRoomId8;
	private Integer fRoomId9;
	private Integer fRoomId10;
	private List<Integer> fRoomIdList;

}