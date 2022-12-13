package com.gdu.tagmusic.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
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
	public int addChat(HttpServletRequest request) {
		
		
		// 파라미터에서 뽑으면 String임
		Optional<String> opt = Optional.ofNullable(request.getParameter("userNO"));
		
		int userNo =  Integer.parseInt(opt.orElse("0"));
		String content =  securityUtil.preventXSS(request.getParameter("content"));
		String ip = request.getRemoteAddr();
		
		ChatDTO chat = new ChatDTO();
		chat.setUserNo(userNo);
		chat.setContent(content);
		chat.setIp(ip);
		
		return chatMapper.insertChat(chat);
	}
	
	@Override
	public void findChatList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		HttpSession session = request.getSession();
		Optional<Integer> opt = Optional.ofNullable(((UserDTO)session.getAttribute("loginUser")).getUserNo());
		
		
		if(opt.isPresent()) {
			
			int userNo = opt.get();
	
			List<ChatDTO> chatList = chatMapper.selectChatList(userNo);
			model.addAttribute("chatList", chatList);
			
		}else{
			
			try {
				
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('회원만 이용가능한 페이지 입니다.');");
			out.println("history.back();");
			out.println("</script>");	
			
			out.close();
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
