package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.ChatDTO;

@Mapper
public interface AdminMapper {
	
	public int selectAllChatCount();
	public List<ChatDTO> selectChatUsingScroll(Map<String, Object> map);
	public List<ChatDTO> getChatDetail(int groupNo);
	
	
	
	
	
	
	
	

}
