package com.gdu.tagmusic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gdu.tagmusic.domain.SleepUserDTO;
import com.gdu.tagmusic.service.UserService;

@Component
public class SleepUserCheckingInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	/*
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 로그인하려고 사용자가 입력한 아이디
		String id = request.getParameter("id");
		
		// 해당 아이디가 휴면테이블에 있는지 확인
		SleepUserDTO sleepUser = userService.getSleepUserById(id);
		
		// session에 휴면계정 정보를 올려둠
		HttpSession session = request.getSession();
		session.setAttribute("sleepUser", sleepUser);
		
		// 휴면회원이면 복원을 위한 과정(/user/sleep/display)을 진행함
		if(sleepUser != null) {
			response.sendRedirect(request.getContextPath() + "/user/sleep/display");
			return false;
		}
		// 휴면회원이 아니면 로그인(/user/login)을 진행함
		else {
			return true;
		}
		
	}
	*/
}
