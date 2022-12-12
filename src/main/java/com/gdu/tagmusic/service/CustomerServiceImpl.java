package com.gdu.tagmusic.service;

import java.util.Optional;

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
		
		
		// 파라미터에서 뽑으면 String임
		Optional<String> opt = Optional.ofNullable(request.getParameter("userNO"));
		
		int userNo =  Integer.parseInt(opt.orElse("0"));
		String title =  securityUtil.preventXSS(request.getParameter("title"));
		String ip = request.getRemoteAddr();
		
		
		
		
		
		
		return 0;
	}

}
