package com.gdu.tagmusic.service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.MusicCommentDTO;
import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.mapper.TuneMapper;
import com.gdu.tagmusic.util.MyFileUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TuneServiceImpl implements TuneService {

	private TuneMapper tuneMapper;
	private MyFileUtil myFileUtil; 
	
	
	// 음원 등록
	@Override
	public int addMusic(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		String email = request.getParameter("email");
		String musicAlbum = request.getParameter("musicAlbum"); // 앨범이름
		String musicTitle = request.getParameter("musicTitle"); // 음원이름
		String musicGenre = request.getParameter("musicGenre"); // 음원장르
		String musicContent = request.getParameter("musicContent"); // 음원가사및내용
		
		Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
		String ip = opt.orElse(request.getRemoteAddr());
		
		String imgOrigin = "";
		String imgFilesystem = "";
		String imgPath = "";
		String musicOrigin = "";
		String musicFilesystem = "";
		String musicPath = "";
		int hasThumbnail = 0;
		int musicNo = 0;
		// 첨부된 파일 목록
		// 이미지
		MultipartFile imgFile= request.getFile("image");  // <input type="file" name="file">
		// 음악파일
		MultipartFile musicFile= request.getFile("music");  // <input type="file" name="file">
				
		int attachResult;
		if(imgFile.getSize() == 0) { // 첨부가 없는 경우, 1
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		try {
			
			// 이미지 - 첨부가 있는지 점검
			if(imgFile != null && imgFile.isEmpty() == false) {  // 둘 다 필요함
				hasThumbnail = 1;
				// 원래 이름
				imgOrigin = imgFile.getOriginalFilename();
				imgOrigin = imgOrigin.substring(imgOrigin.lastIndexOf("\\") + 1);  // IE는 origin에 전체 경로가 붙어서 파일명만 사용해야 함
				
				// 저장할 이름
				imgFilesystem = myFileUtil.getFilename(imgOrigin);
				
				// 저장할 경로
				imgPath = myFileUtil.getTodayPath();
				
				// 저장할 경로 만들기
				File dir = new File(imgPath);
				if(dir.exists() == false) {
					dir.mkdirs();
				}
			// 첨부할 File 객체
			File file = new File(dir, imgFilesystem);
			
			// 이미지 첨부파일 서버에 저장(업로드 진행)
			imgFile.transferTo(file);
			}
			
			// 음악파일 - 첨부가 있는지 점검
			if(musicFile != null && musicFile.isEmpty() == false) {  // 둘 다 필요함
				
				// 원래 이름
				musicOrigin = musicFile.getOriginalFilename();
				musicOrigin = musicOrigin.substring(musicOrigin.lastIndexOf("\\") + 1);  // IE는 origin에 전체 경로가 붙어서 파일명만 사용해야 함
				
				// 저장할 이름
				musicFilesystem = myFileUtil.getFilename(musicOrigin);
				
				// 저장할 경로
				musicPath = myFileUtil.getTodayPath();
				
				// 저장할 경로 만들기
				File dir = new File(musicPath);
				if(dir.exists() == false) {
					dir.mkdirs();
				}	
			// 첨부할 File 객체
			File file = new File(dir, musicFilesystem);
			
			// 음악 첨부파일 서버에 저장(업로드 진행)
			musicFile.transferTo(file);
			
			}
			// DB로 보낼 MusicDTO 만들기
			MusicDTO music = MusicDTO.builder()
					.email(email)
					.musicAlbum(musicAlbum)
					.musicContent(musicContent)
					.musicTitle(musicTitle)
					.musicGenre(musicGenre)
					.imgOrigin(imgOrigin)
					.imgFilesystem(imgFilesystem)
					.imgPath(imgPath)
					.musicOrigin(musicOrigin)
					.musicFilesystem(musicFilesystem)
					.musicPath(musicPath)
					.ip(ip)
					.hasThumbNail(hasThumbnail)
					.build();
			Map<String, Object> result = new HashMap<>();
			int insertSuccess = tuneMapper.insertMusic(music);
			if(insertSuccess >= 1) {
				musicNo = tuneMapper.selectMaxmMsicNoByEmail(email);
			}
			result.put("result", insertSuccess);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return musicNo;
		
	}
	
	
	// 음원 상세보기
	@Override
	public MusicDTO getMusicByNo(int musicNo) {
		MusicDTO music = tuneMapper.selectMusicByNo(musicNo);
		return music;
	}
	
	// 음원 상세보기 - 음악 display
	@Override
	public ResponseEntity<byte[]> displayMusic(int musicNo) {
		MusicDTO music = tuneMapper.selectMusicByNo(musicNo);
		File musicFile = new File(music.getMusicPath(), music.getMusicFilesystem());
		ResponseEntity<byte[]> result = null;

		try {

			if(music.getHasThumbNail() == 1) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(musicFile.toPath()));
				File thumbnail = new File(music.getMusicPath(), music.getMusicFilesystem());
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), null, HttpStatus.OK);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	// 음원 상세보기 - 이미지 display
	@Override
	public ResponseEntity<byte[]> displayImage(int musicNo) {
		MusicDTO music = tuneMapper.selectMusicByNo(musicNo);
		File imgFile = new File(music.getImgPath(), music.getImgFilesystem());
		ResponseEntity<byte[]> result = null;

		try {

			if(music.getHasThumbNail() == 1) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(imgFile.toPath()));
				File image = new File(music.getImgPath(), music.getImgFilesystem());
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(image), null, HttpStatus.OK);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public ResponseEntity<Resource> download(String userAgent, int musicNo) {
		// 다운로드 할 첨부 파일의 정보(경로, 이름)
		MusicDTO music = tuneMapper.selectMusicByNo(musicNo);
		File file = new File(music.getMusicPath(), music.getMusicFilesystem());
				
		// 반환할 Resource
		Resource resource = new FileSystemResource(file);
				
		// Resource가 없으면 종료 (다운로드할 파일이 없음)
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
				
		// 다운로드 횟수 증가
		tuneMapper.updateDownloadCnt(musicNo);
				
		// 다운로드 되는 파일명(브라우저 마다 다르게 세팅)
		String origin = music.getMusicOrigin();
		try {
					
			// IE (userAgent에 "Trident"가 포함되어 있음)
			if(userAgent.contains("Trident")) {
				origin = URLEncoder.encode(origin, "UTF-8").replaceAll("\\+", " ");
			}
			// Edge (userAgent에 "Edg"가 포함되어 있음)
			else if(userAgent.contains("Edg")) {
				origin = URLEncoder.encode(origin, "UTF-8");
			}
			// 나머지
			else {
				origin = new String(origin.getBytes("UTF-8"), "ISO-8859-1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 다운로드 헤더 만들기
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + origin);
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	
	}
	
	
	// 댓글 - 삽입
	@Override
	public Map<String, Object> addComment(MusicCommentDTO comment) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdd", tuneMapper.insertComment(comment) > 0);			
		
		return result;
	}
}
