package com.gdu.tagmusic.domain;
import java.sql.Date;

<<<<<<< HEAD
=======
import java.sql.Date;

>>>>>>> b09f63cee0bc3372b2b1e6fddef0109ee8f8fbb3
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
	
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
	private String sessionId;
	private Date sessionLimitDate;
	private Date joinDate;
	private Date pwModifyDate;
	private Date infoModifyDate;
	private int agreeCode;

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
	private String sessionId;
	private Date sessionLimitDate;
	private Date joinDate;
	private Date pwModifyDate;
	private Date infoModifyDate;
	private int agreeCode;
	
}
