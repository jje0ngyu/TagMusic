package com.gdu.tagmusic.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gdu.tagmusic.domain.ChatDTO;
import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.ChatMapper;
import com.gdu.tagmusic.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
	
	private SecurityUtil securityUtil;
	private ChatMapper chatMapper;
	
	@Override
	public Map<String, Object> addChat(HttpServletRequest request) {
		
		// 파라미터에서 뽑으면 String임
		Optional<String> opt = Optional.ofNullable(request.getParameter("userNO"));
		
		int userNo =  Integer.parseInt(opt.orElse("0"));
		String content =  securityUtil.preventXSS(request.getParameter("content"));
		String ip = request.getRemoteAddr();
		
		ChatDTO chat = new ChatDTO();
		chat.setUserNo(userNo);
		chat.setContent(content);
		chat.setIp(ip);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("insertChat", chatMapper.insertChat(chat)==1);
		return map; 
		
	}
	
	@Override
	public void divideUser(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			HttpSession session = request.getSession();
			Optional<Integer> opt = Optional.ofNullable(((UserDTO)session.getAttribute("loginUser")).getUserNo());
			
			try {
				
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("location.href='" + request.getContextPath() + "/customerService/customerServiceChat';");
			out.println("</script>");	
			
			out.close();
				
			}catch (IOException e2) {
				e2.printStackTrace();
			}
			
			
		}catch (NullPointerException e) {  // userNo가 null일 때 이 예외코드로 넘어옴 = 비회원 접근 시 로그인페이지로 이동
			
			try {
				
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('회원만 이용가능한 페이지 입니다.');");
			out.println("location.href='" + request.getContextPath() + "/user/login/form';");
			out.println("</script>");	
			
			out.close();
				
			}catch (IOException e2) {
				e2.printStackTrace();
			}
 

		}
}
	
	
	@Override
	public Map<String, Object> getChatListUserNo(int userNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chatList", chatMapper.chatListUserNo(userNo));
		
		
		
		return map;
	}
}


