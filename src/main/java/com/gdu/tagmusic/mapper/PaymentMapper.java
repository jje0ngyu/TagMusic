package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.PassDTO;

@Mapper
public interface PaymentMapper {
	public List<PassDTO> selectPass();
	public int insertPayment(Map<String, Object> map);
}
