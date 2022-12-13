package com.gdu.tagmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.PassDTO;

@Mapper
public interface PaymentMapper {
	public List<PassDTO> selectPass();
}
