package com.gdu.tagmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.ChatDTO;


@Mapper
public interface ChatMapper {
	
	public int insertChatbox(ChatDTO chat);
	public ChatDTO chatListUserNo(int userNo);
	public int updatePreviousChat(ChatDTO chat);
	public int insertChat(ChatDTO chat);
	public List<ChatDTO> getChatList(int groupNo);
	
}
