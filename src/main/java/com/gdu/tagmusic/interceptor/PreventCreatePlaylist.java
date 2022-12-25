package com.gdu.tagmusic.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.MusicMapper;

@Component
public class PreventCreatePlaylist implements HandlerInterceptor {
	
	// 로그인이 완료된 사용자가
	// 로그인페이지이동, 약관페이지이동, 가입페이지이동 등의 요청을 하면
	// 이를 막는 인터셉터
	
	@Autowired
	MusicMapper musicMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

			HttpSession session = request.getSession();
			UserDTO user = (UserDTO) session.getAttribute("loginUser");
			String email = user.getEmail();
			
			//int result = musicMapper.selectUserMusiclistCntInercet(email);
		
			//if(result >= 5) {
			
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('플레이리스트는 5개 넘게 만들 수 없습니다.');");
				out.println("window.close();");
				out.println("</script>");
				out.close();
				
				/*
				 * return null; // 컨트롤러의 요청이 처리되지 않는다.
				 * 
				 * } else {
				 * 
				 * return null; // 컨트롤러의 요청이 처리된다.
				 * 
				 * }
				 */
		return true;
	}
	
}
