package com.gdu.tagmusic.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	public void addMusic(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		String userNo = request.getParameter("userNo");	// userNo 를 사용하지는 확인필요
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
			result.put("result", tuneMapper.insertMusic(music));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 음원 상세보기
	@Override
	public MusicDTO getMusicByNo(int musicNo) {
		MusicDTO music = tuneMapper.selectMusicByNo(musicNo);
		return music;
	}
}
