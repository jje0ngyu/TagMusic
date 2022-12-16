package com.gdu.tagmusic.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.RetireUserDTO;
import com.gdu.tagmusic.domain.SleepUserDTO;
import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface UserMapper {
	
	// 로그인
	public UserDTO selectUserByMap(Map<String, Object> map);
	public int updateSessionInfo(UserDTO user);
	
	// 회원가입
	public int insertUser(UserDTO user);
	
	// 개인정보 수정
	public String selectProfileImage(String email);
	public int updateUser(UserDTO user);
	
	// 휴면
	public int insertSleepUser();
	public int deleteUserForSleep();
	public SleepUserDTO selectSleepUserById(String id);
	
	
	// 탈퇴
	public int insertRetireUser(RetireUserDTO retireUser);
	public int deleteUser(int userNo);
}
