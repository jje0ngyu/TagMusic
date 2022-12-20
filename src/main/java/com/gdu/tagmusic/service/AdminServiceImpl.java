package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.tagmusic.domain.ChatDTO;
import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.AdminMapper;
import com.gdu.tagmusic.mapper.ChatMapper;
import com.gdu.tagmusic.util.PageUtil;

@Service
public class AdminServiceImpl implements AdminService {
	
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private ChatMapper chatMapper;
	
	@Autowired
	private PageUtil pageUtil;

	@Override
	public Map<String, Object> getChatListUsingScroll(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		
		// 전체 대화 갯수 구하기
		int totalRecord = adminMapper.selectAllChatCount();
		
		
		// PageUtil 계산하기
		int recordPerPage = 6;  // 스크롤 한 번에 10개씩 가져가기
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		// Map 만들기(field, order, begin, end)
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		// begin~end 목록 가져오기
		List<ChatDTO> chatList = adminMapper.selectChatUsingScroll(map);
		
		// 응답할 데이터
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalPage", pageUtil.getTotalPage());
		resultMap.put("chatList", chatList);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getDetailChat(HttpServletRequest request) {
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chatDetail", adminMapper.getChatDetail(groupNo));
		
		
		
		
		
		return map;
	}
	
	
	@Transactional
	@Override
	public Map<String, Object> insertAdminChat(HttpServletRequest request) {
		
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
		
		// 답글 DTO
		HttpSession session = request.getSession();		
		Optional<Integer> opt = Optional.ofNullable(((UserDTO)session.getAttribute("loginUser")).getUserNo());
		int userNo = opt.orElse(0);
		
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
	
	
	
	



}
