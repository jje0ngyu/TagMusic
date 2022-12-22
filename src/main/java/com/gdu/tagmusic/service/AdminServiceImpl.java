package com.gdu.tagmusic.service;

import java.util.ArrayList;
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
		
		// begin~end 목록 가져오기
		//List<ChatDTO> chatList = adminMapper.selectChatUsingScroll();
		
		
		// 여기서부터임0

		
		List<ChatDTO> userList = adminMapper.existChatUserList();
		ArrayList<List<ChatDTO>> arrayList = new ArrayList<>();
		
		
		int size = userList.size();
		for(int i=0; i<size; i++) {
			int groupNo = (userList.get(i)).getGroupNo();
			List<ChatDTO> chatchat = adminMapper.selectChatUsingScroll(groupNo);
			arrayList.add(chatchat);
			

		}
		
		System.out.println(size);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("arrayList", arrayList);
		
		//System.out.println(resultMap);
		
		
		// 응답할 데이터
		//Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap.put("chatList", chatList);
		
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
		map.put("groupNo", groupNo);
		
		
		

		
		
		
		
		return map;
	}
	
	@Override
	public Map<String, Object> getUserList(HttpServletRequest request, Model model) {
		
		// page 파라미터가 전달되지 않는 경우 page = 1로 처리한다.
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		// 전체 레코드(직원) 개수 구하기
		int totalRecord = adminMapper.countAllUser();
		
		// PageUtil 계산하기
		int recordPerPage = 8;  
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
			
		// begin~end 목록 가져오기
		List<UserDTO> userList = adminMapper.selectUserList(map);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userList", userList);
		result.put("pageUtil", pageUtil);
		result.put("beginNo", totalRecord - page * pageUtil.getRecordPerPage());
		result.put("paging", pageUtil.getPagingForAjax("/admin/user/list"));
		return result;
	}
	
	
	
	



}
