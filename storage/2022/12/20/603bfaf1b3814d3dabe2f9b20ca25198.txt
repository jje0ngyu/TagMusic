package com.gdu.semi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BbsDTO {
	private int bbsNo;
	private String id;
	private String bbsTitle;
	private Date bbscreateDate;
	private int state; 
	private int depth;
	private int groupNo;
	private int groupOrder;
	private Date bsslastmodifyDate;
	private String ip;
	
}