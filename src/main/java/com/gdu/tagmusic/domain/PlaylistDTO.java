package com.gdu.tagmusic.domain;



import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlaylistDTO {

	private int listNo;
	private String email;
	private String listName;
	private UserDTO userDTO;
	
}
