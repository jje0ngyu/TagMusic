package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.tagmusic.mapper.LikeMapper;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeMapper likeMapper;
	
	@Override
	public Map<String, Object> getLikeCheck(HttpServletRequest request) {
		String email = request.getParameter("email");
		int musicNo = Integer.parseInt(request.getParameter("musicNo"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("musicNo", musicNo);
		
		Map<String, Object> result = new HashMap<>();
		result.put("count", likeMapper.selectUserLike(map));
		return result;
	}
	
	@Override
	public Map<String, Object> markLike(HttpServletRequest request) {
		String email = request.getParameter("email");
		int musicNo = Integer.parseInt(request.getParameter("musicNo"));
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("musicNo", musicNo);
		Map<String, Object> result = new HashMap<>();
		if (likeMapper.selectUserLike(map) == 0) {  // 해당 게시물의 "좋아요"를 처음 누른 상태
			result.put("isSuccess",likeMapper.insertLike(map) == 1);  // 신규 삽입			
		} else {
			result.put("isSuccess", likeMapper.deleteLike(map) == 1);  // 기존 정보 삭제		
		}
		return result;
	}
	
}