package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface HomeService {
	
	// # main화면 최신리스트 조회
	public Map<String, Object> selectUpdatedMusic4(HttpServletRequest request);

}
