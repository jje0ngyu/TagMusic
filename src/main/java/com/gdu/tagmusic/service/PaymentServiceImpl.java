package com.gdu.tagmusic.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.tagmusic.mapper.PaymentMapper;
import com.gdu.tagmusic.util.PageUtil;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Autowired 
	private PageUtil pageUtil;
	
	@Override
	public Map<String, Object> getPassList() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("passList", paymentMapper.selectPass());
		
		return map;
	}
	
	@Transactional
	@Override
	public Map<String, Object> buyPass(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String email = request.getParameter("email");
		String price = request.getParameter("price");
		String passNo = request.getParameter("passNo");
		String payPg = request.getParameter("payPg");
		String ticketName = request.getParameter("ticketName");
		String merchantUid = request.getParameter("merchantUid");
		
		map.put("email", email);
		map.put("price", price);
		map.put("passNo", passNo);
		map.put("payPg", payPg);
		map.put("ticketName", ticketName);
		map.put("merchantUid", merchantUid);
		
		int paymentCnt = paymentMapper.selectPaymentCnt(map);
		System.out.println(paymentCnt);
		Map<String, Object> result = new HashMap<>();
		if(paymentCnt == 0) {
			int passResult = paymentMapper.insertPayment(map);
			int logResult = paymentMapper.insertPaymentLog(map);
			if(passResult > 0 && logResult > 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		} else if (paymentCnt >= 1){
			int extendResult = paymentMapper.updatePaymentExtend(map);
			int logResult = paymentMapper.insertPaymentLog(map);
			if(extendResult > 0 && logResult > 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		}
		return result;
	}
	
	@Transactional
	@Override
	public Map<String, Object> presentPass(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<>();
		String email = request.getParameter("email");
		String price = request.getParameter("price");
		String passNo = request.getParameter("passNo");
		String payPg = request.getParameter("payPg");
		String ticketName = request.getParameter("ticketName");
		String merchantUid = request.getParameter("merchantUid");
		String sessionEmail = request.getParameter("sessionEmail");
		
		map.put("email", email);
		map.put("price", price);
		map.put("passNo", passNo);
		map.put("payPg", payPg);
		map.put("ticketName", ticketName);
		map.put("merchantUid", merchantUid);
		map.put("sessionEmail", sessionEmail);
		
		int paymentCnt = paymentMapper.selectPaymentCnt(map);
		System.out.println(paymentCnt);
		Map<String, Object> result = new HashMap<>();
		if(paymentCnt == 0) {
			int passResult = paymentMapper.insertPayment(map);
			int logResult = paymentMapper.insertPaymentGiftLog(map);
			if(passResult > 0 && logResult > 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		} else if (paymentCnt >= 1){
			int extendResult = paymentMapper.updatePaymentExtend(map);
			int logResult = paymentMapper.insertPaymentGiftLog(map);
			if(extendResult > 0 && logResult > 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		}
		return result;
	}
	
	@Override
	public Map<String, Object> getRemainindperiod(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String email = request.getParameter("email");
		map.put("email", email);
		int remaining = paymentMapper.selectRemainiend(map);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dDay = paymentMapper.selectPassDday(map);
		Map<String, Object> result = new HashMap<>();
		result.put("remainingDay", sdf1.format(dDay));
		result.put("dDay", remaining);
		return result;
	}
	
	@Override
	public Map<String, Object> selectRecipientByEmail(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		String email = request.getParameter("email");
		result.put("email", email);
		result.put("result", paymentMapper.selectRecipientByEmail(result));
		System.out.println(result.get("result"));
		return result;
	}
	
	@Override
	public Map<String, Object> getLogList(HttpServletRequest request) {
		String email = request.getParameter("email");
		int page = Integer.parseInt(request.getParameter("page"));
		int logCount = paymentMapper.selectPaymentLogListCount();
		pageUtil.setPageUtil(page, 10, logCount);
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<>();
		result.put("logList", paymentMapper.selectPaymentLogList(map));
		result.put("pageUtil", pageUtil);
		
		return result;
	}
	
	@Override
	public Map<String, Object> removeLog(List<String> payLogNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("payLogNo", payLogNo);
		Map<String, Object> result = new HashMap<>();
		result.put("result", paymentMapper.deleteLogByNo(map));
		return result;
	}
}
