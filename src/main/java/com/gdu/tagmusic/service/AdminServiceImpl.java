package com.gdu.tagmusic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.tagmusic.domain.ChatDTO;
import com.gdu.tagmusic.domain.RetireUserDTO;
import com.gdu.tagmusic.domain.SleepUserDTO;
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
	
	
	@Transactional
	@Override
	public Map<String, Object> getUserList(HttpServletRequest request, Model model) {
		
		// page 파라미터가 전달되지 않는 경우 page = 1로 처리한다.
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page;
		
		try {
			page = Integer.parseInt(opt.orElse("1"));
			page = Integer.parseInt(request.getParameter("page"));
		}catch (NumberFormatException n) {
			page = 1;
		}
		
		// 조회하고자 하는 유저타입
		String userType = request.getParameter("list");
		
		int totalRecord=0;
		int recordPerPage = 8; 
		List<UserDTO> userList = null;
		
		switch (userType) {
			case "nowUser":
				
				// 전체 레코드(직원) 개수 구하기
				totalRecord = adminMapper.countAllUser();
				
				// PageUtil 계산하기
				pageUtil.setPageUtil(page, recordPerPage, totalRecord);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("begin", pageUtil.getBegin() - 1);
				map.put("recordPerPage", pageUtil.getRecordPerPage());
				
				// begin~end 목록 가져오기
				userList = adminMapper.selectUserList(map);
				break;
			case "sleepUser":
				// 전체 레코드(직원) 개수 구하기
				totalRecord = adminMapper.countSleppUser();
				
				// PageUtil 계산하기
				pageUtil.setPageUtil(page, recordPerPage, totalRecord);
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("begin", pageUtil.getBegin() - 1);
				map2.put("recordPerPage", pageUtil.getRecordPerPage());
				
				// begin~end 목록 가져오기
				userList = adminMapper.selectSleepUserList(map2);
				break;
			case "retireuser":
				// 전체 레코드(직원) 개수 구하기
				totalRecord = adminMapper.countRetireUser();
				
				// PageUtil 계산하기
				pageUtil.setPageUtil(page, recordPerPage, totalRecord);
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("begin", pageUtil.getBegin() - 1);
				map3.put("recordPerPage", pageUtil.getRecordPerPage());
				
				// begin~end 목록 가져오기
				userList = adminMapper.selectRetireUserList(map3);
				break;
			default:
				break;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userList", userList);
		result.put("pageUtil", pageUtil);
		result.put("paging", pageUtil.getPagingForAjax("/admin/user/list"));
		return result;
	}
	
	
	@Override
	public Map<String, Object> searchUser(HttpServletRequest request, HttpServletResponse response) {
		
		// page 파라미터가 전달되지 않는 경우 1로 처리한다.
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page;
		
		try {
			page = Integer.parseInt(opt.orElse("1"));
			page = Integer.parseInt(request.getParameter("page"));
		}catch (NumberFormatException n) {
			page = 1;
		}
		
		// 검색테이블
		String table = request.getParameter("table");
		
		// 검색 대상
		String column = request.getParameter("column");
		
		// 검색어
		String query = request.getParameter("query");

		
		String start = request.getParameter("start");
		String stop = request.getParameter("stop");
		
		// 조회와 검색된 사원수를 알아낼 때 사용하는 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		map.put("query", query);
		map.put("start", start);
		map.put("stop", stop);
		
		switch (table) {
			case "USERS":
				map.put("table", "USERS");
				break;
			case "SLEEP_USERS":
				map.put("table", "SLEEP_USERS");
				break;
			case "RETIRE_USERS":
				map.put("table", "RETIRE_USERS");
				break;
			default:
				map.put("table", "USERS");
				break;
		}
		
		int totalRecord = 0;
		try {
			// 검색된 유저 갯수
			totalRecord = adminMapper.serchUserCount(map);
		}catch(BadSqlGrammarException e){
			System.out.println("오류이긴하나 기능에 문제 없음");
		}
		
		// 페이징 계산	
		int recordPerPage = 8;
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		// 조회에서 사용하는 Map
		map.put("begin", pageUtil.getBegin());
		map.put("recordPerPage", pageUtil.getRecordPerPage());

		List<UserDTO> userList= null;
		try {
			userList = adminMapper.selectSearchEmployeeList(map);
		}catch(BadSqlGrammarException e){
			System.out.println("오류이긴하나 기능에 문제 없음");
		}
		
		
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userList", userList);
		result.put("pageUtil", pageUtil);
		
		
		switch (table) {
		case "USERS":
			result.put("table", "USERS");
			break;
		case "SLEEP_USERS":
			result.put("table", "SLEEP_USERS");
			break;
		case "RETIRE_USERS":
			result.put("table", "RETIRE_USERS");
			break;
		default:
			result.put("table", "USERS");
			break;
	}
		
		

		return result;
		
		
		
	}
	
	@Override
	public Map<String, Object> getAutoCompleteList(HttpServletRequest request) {
		
		
		String column = request.getParameter("column");
		String query = request.getParameter("query");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		map.put("query", query);
		
		List<UserDTO> list = adminMapper.selectAutoCompleteList(map);

		Map<String, Object> result = new HashMap<String, Object>();
		if(list.size() == 0) {
			result.put("status", 400);
			result.put("list", null);
		} else {
			result.put("status", 200);
			result.put("list", list);
		}
		
		
		switch(column) {
		case "NAME": result.put("column", "name"); break;
		case "GENDER": result.put("column", "gender"); break;
		case "ARTIST": result.put("column", "artist"); break;
		case "MOBILE": result.put("column", "mobile"); break;
		case "EMAIL": result.put("column", "email"); break;
		case "SNS_TYPE": result.put("column", "snsType"); break;
		}
		
		
		return result;
	}
	
	@Transactional
	@Override
	public Map<String, Object> sleepUser(Map<String, Object> userNo) {
		
	      Map<String, Object> sleepUser = new HashMap<>();
	      List<UserDTO> users = adminMapper.selectUserByNo(userNo);
	      
	      List<SleepUserDTO> sleepUserList = new ArrayList<>();
	      for(int i = 0; i < users.size(); i++) {
	         SleepUserDTO setSleepUser = SleepUserDTO.builder()
	        		 .userNo(users.get(i).getUserNo())
	        		 .email(users.get(i).getEmail())
	        		 .artist(users.get(i).getArtist())
	        		 .name(users.get(i).getName())
	        		 .pw(users.get(i).getPw())
	        		 .profileImage(users.get(i).getProfileImage())
	        		 .mobile(users.get(i).getMobile())
	        		 .gender(users.get(i).getGender())
	        		 .birthyear(users.get(i).getBirthyear())
	        		 .birthday(users.get(i).getBirthyear())
	        		 .postcode(users.get(i).getPostcode())
	        		 .roadAddress(users.get(i).getRoadAddress())
	        		 .jibunAddress(users.get(i).getJibunAddress())
	        		 .detailAddress(users.get(i).getDetailAddress())
	        		 .extraAddress(users.get(i).getExtraAddress())
	        		 .snsType(users.get(i).getSnsType())
	        		 .joinDate(users.get(i).getJoinDate())
	        		 // sleepDate
	        		 .pwModifyDate(users.get(i).getPwModifyDate())
	        		 .infoModifyDate(users.get(i).getInfoModifyDate())
	        		 .agreeCode(users.get(i).getAgreeCode())
	               .build();
	         sleepUserList.add(i, setSleepUser);
	      }
	      
	         
	         Map<String, Object> sUser = new HashMap<>();
	         sUser.put("sleepUsers", sleepUserList);
	         
	         int insertResult = adminMapper.insertSleepUser(sUser);
	         int deleteResult = adminMapper.deleteUserByNo(userNo);
	         
	         sleepUser.put("isSleepUser", deleteResult);
	         
	         return sleepUser;

	}
	
	@Transactional
	@Override
	public Map<String, Object> removeUser(Map<String, Object> userNo) {

	      Map<String, Object> deleteUser = new HashMap<>();
	      List<UserDTO> users = adminMapper.selectUserByNo(userNo);

	      List<RetireUserDTO> retireUserList = new ArrayList<>();
	      for(int i = 0; i < users.size(); i ++) {
	         RetireUserDTO retireUser = RetireUserDTO.builder()
	        		 .userNo(users.get(i).getUserNo())
	        		 .email(users.get(i).getEmail())
	        		 .artist(users.get(i).getArtist())
	        		 // 탈퇴일
	        		 .build();
	         retireUserList.add(i, retireUser);
	      }
	      Map<String, Object> rUser = new HashMap<>();
	      rUser.put("retireUsers", retireUserList);
	      int insertResult = adminMapper.insertRetireUser(rUser);
	      int deleteResult = adminMapper.deleteUserByNo(userNo);

	      deleteUser.put("isRemove", deleteResult);
	      
	      return deleteUser;
	}


}
