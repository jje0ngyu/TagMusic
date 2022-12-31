package com.gdu.tagmusic.service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.domain.MusicLikeDTO;
import com.gdu.tagmusic.domain.MyMusicDTO;
import com.gdu.tagmusic.domain.PlaylistDTO;
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
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
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
		File file = new File(music.getImgPath(), "s_" + music.getImgFilesystem());

		System.out.println(music);
		
		// db 정보를 통해 이미지를 담은 responseentity객체 반환
		ResponseEntity<byte[]> result = null;

		try {

			if (music.getHasThumbNail() == 1) {

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				File thumbnail = new File(music.getImgPath(), "s_" + music.getImgFilesystem());
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
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());

		model.addAttribute("paging", pageUtil.getPaging("/music/board/updatedMusic"));
		model.addAttribute("musicList", musicMapper.selectUpdatedMusicList(map));
		model.addAttribute("beginNo", totalRecordCnt - (page - 1) * pageUtil.getRecordPerPage());
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
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		// map.put("genre", genre);

		List<MusicDTO> musicList = musicMapper.selectPopularMusicList(map);

		// 5. 모든 데이터를 전달할 map
		map.put("musicList", musicList);
		map.put("pageUtil", pageUtil);
		return map;
	}

	// # 인기순 게시판 조회
	@Override
	public void selectPopularMusicList(HttpServletRequest request, Model model) {

		// 1. 기초데이터
		// 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));

		// 2. 페이징 처리
		int recordPerPage = 10;
		int totalRecordCnt = musicMapper.selectPopularMusicCnt();
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);
		// 3. 음악리스트 조회
		// - users 테이블, active_log 테이블과 조인하여 해당 게시글의 artist를 조회해온다
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		// map.put("genre", genre);

		List<MusicDTO> list =  musicMapper.selectPopularMusicList(map);
		
		// 4. model로 전달
		model.addAttribute("paging", pageUtil.getPaging("/music/board/popularMusic"));
		model.addAttribute("musicList", musicMapper.selectPopularMusicList(map));
		model.addAttribute("beginNo", totalRecordCnt - (page - 1) * pageUtil.getRecordPerPage());
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
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
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
		int totalRecordCnt = musicMapper.selectSearchMusicCnt(query); // 검색어 query 전달

		// 2) 페이징 처리
		pageUtil.setPageUtil(page, recordPerPage, totalRecordCnt);

		// 3) model에 값을 전달
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		map.put("query", query);

		model.addAttribute("paging", pageUtil.getPaging("/music/main/totalSearch?query=" + query));
		model.addAttribute("musicList", musicMapper.selectSearchMusicList(map));
		model.addAttribute("beginNo", totalRecordCnt - (page - 1) * pageUtil.getRecordPerPage());
		// 게시글 가장 첫번째번호 : html에서 index값을 뺴서 no값을 출력
		model.addAttribute("pageName", "검색어 :  " + query);
		model.addAttribute("query", query); // * 검색값도 화면에 반환
	}

	// # 랭킹
	@Override
	public Map<String, Object> selectMusicRank() {

		// # 페이징 처리 : page는 1페이지만, 랭킹은 10개까지, 전체도 10개로 한정
		pageUtil.setPageUtil(1, 10, 10);

		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());

		List<MusicDTO> rankingList = musicMapper.selectMusicRanking10(map);
		map.put("rankingList", rankingList);

		// * list를 반환시, list와 map 주
		
		

		return map;
	}

	// [유저서비스]

	// # 구현 : 유저 플레이리스트 목록 조회

	@Override
	public Map<String, Object> selectUserPlaylist(HttpServletRequest request) {

		// 유저정보 : email, userNo
		 HttpSession session = request.getSession(); 
		 UserDTO user = (UserDTO)session.getAttribute("loginUser"); 
		 
		 
		 Map<String, Object> map = new HashMap<>();
	 
		 // 제약 : 로그인이 안됬을 경우 이벤트 실패
		 if(user == null) {

			 map.put("result", 0);
			 return map;
		 }
	
		 // * 위의 제약이 실행되기전에 비회원일 경우 email이 null값이 발생하니 제약 뒤에서 값을 호출
		 int userNo = user.getUserNo(); 
		 String userNickName = user.getArtist();
		 String email = user.getEmail();
		 map.put("email", email);
			
		 // 제약 : 유저의 플레이리스트가 0개일 경우 별도의 창 생성
		 
		 int playlistCnt = musicMapper.checkUserPlaylistCnt(map);

		 if(playlistCnt == 0) {
			
			map.put("result", 2);
			return map;
		
		 
		 } else {

			 
			 // map에 담기
			 map.put("email", email);
			 map.put("userNo", userNo);
			 
			 // 반환할 데이터
			 map.put("userNickName", userNickName);										// 유저명
			 map.put("userPlaylistCnt",musicMapper.selectUserPlaylistCnt(map)); // 플레이리스트 개수
			 map.put("userPlaylist", musicMapper.selectUserPlaylist(map));		// 플레이리스트 목록
			 map.put("result", 1);
			
			 
			return map;
			 
			 
		 }
		 
		 
		 
		 // 2. 로그인 되었을 경우 이벤트 실행
		
	}
	
	// 구현 : 최신썸네일 가져오기
	@Override 
	public ResponseEntity<byte[]> selectPlaylistThumbnail(HttpServletRequest request) {
	 
	 // 파라미터 : listNo 
		Optional<String> opt = Optional.ofNullable(request.getParameter("listNo")); 
		int listNo = Integer.parseInt(opt.orElse("0"));
	 
		
		
		MusicDTO music = musicMapper.selectUserPlaylistThumbnail(listNo); //
		File file = new File(music.getImgPath(),
		music.getImgFilesystem());
	  
	 // db 정보를 통해 이미지를 담은 responseentity객체 반환 
		ResponseEntity<byte[]> result = null;
	 
		 try {
		 
			 if (music.getHasThumbNail() == 1) {
			 
			 HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
			 Files.probeContentType(file.toPath())); File thumbnail = new
			  File(music.getImgPath(), "s_" + music.getImgFilesystem()); result = new
			 ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), null,
			  HttpStatus.OK); return result;
		  
			 }
		  
		  } catch (Exception e) { e.printStackTrace(); }
		  
		 return null; }
		
	
	// # 구현 : 플레이리스트 수록곡 조회
	@Override
	public Map<String, Object> selectUserPlaylistMusicList(HttpServletRequest request) {
		
		// 파라미터 
		// (1) page : 게시판용 파라미터 (2) listNo : 플레이리스트번호
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page")); 
		int page = Integer.parseInt(opt.orElse("1"));
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("listNo")); 
		int listNo = Integer.parseInt(opt2.orElse("1"));
		
		
		// 유저정보 : email, userNo
		 HttpSession session = request.getSession(); 
		 UserDTO user = (UserDTO)session.getAttribute("loginUser"); 
		 String email = user.getEmail();
		 int userNo = user.getUserNo(); 
		 
		 // 해당 플레이리스트 음악 개수
		 int playlistMusicCnt = musicMapper.selectUserPlaylistMusicCnt(listNo);
		 
			/*
			 * System.out.println(user); System.out.println(email);
			 * System.out.println(userNo); System.out.println(playlistMusicCnt);
			 * System.out.println(listNo);
			 */
		 // 한 페이지당 10개의 게시글 조회
		 pageUtil.setPageUtil(page, 5, playlistMusicCnt);
		 
		// map에 담기
		 Map<String, Object> map = new HashMap<>();
		 map.put("begin", pageUtil.getBegin() -1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		 map.put("listNo", listNo);
		 map.put("email", email);
		 map.put("userNo", userNo);
		 
		// System.out.println( "end : "+ pageUtil.getEnd());
		 List<MyMusicDTO> PlaylistMusiclist = musicMapper.selectUserPlaylistMusiclist(map);
		 //System.out.println(PlaylistMusiclist);
		 //PlaylistMusiclist.stream().forEach(System.out::println);
		 
		 map.put("pageUtil", pageUtil);								// 페이지
		 map.put("paging", pageUtil.getPaging("/music/user/playlistMusiclist"));								// 페이지
		 map.put("playlistMusicCnt", playlistMusicCnt);				// 수록곡 수 
		 map.put("PlaylistMusiclist", PlaylistMusiclist);			// 수록곡 리스트 
		 
		return map;

	}
	
	// 구현 : 플레이리스트명 수정
	@Override
	public Map<String, Object> modifyUserPlaylistMusicList(HttpServletRequest request) {
		
		// 1. 기초데이터
		// 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("listNo")); 
		int listNo = Integer.parseInt(opt.orElse("1"));
		String listName = request.getParameter("listName");
		
		// 유저정보 : email, userNo
		 HttpSession session = request.getSession(); 
		 UserDTO user = (UserDTO)session.getAttribute("loginUser"); 
		 String email = user.getEmail();
		
		// 제약
		
		// map
		Map<String, Object> map = new HashMap<>();
		map.put("listNo", listNo);
		map.put("listName", listName);
		map.put("email", email);
		
		// 제약 : 해당 플레이리스트명이 이미 존재하는 경우 방지 
		PlaylistDTO playlist = musicMapper.checkPlaylistAtUserByListName(map);
		
		if(playlist != null) {
			
			map.put("result", 0);
			return map;
			
		} else {
		
		
			int result = musicMapper.updatePlaylistName(map);
			map.put("result", 1);
			return map;
		}
		
	}
	
	// 구현 : 플레이리스트 삭제
	@Override
	public Map<String, Object> deleteUserPlaylist(HttpServletRequest request) {
		
		// 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("listNo")); 
		int listNo = Integer.parseInt(opt.orElse("1"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("listNo", listNo);
		
		int result = musicMapper.deletePlaylist(map);
		
		map.put("result", result);
		
		return map;
	}
	
	// 구현 : 수록곡 삭제
	@Override
	public Map<String, Object> deletePlaylistMusic(HttpServletRequest request) {
		
		// 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("myMusicNo")); 
		int myMusicNo = Integer.parseInt(opt.orElse("1"));
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("listNo")); 
		int listNo = Integer.parseInt(opt2.orElse("1"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("myMusicNo", myMusicNo);
		map.put("listNo", listNo);
		
		int result = musicMapper.deletePlaylistMusic(map);
		
		map.put("result", result);
		
		return map;
		
	}
	
	// 구현 : 플레이리스트에 음악 추가
	@Override
	public Map<String, Object> addPlaylistMusic(HttpServletRequest request) {
		
		// 1.파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo")); 
		int musicNo = Integer.parseInt(opt.orElse("1"));
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("listNo")); 
		int listNo = Integer.parseInt(opt2.orElse("1"));
		
		// 2.map
		Map<String, Object> map = new HashMap<>();
		map.put("musicNo", musicNo);
		map.put("listNo", listNo);
		
		
		// 3.. 요청한 음악이 해당 플레이리스트에 존재하는지 확인
		MyMusicDTO myMusic = musicMapper.checkMusicInPlaylist(map);
		
		// 1) 존재하는 경우
		if(myMusic != null) {
	
			map.put("result", 0);
			return map;
			
		} else {
			
		// 2) 존재하지 않는 경우
			
			// insert
			int result = musicMapper.insertMusicToPlaylist(map);
			map.put("result", 1);
			return map;
			
		}
	}
	
	// 구현 : 플레이리스트 생성
	@Transactional
	@Override
	public Map<String, Object> createPlaylist(HttpServletRequest request) {
				
		// 파라미터 : 플레이리스트명
		String listName = request.getParameter("listName");
		
		// session의 email과 user
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		String email = user.getEmail();
		
		// map에 담기 : 플레이리스트에 필요한 칼럼 2가지
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("listName", listName);
		
		
		// 제약 : 플레이리스트가 5개 존재하는경우
		int playlistCnt = musicMapper.checkUserPlaylistCnt(map);
		if(playlistCnt >= 5) {
			
			map.put("result", 0);
			return map;
			
		}
		
		// 제약: 해당 유저가 지은 플레이리스트명이 이미 존재하는경우 : 이벤트 X
		PlaylistDTO playlist = musicMapper.checkPlaylistAtUserByListName(map);
		
		if(playlist != null) {
			
			map.put("result", 2);
			return map;
			
		} else {
			
			// 2. 해당 유저가 지은 플레이리스트명이 처음인 경우 : 이벤트 발생

			
			// 1) 플레이리스트 생성
			int result = musicMapper.insertPlaylist(map);
			System.out.println("결과:" + result);
			
			// 2) 생성한 플레이리스트명과 이메일이 동일한 리스트 pk값 가져오기
			int listNo = musicMapper.selectPlaylistNo(map);
			map.put("listNo", listNo);
			
			// 3) 해당 플레이리스트에 디폴트곡 추가
			int result2 = musicMapper.insertDefaultMusicToPlaylist(map);
			
			map.put("result", 1);
			return map;
			
			
		}
	}
	
	// # 유저 좋아요
	
	// 1. 좋아요 전체 조회
	// 해당 유저가 좋아요를 누른 목록을 조회
	@Override
	public Map<String, Object> selectMusicLikeList(HttpServletRequest request) {
		
		// 1. 기초데이터
		// 파라미터 : page
		Optional<String> opt = Optional.ofNullable(request.getParameter("page")); 
		int page = Integer.parseInt(opt.orElse("1"));
			
		// 유저 : email
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		String email = user.getEmail();
		String userNickName = user.getArtist();
		
		// map
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		
		// 2. 유저의 전체 좋아요 수 조회
		int musicLikeCnt = musicMapper.selectUserMusicLikeCnt(map);
		
		// 제약 : 좋아요 수가 0일경우 result = 0 반환
		if(musicLikeCnt == 0) {
			map.put("result", 0);
			return map;
		}
		
		// 3. 페이징 처리
		pageUtil.setPageUtil(page, 10, musicLikeCnt);
		
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		// 4. 좋아요 목록 조회
		List<MusicDTO> musicLikeList = musicMapper.selectUserMusicLikeList(map);
		System.out.println("==" + musicLikeList.size());
		map.put("pageUtil", pageUtil);
		map.put("beginNo", pageUtil.getBegin());
		map.put("userNickName", userNickName);
		map.put("selectUserMusicLikeList", musicLikeList);
		map.put("result", 1);
		map.put("musicLikeCnt", musicLikeCnt);
		/*
		 * System.out.println(pageUtil.getBegin()); //1
		 * System.out.println(pageUtil.getEnd()); // 10
		 * System.out.println(pageUtil.getBeginPage()); // 1
		 * System.out.println(pageUtil.getEndPage()); //1
		 * System.out.println(pageUtil.getRecordPerPage());// 10
		 * System.out.println(pageUtil.getTotalPage()); //1
		 */		//System.out.println(map);
		
		return map;
	}

	// 2. 유저 좋아요 삭제
	@Override
	public Map<String, Object> deleteUserMusicLike(HttpServletRequest request) {

		// 1. 기초데이터
		// 파라미터 : likeNo
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo")); 
		int musicNo = Integer.parseInt(opt.orElse("1"));
		
		// 유저 : email
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		String email = user.getEmail();
		
		// map
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("musicNo", musicNo);
		
		// 2. 좋아요 목록에서 삭제
		int result = musicMapper.deleteMusicLike(map);

		Map<String, Object> map2 = new HashMap<>();
		map2.put("result", result);
		
		return map2;
	}
	
	// 3. 유저 좋아요 상태 조회
	@Override
	public Map<String, Object> checkMusicLike(HttpServletRequest request) {
		
		// 1. 기초데이터
		// 파라미터 : likeNo
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo")); 
		int musicNo = Integer.parseInt(opt.orElse("1"));
		
		// 유저 : email
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String email = loginUser.getEmail();
		
		
		
		// 여부확인

		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("musicNo", musicNo);
	
		
		Map<String, Object> result = new HashMap<>();
	    result.put("musicLikeCheck", musicMapper.checkUserMusicLike(map));
		
		return result;
	}
	
	// 4. 좋아요 개수 조회
	@Override
	public Map<String, Object> checkMusicLikeCnt(HttpServletRequest request) {

		// 1. 기초데이터
		// 파라미터 : likeNo
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo")); 
		int musicNo = Integer.parseInt(opt.orElse("1"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("musicNo", musicNo);
		
		Map<String, Object> result = new HashMap<>();
		//int aaa =  musicMapper.checkMusicLikeCnt(map);
		result.put("musicLikeCnt", musicMapper.checkMusicLikeCnt(map));
		//System.out.println(aaa);
		
		return result;
	}
	
	// 5. 좋아요 선택/해제
	@Override
	public Map<String, Object> toggleMusicLike(HttpServletRequest request) {
		
		// 1. 기초데이터
		// 파라미터 : likeNo
		Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo")); 
		int musicNo = Integer.parseInt(opt.orElse("1"));
		
		// 유저 : email
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String email = loginUser.getEmail();
		
		Map<String, Object> map = new HashMap<>();
		map.put("musicNo", musicNo);
		map.put("email", email);
		
		Map<String, Object> result = new HashMap<>();
	
		int checkUserMusicLike = musicMapper.checkUserMusicLike(map);
		
	
		 if(checkUserMusicLike == 0) {
			
			 musicMapper.insertMusicLike(map);	// 선택
		
			 result.put("result", 0); 
			 return result;
		 } else {
			 
			 musicMapper.deleteMusicLike(map);
			
			 result.put("result", 1); // 삭제
			 return result;
		 }
			
	 
	}
	
	
	// # 최근들은
	// 1. 유저_최근들은 목록 조회
	@Override
	public Map<String, Object> selectMusicLastlyList(HttpServletRequest request) {
		
		// 1. 기초데이터
		// 파라미터 : page 
		Optional<String> opt = Optional.ofNullable(request.getParameter("page")); 
		int page = Integer.parseInt(opt.orElse("1"));

		// 유저 : email
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String email = loginUser.getEmail();
		String userNickName = loginUser.getArtist();
		
		// map
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
				
		// 2. 유저의 전체 좋아요 수 조회
		int musicLastlyCnt = musicMapper.selectUserMusicLastlyCnt(map);
		
		// 3. 페이징 처리
		pageUtil.setPageUtil(page, 10, musicLastlyCnt);

		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		// 4. 최근들은 목록 조회
	    List<MusicDTO> musicLastlyList = musicMapper.selectUserMusicLastlyList(map);
	    //System.out.println(musicLastlyList);
		
	    Map<String, Object> result = new HashMap<>();
		result.put("musicLastlyCnt", musicLastlyCnt);
		result.put("musicLastlyList", musicLastlyList);
		result.put("pageUtil", pageUtil);
		result.put("beginNo", pageUtil.getBegin());
		result.put("userNickName", userNickName);
	    
		return result;
	}
	
	// 2. 유저_최근들은, 많이들은 삭제
	@Override
	public Map<String, Object> deleteUserMusicLog(HttpServletRequest request) {
		
		// 1. 기초데이터
		
			// 파라미터 : likeNo
			Optional<String> opt = Optional.ofNullable(request.getParameter("musicNo")); 
			int musicNo = Integer.parseInt(opt.orElse("1"));
			
			// 유저 : email
			HttpSession session = request.getSession();
			UserDTO user = (UserDTO) session.getAttribute("loginUser");
			String email = user.getEmail();
			
			// map
			Map<String, Object> map = new HashMap<>();
			map.put("email", email);
			map.put("musicNo", musicNo);
			
			// 2. 좋아요 목록에서 삭제
			int deleteResult = musicMapper.deleteMusicLog(map);

			Map<String, Object> result = new HashMap<>();
			result.put("result", deleteResult);
			
			return result;
	}
	
	// # 많이들은
	
	// 1. 유저_많이들은 목록조회
	
	@Override
	public Map<String, Object> selectMusicManyList(HttpServletRequest request) {
		
		// 1. 기초데이터
				// 파라미터 : page 
				Optional<String> opt = Optional.ofNullable(request.getParameter("page")); 
				int page = Integer.parseInt(opt.orElse("1"));

				// 유저 : email
				HttpSession session = request.getSession();
				UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
				String email = loginUser.getEmail();
				String userNickName = loginUser.getArtist();
				
				// map
				Map<String, Object> map = new HashMap<>();
				map.put("email", email);
						
				// 2. 유저의 전체 좋아요 수 조회
				int userMusicManyCnt = musicMapper.selectUserMusicManyCnt(map);
				
				// 3. 페이징 처리
				pageUtil.setPageUtil(page, 10, userMusicManyCnt);

				map.put("begin", pageUtil.getBegin() - 1);
				map.put("recordPerPage", pageUtil.getRecordPerPage());
				
				// 4. 최근들은 목록 조회
			    List<MusicDTO> musicManyList = musicMapper.selectUserMusicManyList(map);
			    //System.out.println(musicLastlyList);
				
			    Map<String, Object> result = new HashMap<>();
				result.put("userMusicManyCnt", userMusicManyCnt);
				result.put("musicManyList", musicManyList);
				result.put("pageUtil", pageUtil);
				result.put("beginNo", pageUtil.getBegin());
				result.put("userNickName", userNickName);
			    
				return result;
	}
	
	// 3. 유저_많이들은 음악 전체삭제
	
	@Override
	public Map<String, Object> deleteALLUserMusicLog(HttpServletRequest request) {
		
		
		// 1. 기초데이터
		

			// 유저 : email
			HttpSession session = request.getSession();
			UserDTO user = (UserDTO) session.getAttribute("loginUser");
			String email = user.getEmail();
			
			// map
			Map<String, Object> map = new HashMap<>();
			map.put("email", email);

			
			// 2. 좋아요 목록에서 삭제
			int deleteAllResult = musicMapper.deleteAllMusicLog(map);

			Map<String, Object> result = new HashMap<>();
			result.put("result", 1);
			
			return result;

	
		
	}

	
	

}
