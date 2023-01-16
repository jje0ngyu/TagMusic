package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.MusicCommentDTO;
import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface TuneMapper {
	
	public MusicDTO selectMusicByNo (int musicNo);
	
	// 음원 등록
	public int insertMusic (MusicDTO music);
	public int selectMaxMsicNoByEmail (String email);
	
	// 음원 다운로드
	public int updateDownloadCnt(int musicNo);
	
	// 작성한 음원 목록
	public List<MusicDTO> selectMusicsByEmail (String email);
	public int selectCntLikeByMusicNo (int musicNo);
	public int selectCntCommentByMusicNo (int musicNo);
	public int updateMusicByNo(MusicDTO musicNo);
	public int deleteMusicByNo(int musicNo);
	
	// 댓글
	public int selectCommentCount(int musicNo);
	public List<MusicCommentDTO> selectComment(Map<String, Object> map);
	public int insertComment(MusicCommentDTO comment);
	public int deleteComment(int commentNo);
	public int selectCommentByEmail(UserDTO user);
	
	// 음원 트랙
	public List<MusicDTO> selectMusic();
	public List<MusicDTO> selectMusicByRandom();
}
	

