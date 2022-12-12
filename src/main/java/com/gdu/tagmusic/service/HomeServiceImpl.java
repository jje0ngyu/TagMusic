package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.mapper.HomeMapper;
import com.gdu.tagmusic.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
	
	
	private HomeMapper homeMapper;
	private PageUtil pageUtil;
	
	
	// # main화면 최신리스트 조회
	// 1. 음악파일의 path를 조회하여 이미지를 불러온다 image
	// 2. music테이블 전체 조회
	// 3. 4개만 나올 수 있도록 pageutil 처리
	// 4. 해당 음악의 artist를 처리 : 2번에서 musicNo
	
	
	// 음악파일의 path를 가져와서 해당 음악파일
	@Override
	public Map<String, Object> selectUpdatedMusic4(HttpServletRequest request) {
		
		// 1. 파라미터 : page
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("0"));	
		// - 한 페이지(바)당 4개를 보여줄 것
		
		// 2. 페이징 처리
		int recordPerPage = 4;
		int totalRecord = homeMapper.selectMusicCnt();
		
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		// 3. 음악리스트 조회
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		List<MusicDTO> musicList = homeMapper.selectMusicList(map);
		
		// 4. 해당 음악의 아티스트명
		// - 업로드한 게시글의 id와 users테이블의 id가 동일한 경우의 닉네임
		
		
		
		
		// 5. 모든 데이터를 전달할 map
		Map<String, Object> result = new HashMap<>();
		map.put("musicList", musicList);
		
		// pageutil에서 <, > 좌우 버튼만 가져오고 나머지는 리스트 조회
		// 4개의 게시글만 나오는건 쿼리문에서 처리함
		// 해당 뿌려진 게시글을 클릭하면 음악, 또는 상세화면 요청을 실시
		map.put("pageUtil", pageUtil);
	
	
		
		
		return map;
	}
	
	// # 전체조회화면 이동하기

}
