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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.tagmusic.domain.ChatDTO;
import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.ChatMapper;
import com.gdu.tagmusic.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
	
	
	@Autowired
	private SecurityUtil securityUtil;
	@Autowired
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
		
		int userNo = Integer.parseInt(request.getParameter("userNo")); // 정상
		String content = request.getParameter("content"); // 정상
		
		ChatDTO chat = new ChatDTO();
		chat.setUserNo(userNo);
		chat.setContent(content);
		chat.setIp(request.getRemoteAddr());
		chat.setGroupNo(groupNo);
		chat.setGroupOrder(groupOrder+1);
		
		Map<String, Object> map = new HashMap<>();
		map.put("insertChat", chatMapper.insertChat(chat) == 1);
		
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
						chatMapper.insertChatbox(chat);
					}else {
						try {
							response.setContentType("text/html; charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("<script>");
							out.println("location.href='" + request.getContextPath() + "/admin/chatUserList';");
							out.println("</script>");	
							out.close();
							
						}catch (IOException e2) {
							e2.printStackTrace();
						}
						
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
		
		
		// 여기서 null이 떠버리면 곤란해짐
		
		HttpSession session = request.getSession();
		Optional<Integer> opt = Optional.ofNullable(((UserDTO)session.getAttribute("loginUser")).getUserNo());
		int userNo = opt.orElse(0);
		
		
		return chatMapper.chatListUserNo(userNo);
	}
	
	
	
	@Override
	public Map<String, Object> getChatList(HttpServletRequest request) {
		
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		// 검색을 위한 map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupNo", groupNo);
		map.put("userNo", userNo);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("chatList", chatMapper.getChatList(map));
		
		return result;
	}
	
	
}


