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
public class SleepUserDTO {

	private int userNo;
	private String email;
	private String artist;
	private String name;
	private String pw;
	private String profileImage;
	private String mobile;
	private String gender;
	private String birthyear;
	private String birthday;
	private String postcode;
	private String roadAddress;
	private String jibunAddress;
	private String detailAddress;
	private String extraAddress;
	private String snsType;
	private Date joinDate;
	private Date sleepDate;		// 휴면 전환일
	private Date pwModifyDate;
	private Date infoModifyDate;
	private int agreeCode;
	
}
