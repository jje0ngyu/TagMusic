package com.gdu.tagmusic.mapper;

import java.util.List;

import com.gdu.tagmusic.domain.ChatDTO;

public interface ChatMapper {
	
	public int insertChat(ChatDTO chat);
	public List<ChatDTO> chatListUserNo(int userNo);
	
}
