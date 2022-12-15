package com.gdu.tagmusic.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
	
	
	@Transactional
	@Override
	public Map<String, Object> addChat(HttpServletRequest request) {
		

		
		// 원글의 DEPTH, GROUP_NO, GROUP_ORDER
		int depth = Integer.parseInt(request.getParameter("depth"));
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int groupOrder = Integer.parseInt(request.getParameter("groupOrder"));
		
		// 원글 DTO
		ChatDTO chatBox = new ChatDTO();
		chatBox.setDepth(depth);
		chatBox.setGroupNo(groupNo);
		chatBox.setGroupOrder(groupOrder);
		
		chatMapper.updatePreviousChat(chatBox);
		
		
		
		// 답글DTO를 만들어야함!! 필요한 파라미터는 
		// 파라미터에서 뽑으면 String임
		// 작성자, 제목, 아이피
		ChatDTO chat = new ChatDTO();
		chat.setUserNo(request.getParameter("userNO"));
		chat.set
		
		
		
		
		chat.setWriter(writer);
		chat.setTitle(title);
		reply.setIp(ip);
		reply.setDepth(depth + 1);            // 답글 depth : 원글 depth + 1
		reply.setGroupNo(groupNo);            // 답글 groupNo : 원글 groupNo
		reply.setGroupOrder(groupOrder + 1);  // 답글 groupOrder : 원글 groupOrder + 1
		
		
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("userNO"));
		int userNo =  Integer.parseInt(opt.orElse("0"));
		String content =  securityUtil.preventXSS(request.getParameter("content"));
		String ip = request.getRemoteAddr();
		

		
		
		Map<String, Object> map = new HashMap<>();
		map.put("insertChat", chatMapper.insertChat(chat)==1);
		return map; 
		
	}
	
	@Override
	public void divideUser(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			HttpSession session = request.getSession();
			Optional<Integer> opt = Optional.ofNullable(((UserDTO)session.getAttribute("loginUser")).getUserNo());
				
				int userNo = opt.orElse(0);
				
				try {
					// 게시판이 있는지 조회함. 없으면 예외발생
					//ChatDTO chat1 = chatMapper.chatListUserNo(userNo)
					Optional<ChatDTO> opt2 = Optional.ofNullable(chatMapper.chatListUserNo(userNo));
					opt2.get();
					
				// 게시판 조회값이 null이면 일로 넘어옴
				}catch(NoSuchElementException e) {
					System.out.println(e.getMessage());
					if(userNo != 1) {
						String ip = request.getRemoteAddr();
						ChatDTO chat = new ChatDTO();
						chat.setUserNo(userNo);
						chat.setIp(ip);
						chatMapper.insertChat(chat);
					}else {
						
						
					// 관리자일 때 이동하는 페이지도 필요함
					}
				}
				
				

				
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
			
			// userNo가 null일 때 이 예외코드로 넘어옴 = 비회원 접근 시 로그인페이지로 이동
		}catch (NullPointerException e) {  
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
	public ChatDTO getChatUserNo(HttpServletRequest request) {
		
		
		HttpSession session = request.getSession();
		Optional<Integer> opt = Optional.ofNullable(((UserDTO)session.getAttribute("loginUser")).getUserNo());
		int userNo = opt.orElse(0);

		
		if(userNo != 1) {
			return chatMapper.chatListUserNo(userNo);
		}else {
			return null;
		}

		
	}
}


