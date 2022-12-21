package com.gdu.tagmusic.service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.MusicMapper;
import com.gdu.tagmusic.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MusicServiceImpl implements MusicService {
	
	
	private MusicMapper musicMapper;
	private PageUtil pageUtil;
	
	
	// [메인페이지]
	
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
		int totalRecordCnt = musicMapper.selectMusicCnt();
		
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		
		// 3. 음악리스트 조회
		// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());

		List<MusicDTO> musicList = musicMapper.selectUpdatedMusicList(map);

		
		// 5. 모든 데이터를 전달할 map
		map.put("musicList", musicList);	
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
		MusicDTO music = musicMapper.selectMusicByNo(musicNo);
		File file = new File(music.getImgPath(), music.getImgFilesystem());
		
		// db 정보를 통해 이미지를 담은 responseentity객체 반환
		ResponseEntity<byte[]> result = null;

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
		int totalRecordCnt = musicMapper.selectMusicCnt();
		
		
		// 2) 페이징 처리
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		
		// 3) model에 값을 전달
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		model.addAttribute("paging", pageUtil.getPaging("/music/board/updatedMusic"));	
		model.addAttribute("musicList", musicMapper.selectUpdatedMusicList(map));
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
	
	// # 인기리스트
	// 1) 인기리스트 데이터 가져오기
	@Override
	public Map<String, Object> selectPopularMusic4(HttpServletRequest request) {
		
		// 1. 파라미터 : page
				Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
				int page = Integer.parseInt(opt.orElse("1"));	
				// - 한 페이지(바)당 4개를 보여줄 것
				
				
				// 파라미터 : 장르
				/*
				 * String genre = request.getParameter("genre"); Optional<String> opt2 =
				 * Optional.ofNullable(request.getParameter("page"));
				*/
				
				
				// 2. 페이징 처리
				int recordPerPage = 6;
				int totalRecordCnt = musicMapper.selectPopularMusicCnt();
				pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
				
				// 3. 음악리스트 조회
				// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
				Map<String, Object> map = new HashMap<>();
				map.put("begin", pageUtil.getBegin());
				map.put("end", pageUtil.getEnd());
				//map.put("genre", genre);

				List<MusicDTO> musicList = musicMapper.selectPopularMusicList(map);

				
				// 5. 모든 데이터를 전달할 map
				map.put("musicList", musicList);	
				map.put("pageUtil", pageUtil);
				return map;
	}
	
	// # 인기순 게시판 조회
	@Override
	public void selectPopularMusicList(HttpServletRequest request, Model model) {
		
			// 1. 파라미터
			Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
			int page = Integer.parseInt(opt.orElse("1"));
	
			// 2. 페이징 처리
			int recordPerPage = 10;
			int totalRecordCnt = musicMapper.selectPopularMusicCnt();		
			pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
			// 3. 음악리스트 조회
			// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
			Map<String, Object> map = new HashMap<>();
			map.put("begin", pageUtil.getBegin());
			map.put("end", pageUtil.getEnd());
			//map.put("genre", genre);

		
			// 4. model로 전달
				model.addAttribute("paging", pageUtil.getPaging("/music/board/popularMusic"));	
				model.addAttribute("popularList", musicMapper.selectPopularMusicList(map));
				model.addAttribute("beginNo", totalRecordCnt - (page-1) * pageUtil.getRecordPerPage());		
				// 게시글 가장 첫번째번호 : html에서 index값을 뺴서 no값을 출력
				model.addAttribute("pageName", "인기리스트");
		
	}
	
	// # 장르별 인기리스트
	@Override
	public Map<String, Object> selectPopularMusicGenre4(HttpServletRequest request) {
		// 1. 파라미터 : page
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));	
		// - 한 페이지(바)당 4개를 보여줄 것
		
		
		// 파라미터 : 장르
		String genre = request.getParameter("genre");
		
		// 2. 페이징 처리
		int recordPerPage = 6;
		int totalRecordCnt = musicMapper.selectPopularMusicCnt();		
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		
		// 3. 음악리스트 조회
		// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		map.put("genre", genre);

		List<MusicDTO> musicList = musicMapper.selectPopularMusicGenreList(map);

		
		// 5. 모든 데이터를 전달할 map
		map.put("musicList", musicList);	
		map.put("pageUtil", pageUtil);
		return map;
		
	}
	
	// # 전체검색
	@Override
	public void selectSearchMusic(HttpServletRequest request, Model model) {
		
		// 1) 파라미터
				Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
				int page = Integer.parseInt(opt.orElse("1"));
				String query = request.getParameter("query");

				int recordPerPage = 10;
				int totalRecordCnt = musicMapper.selectSearchMusicCnt(query);	// 검색어 query 전달
				
				// 2) 페이징 처리
				pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
				
				// 3) model에 값을 전달
				Map<String, Object> map = new HashMap<>();
				map.put("begin", pageUtil.getBegin());
				map.put("end", pageUtil.getEnd());
				map.put("query", query);
				
				model.addAttribute("paging", pageUtil.getPaging("/music/main/totalSearch?query=" + query));	
				model.addAttribute("searchList", musicMapper.selectSearchMusicList(map));
				model.addAttribute("beginNo", totalRecordCnt - (page-1) * pageUtil.getRecordPerPage());		
				// 게시글 가장 첫번째번호 : html에서 index값을 뺴서 no값을 출력
				model.addAttribute("pageName", "검색어 :  " +  query);
				model.addAttribute("query", query);	// * 검색값도 화면에 반환	
	}
	
	// # 랭킹
	@Override
	public Map<String, Object> selectMusicRank() {
		
		// # 페이징 처리 : page는 1페이지만, 랭킹은 10개까지, 전체도 10개로 한정
		pageUtil.setPageUtil(1, 10, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		
		List<MusicDTO> rankingList = musicMapper.selectMusicRanking10(map);
		map.put("rankingList", rankingList);
		
		// * list를 반환시, list와 map 주의
		

		return map;
	}
	
	
	
	
	
	
	
	
	
	// [유저서비스]
	// # 유저 플레이리스트 페이지 이동
	@Override
	public void selectUserPlaylist(HttpServletRequest request, Model model) {
	
		// 1. page 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		// 2. session에 저장된 User EMAIL 가져오기
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		String email = user.getEmail();
		int userNo = user.getUserNo();
		String name = user.getName();
		//System.out.println(user.getEmail());
		
		// 3. 전달할 데이터를 담을 map
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("userNo", userNo);
		
		// 4. 페이징 처리
		int totalRecordCnt = musicMapper.selectUserMusicListCnt(map);
		int recordPerPage = 5;
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		
		// 5. model에 플레이리스트 개수, 리스트 반환, 유저명
		model.addAttribute("userPlaylistCnt", totalRecordCnt);
		model.addAttribute("userName", name);
		model.addAttribute("userPlaylist", musicMapper.selectUserMusicList(map));

		
	}
	
	// # 유저 각 플레이리스트에 담긴 음악수
	@Override
	public Map<String, Object> selectUserPlaylistMusicCnt(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		String email = user.getEmail();
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("listNo"));
		int listNo = Integer.parseInt(opt.orElse("1"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("listNo", listNo);
		
		int userPlaylistMusicCnt = musicMapper.selectUserMusicListMusicCnt(map);
		
		Map<String, Object> result = new HashMap<>();
		map.put("userPlaylistMusicCnt", userPlaylistMusicCnt);
		System.out.println(userPlaylistMusicCnt);
		return result;
	}
	
	
	// # 플레이리스트의 썸네일 가져오기 : 가장 최근에 업로드된 음악의 썸네일
	@Override
	public ResponseEntity<byte[]> selectUserPlaylist_TopMusicThumbnail(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}