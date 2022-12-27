package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.ChatDTO;
import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface AdminMapper {
	
	public int selectAllChatCount();
	public List<ChatDTO> selectChatUsingScroll(int groupNo);
	public List<ChatDTO> getChatDetail(int groupNo);
	public List<ChatDTO> existChatUserList();
	
	// 회원 조회 매퍼
	public int countAllUser();
	public List<UserDTO> selectUserList(Map<String, Object> map);
	
	// 회원 조회 매퍼 -- 휴면 계정
	public int countSleppUser();
	public List<UserDTO> selectSleepUserList(Map<String, Object> map);
	
	// 회원 조회 매퍼 -- 탈퇴 계정
	public int countRetireUser();
	public List<UserDTO> selectRetireUserList(Map<String, Object> map);
	
	
	
	// 회원 검색 매퍼
	public int serchUserCount(Map<String, Object> map);
	public List<UserDTO> selectSearchEmployeeList(Map<String, Object> map);
	
	// 회원 자동완성리스트만드는 매퍼
	public List<UserDTO> selectAutoCompleteList(Map<String, Object> map);
	
	// 회원 휴면, 탈퇴로 돌리는 매퍼
	public List<UserDTO> selectUserByNo(Map<String, Object> userNo);
	public int insertSleepUser(Map<String, Object> sleepUserList);
	public int deleteUserByNo(Map<String, Object> userNo);
	public int insertRetireUser(Map<String, Object> retireUserList);
	
	

}
