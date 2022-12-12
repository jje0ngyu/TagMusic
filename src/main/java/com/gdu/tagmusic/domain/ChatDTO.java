package com.gdu.tagmusic.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatDTO {
	
	private int chatNo;
	private int userNo; // 이건 어떻게 쓰는거지
	private Date chatDate;
	private int ip; 
	private String content;
	private int state;
	private int depth;
	private int groupNO;
	private int groupOrder;
	

}
