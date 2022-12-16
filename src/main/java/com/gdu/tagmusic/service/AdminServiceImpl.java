package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gdu.tagmusic.mapper.AdminMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
	
	
	private AdminMapper adminMapper;
	
	@Override
		public Map<String, Object> userChatList() {
		
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("chatList", adminMapper.selectChatList());
			
		
			return map;
		}

}
