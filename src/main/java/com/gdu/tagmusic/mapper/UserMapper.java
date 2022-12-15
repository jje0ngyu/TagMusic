package com.gdu.tagmusic.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface UserMapper {
	
	// 로그인
	public UserDTO selectUserByMap(Map<String, Object> map);
	public int updateSessionInfo(UserDTO user);
	
	// 회원가입
	public int insertUser(UserDTO user);
	
	// 개인정보 수정
	public int updateUser(UserDTO user);
	
	// 휴면
	// 탈퇴
}
