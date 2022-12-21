package com.gdu.tagmusic.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.MusicDTO;

@Mapper
public interface TuneMapper {
	
	public MusicDTO selectMusicByNo (int musicNo);
	public int insertMusic (MusicDTO music);
	
	// 음원 다운로드
	public int updateDownloadCnt(int musicNo);
}
	

