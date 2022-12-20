package com.gdu.tagmusic.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatDTO {
	
	private int chatNo;
	private int userNo; // 이건 어떻게 쓰는거지
	private Date chatDate;
	private String ip; 
	private String content;
	private int state;
	private int depth;
	private int groupNo;
	private int groupOrder;
	private UserDTO userDTO;

}
