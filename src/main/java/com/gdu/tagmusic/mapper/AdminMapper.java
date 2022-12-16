package com.gdu.tagmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.ChatDTO;

@Mapper
public interface AdminMapper {
	
	public List<ChatDTO> selectChatList();
	
	

}
