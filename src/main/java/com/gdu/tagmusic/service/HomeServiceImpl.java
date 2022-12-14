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
	// 
	// 1. 음악파일의 path를 조회하여 이미지를 불러온다 image
	// 2. music테이블 전체 조회
	// 3. 4개만 나올 수 있도록 pageutil 처리
	// 4. 해당 음악의 artist를 처리 : 2번에서 musicNo
	
	
	// 1) music 테이블에 저장된 모든 음악데이터 조회
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
		// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());

		List<MusicDTO> musicList = homeMapper.selectMusicList(map);

		
		// 5. 모든 데이터를 전달할 map
		map.put("musicList", musicList);	
		System.out.println(map);
		
		map.put("pageUtil", pageUtil);
		return map;
	}
	
	
	// 2) music별 썸네일 가져오기
	// * 별도로 만든이유 : 해당 음악별 musicNo를 받아서 해당 dto를 조회해야하기때문
	@Override
	public ResponseEntity<byte[]> selectThumbnail(HttpServletRequest request) {
		
		// 파라미터 : 나중에 없애기
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo"));
		int musicNo = Integer.parseInt(opt.orElse("0"));
		
		// db에서 이미지 정보 가져오기 
		MusicDTO music = homeMapper.selectMusicByNo(musicNo);
		File file = new File("c:\\" + music.getImgPath(), music.getImgFilesystem());
		
		// db 정보를 통해 이미지를 담은 responseentity객체 반환
		ResponseEntity<byte[]> result = null;
		System.out.println(music.getHasThumbNail());		// 0000
		try {
			
			if(music.getHasThumbNail() == 1) {
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				File thumbnail = new File("c:\\" + music.getImgPath(), music.getImgFilesystem());
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), null, HttpStatus.OK);
				return result;
				
			} else if(music.getHasThumbNail() == 0) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				File thumbnail = new File("c:\\" + music.getImgPath(),"defaultImage.jpg");
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), null, HttpStatus.OK);
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// # 전체음악 조회 
	

}
