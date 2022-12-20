package com.gdu.tagmusic.domain;

import java.security.Timestamp;

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
	private String content;
	private Timestamp createDate;
	private Timestamp modifyDate;
	private int attachCnt;
}
