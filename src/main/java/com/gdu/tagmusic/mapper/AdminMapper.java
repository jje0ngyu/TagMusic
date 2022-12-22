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
	public List<UserDTO> selectUserList(Map<String, Object> map);
	public int countAllUser();
	
	
	
	
	

}
