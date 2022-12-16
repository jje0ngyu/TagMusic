package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.BoardDTO;

@Mapper
public interface BoardMapper {
	
	public int insertBoard(BoardDTO board);
	public int insertSummernoteImage(com.gdu.tagmusic.domain.SummernoteImageDTO summernote);
	public int selectBoardListCount();
	public List<BoardDTO> selectBoardListByMap(Map<String, Object> map);
	
}
