package com.gdu.tagmusic.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.PassDTO;
import com.gdu.tagmusic.domain.PaymentLogDTO;
import com.gdu.tagmusic.domain.UserDTO;

@Mapper
public interface PaymentMapper{
	public List<PassDTO> selectPass();
	public int insertPayment(Map<String, Object> map);
	public int insertPaymentLog(Map<String, Object> map);
	public int insertPaymentGiftLog(Map<String, Object> map);
	public int selectPaymentCnt(Map<String, Object> map);
	public int updatePaymentExtend(Map<String, Object> map);
	public Integer selectRemainiend(Map<String, Object> map);
	public Date selectPassDday(Map<String, Object> map);
	public int deletePass();
	public UserDTO selectRecipientByEmail(Map<String, Object> map);
	
	public int selectPaymentLogListCount(Map<String, Object> map);
	public List<PaymentLogDTO> selectPaymentLogList(Map<String, Object> map);
	
	public int deleteLogByNo(Map<String, Object> map);
	public int selectIsPaymentCnt(Map<String, Object> map);
}
