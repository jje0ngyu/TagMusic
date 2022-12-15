package com.gdu.tagmusic.service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.mapper.HomeMapper;
import com.gdu.tagmusic.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
	
	
	private HomeMapper homeMapper;
	private PageUtil pageUtil;
	
	
	// # 구현 : main화면 최신리스트 조회
	// 1) music 테이블에 저장된 모든 음악데이터 조회
	@Override
	public Map<String, Object> selectUpdatedMusic4(HttpServletRequest request) {
		
		// 1. 파라미터 : page
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));	
		// - 한 페이지(바)당 4개를 보여줄 것
		
		// 2. 페이징 처리
		int recordPerPage = 4;
		int totalRecordCnt = homeMapper.selectMusicCnt();
		
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		
		// 3. 음악리스트 조회
		// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());

		List<MusicDTO> musicList = homeMapper.selectUpdatedMusicList(map);

		
		// 5. 모든 데이터를 전달할 map
		map.put("musicList", musicList);	
		System.out.println(map);
		
		map.put("pageUtil", pageUtil);
		return map;
	}
	
	
	// 2) 썸네일 가져오기
	@Override
	public ResponseEntity<byte[]> selectThumbnail(HttpServletRequest request) {
		
		// 파라미터 : 나중에 없애기
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo"));
		int musicNo = Integer.parseInt(opt.orElse("0"));
		
		// db에서 이미지 정보 가져오기 
		
		// 경로 : fileutil을 통해 차후 결정
		MusicDTO music = homeMapper.selectMusicByNo(musicNo);
		File file = new File(music.getImgPath(), music.getImgFilesystem());
		
		// db 정보를 통해 이미지를 담은 responseentity객체 반환
		ResponseEntity<byte[]> result = null;
		System.out.println(music.getHasThumbNail());		
		try {
			
			if(music.getHasThumbNail() == 1) {
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				File thumbnail = new File("c:\\" + music.getImgPath(), music.getImgFilesystem());
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), null, HttpStatus.OK);
				return result;
				
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	// # 구현 : 최신리스트 게시판조회
	// - 게시판 화면에 필요한 데이터 : music 칼럼들, 썸네일과 아티스트도 가져와야한다
	// - 아티스트는 model에 담아서 보내고, 썸네일은 해당 페이지에서 요청
	
	@Override
	public void selectUpdateMusicList(HttpServletRequest request, Model model) {
	
		// 1) 전체 게시글 수 조회
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));

		int recordPerPage = 10;
		int totalRecordCnt = homeMapper.selectMusicCnt();
		
		
		// 2) 페이징 처리
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		
		// 3) model에 값을 전달
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		
		model.addAttribute("paging", pageUtil.getPaging("/music/updatedMusicBoard"));	
		model.addAttribute("musicList", homeMapper.selectUpdatedMusicList(map));
		model.addAttribute("beginNo", totalRecordCnt - (page-1) * pageUtil.getRecordPerPage());		
		// 게시글 가장 첫번째번호 : html에서 index값을 뺴서 no값을 출력
		model.addAttribute("pageName", "최신리스트");
	
		
		
		// 조회수
		/*
		 * HttpSession session = request.getSession();
		 * if(session.getAttribute("updateHit") != null) {
		 * session.removeAttribute("updateHit"); }
		 */
	}
	

}
