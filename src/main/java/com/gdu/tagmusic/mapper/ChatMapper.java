package com.gdu.tagmusic.mapper;

import com.gdu.tagmusic.domain.ChatDTO;

public interface ChatMapper {
	
	public int insertChat(ChatDTO chat);
	public ChatDTO chatListUserNo(int userNo);
	public int updatePreviousChat(ChatDTO chat);
	
	
	
}
