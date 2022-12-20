package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.MusicDTO;

@Mapper
public interface HomeMapper {
	
	// [main 페이지]
	
	// # 최신리스트
	// 1) 전체 게시글 수 
	public int selectMusicCnt();
	// 2) 최신리스트 데이터 조회
	public List<MusicDTO> selectUpdatedMusicList(Map<String, Object> map);
	// 3) 썸네일 조회 : 음악 dto 한개 얻기 	
	public MusicDTO selectMusicByNo(int musicNo);
	
	// # 인기리스트
	// 1) 인기게시글 수 
	public int selectPopularMusicCnt();
	// 2) 인기리스트 데이터 조회
	public List<MusicDTO> selectPopularMusicList(Map<String, Object> map);
	// 3) 썸네일 조회 : 재활용
	
	// # 장르별 인기리스트
	// 1) 인기게시글수 : 재활용
	// 2) 장르별 인기리스트 데이터 조회
	public List<MusicDTO> selectPopularMusicGenreList(Map<String, Object> map);

	// # 전체검색
	// 1) 검색한 게시글 수 조회
	public int selectSearchMusicCnt(String query);
	// 2) 검색한 음악리스트 조회
	public List<MusicDTO> selectSearchMusicList(Map<String, Object> map);


	// # 랭킹
	public List<MusicDTO> selectMusicRanking10(Map<String, Object> map);

	
	

}
