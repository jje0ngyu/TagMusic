package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.tagmusic.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentMapper paymentMapper;
	
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
		
		map.put("email", email);
		map.put("price", price);
		map.put("passNo", passNo);
		map.put("payPg", payPg);
		map.put("ticketName", ticketName);
		
		Map<String, Object> result = new HashMap<>();
		int passResult = paymentMapper.insertPayment(map);
		int logResult = paymentMapper.insertPaymentLog(map);
		
		if(passResult > 0 && logResult > 0) {
			result.put("result", 1);
		} else {
			result.put("result", 0);
		}
		
		return result;
	}
}
