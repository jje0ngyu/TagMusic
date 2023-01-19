package com.gdu.tagmusic.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
	
	public int selectUserLike (Map<String, Object> map);
	public int insertLike (Map<String, Object> map);
	public int deleteLike (Map<String, Object> map);
	
}