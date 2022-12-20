package com.gdu.tagmusic.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.ProfileImageDTO;
import com.gdu.tagmusic.domain.RetireUserDTO;
import com.gdu.tagmusic.domain.SleepUserDTO;
import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface UserMapper {
	
	// 로그인
	public UserDTO selectUserByMap(Map<String, Object> map);
	public int updateSessionInfo(UserDTO user);
	// 로그인 - 네이버 간편로그인
	public int insertNaverUser(UserDTO user);
	// 로그인 - 로그 남기기
	public int updateAccessLog(String email);
	public int insertAccessLog(String email);
	// 로그인 - 임시 비밀번호
	public int updateUserPassword(UserDTO user);
	
	// 회원가입
	public int insertUser(UserDTO user);
	
	// 개인정보 수정
	public String selectProfileImage(String email);
	public int insertImage(ProfileImageDTO image);
	public int updateImagePath(ProfileImageDTO image);
	public int updateUser(UserDTO user);
	
	// 휴면
	// 휴면 - 검사
	public SleepUserDTO selectSleepUserByEmail(String email);
	// 휴면 - 휴면처리
	public int insertSleepUser();
	public int deleteUserForSleep();
	// 휴면 - 복원
	public int insertRestoreUser(String email);
	public int deleteSleepUser(String email);
	
	
	// 탈퇴
	public int insertRetireUser(RetireUserDTO retireUser);
	public int deleteUser(int userNo);
}
