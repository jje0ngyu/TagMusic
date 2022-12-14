package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.AlarmDTO;
import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface AlarmMapper{
	
	public List<AlarmDTO> selectIsAlarmByEmail(String email);
	public int insertAlarm(Map<String, Object> map);
	public int deleteAlarmByEmail(String email);
	
	public List<UserDTO> selectAllUserEmail();
	
}
