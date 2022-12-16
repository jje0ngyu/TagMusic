package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.MusicDTO;

@Mapper
public interface HomeMapper {
	
	// [main 페이지]
	
	// # 구현 : 최신리스트바
	// 1) 전체 게시글 수 
	public int selectMusicCnt();
	
	// 2) 최신리스트 바 조회
	// * 차이점 : user테이블의 별명까지 가져옴
	public List<MusicDTO> selectUpdatedMusicList(Map<String, Object> map);
	
	// 3) 썸네일 조회 : 음악 dto 한개 얻기 			+ 상세화면에도 재활용
	public MusicDTO selectMusicByNo(int musicNo);
	
	// # 구현 : 최신리스트 게시판조회
	//public List<MusicDTO> selectUpdatedMusicList(Map<String, Object> map);


	
	

}
