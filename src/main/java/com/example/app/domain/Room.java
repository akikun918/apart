package com.example.app.domain;

import java.util.List;

import com.example.app.validation.RoomSaveGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Room {

	private Integer id;
	private Integer apartId;
	@NotNull(groups = { RoomSaveGroup.class })
	@Min(value = 1, groups = { RoomSaveGroup.class })
	@Max(value = 9999, groups = { RoomSaveGroup.class })
	private Integer number;
	private Integer active;
	@NotNull(groups = { RoomSaveGroup.class })
	@Min(value = 0, groups = { RoomSaveGroup.class })
	private Integer price;
	@NotNull(groups = { RoomSaveGroup.class })
	@Min(value = 0, groups = { RoomSaveGroup.class })
	private Integer deposit;
	@NotNull(groups = { RoomSaveGroup.class })
	@Min(value = 0, groups = { RoomSaveGroup.class })
	private Integer keyMoney;
	@NotNull(groups = { RoomSaveGroup.class })
	@Min(value = 1, groups = { RoomSaveGroup.class })
	private Integer size;
	@NotNull(groups = { RoomSaveGroup.class })
	private Integer floor;
	private Integer sepToilet;
	@Size(min = 2, max = 25, groups = { RoomSaveGroup.class })
	private String point;
	@Size(min = 2, max = 100, groups = { RoomSaveGroup.class })
	private String other;
	private List<RoomImage> roomImageList;
	

}
