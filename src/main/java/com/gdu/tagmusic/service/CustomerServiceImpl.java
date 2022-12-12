package com.gdu.tagmusic.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.tagmusic.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
	
	private SecurityUtil securityUtil;
	
	@Override
	public int addChat(HttpServletRequest request) {
		
		
		// 아이디 암호화를 왜 하는거지??ㄴ
		
		String userid =  request.getParameter("userID");
		String title =  securityUtil.preventXSS(request.getParameter("title"));
		String ip = request.getRemoteAddr();
		
		
		
		return 0;
	}

}
