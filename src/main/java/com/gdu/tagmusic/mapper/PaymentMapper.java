package com.gdu.tagmusic.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.PassDTO;

@Mapper
public interface PaymentMapper {
	public List<PassDTO> selectPass();
	public int insertPayment(Map<String, Object> map);
	public int insertPaymentLog(Map<String, Object> map);
	public int selectPaymentCnt(Map<String, Object> map);
	public int updatePaymentExtend(Map<String, Object> map);
	public int selectRemainiend(Map<String, Object> map);
	public Date selectPassDday(Map<String, Object> map);
	public int deletePass();
}
