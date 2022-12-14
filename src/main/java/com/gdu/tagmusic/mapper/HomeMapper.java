package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.MusicDTO;

@Mapper
public interface HomeMapper {
	
	// # main화면 최신리스트 조회
	// 1) 전체 음악리스트 게시글 수
	public int selectMusicCnt();
	
	// 2) 전체 음악리스트 조회
	public List<MusicDTO> selectMusicList(Map<String, Object> map);
	
	// 3) 썸네일 조회 : 음악 dto 한개 얻기 			+ 상세화면에도 재활용
	public MusicDTO selectMusicByNo(int musicNo);
	


	
	

}
