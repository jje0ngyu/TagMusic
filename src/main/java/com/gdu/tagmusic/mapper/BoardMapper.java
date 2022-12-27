package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.BoardDTO;
import com.gdu.tagmusic.domain.SummernoteImageDTO;

@Mapper
public interface BoardMapper {
	
	public int selectBoardListCount(); 
	public List<BoardDTO> selectBoardListByMap(Map<String, Object> map);
	public int insertSummernoteImage(SummernoteImageDTO summernote);
	public int insertBoard(BoardDTO board);
	public int updateHit(int boardNo);
	public BoardDTO selectBoardByNo(int boardNo);
	public int updateBoard(BoardDTO board);
	public int deleteBoard(int boardNo);
	public List<SummernoteImageDTO> selectSummernoteImageListInBoard(int boardNo);
	public List<SummernoteImageDTO> selectAllSummernoteImageList();
	public int deleteSummernoteImage(String filesystem);
	
}