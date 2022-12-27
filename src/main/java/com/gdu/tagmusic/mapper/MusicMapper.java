package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.domain.MyMusicDTO;
import com.gdu.tagmusic.domain.PlaylistDTO;

@Mapper
public interface MusicMapper {
	
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
	
	// [유저서비스]
	// # 유저 플레이리스트 페이지 이동
	
	// 1) 유저의 플레이리스트 개수
	public int 	selectUserPlaylistCnt(Map<String, Object> map);
		
	// 2) 유저의 플레이리스트 조회
	public List<PlaylistDTO> selectUserPlaylist(Map<String, Object> map);

	// 3) 플레이리스트 수록곡 수 조회
	public int selectUserPlaylistMusicCnt(int listNo);
	
	// 4) 플레이리스트 수록곡 목록 조회
	public List<MyMusicDTO> selectUserPlaylistMusiclist(Map<String, Object> map);

	// 5) 플레이리스트명 수정
	public int updatePlaylistName(Map<String, Object> map);

	// 6) 플레이리스트 삭제
	public int deletePlaylist(Map<String, Object> map);
	
	// 7) 수록곡 삭제
	public int deletePlaylistMusic(Map<String, Object> map);
	
	// 8) 수록곡 추가
	public int insertMusicToPlaylist(Map<String, Object> map);
	
	// 9) 제약 : 플레이리스트에 해당 음악존재여부
	public MyMusicDTO checkMusicInPlaylist(Map<String, Object> map);
	
	// 10) 플레이리스트 생성
	public int insertPlaylist(Map<String, Object> map);
	
	// 11) 유저의 플레이리스트 번호조회 : 디폴트곡 넣기용
	public int selectPlaylistNo(Map<String, Object> map);
	
	// 12) 디폴트곡 넣기
	public int insertDefaultMusicToPlaylist(Map<String, Object> map);
	
	// 13) 제약 : 플레이리스트명 확인_이름으로확인
	public PlaylistDTO checkPlaylistAtUserByListName(Map<String, Object> map);

	// 14) 제약 : 플레이리스트 개수 확인
	public int checkUserPlaylistCnt(Map<String, Object> map);
	
	
	
	
	/*
	 * // 2) 유저의 플레이리스트 썸네일 반환 public MusicDTO selectUserPlaylistThumbnail(int
	 * listNo);
	 * 
	 * // 3) 플레이리스트 추가 public int insertPlaylist(Map<String, Object> map);
	 * 
	 * // 4) 플레이리스트 개수 방지 : 인터셉터 public int selectUserMusiclistCntInercet(String
	 * email);
	 * 
	 * // 5) 플레이리스트 이름 받기 //public String selectPlaylistName(int listNo);
	 * 
	 * // 6) 플레이리스트 썸네일 여부 조회하기 public List<PlaylistDTO>
	 * selectPlaylistMusicThumnail(Map<String, Object> map);
	 * 
	 * // 7) 플레이리스트 리스트명 받아오기 public String selectUserPlaylistName(int listNo);
	 * 
	 * // 8) 플레이리스트명 수정 public int updateMusiclistName(Map<String, Object> map);
	 * 
	 * // 9) 플레이리스트 삭제 public int deletePalylist(Map<String, Object> map);
	 */
	
	
	/*
	 * // 3) 해당 유저 플레이리스트별 곡수 public List<Integer>
	 * selectUserMusicListMusicCnt(Map<String, Object> map);
	 */
	// 4) 유저의 플레이리스트 LIST_NO
	//public List<Integer> selectUserPlaylistNoList(Map<String, Object> map);

	
	

}
