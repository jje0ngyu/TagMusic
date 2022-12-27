package com.gdu.tagmusic.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadDTO {
	
	private int uploadNo;
	private String title;
	private int Hit;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private int attachCnt;
}
