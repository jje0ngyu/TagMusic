package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.tagmusic.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Override
	public Map<String, Object> getPassList() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("passList", paymentMapper.selectPass());
		
		System.out.println("map:" + map);
		return map;
	}
	
	@Override
	public Map<String, Object> buyPass(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String email = request.getParameter("email");
		String price = request.getParameter("price");
		String passNo = request.getParameter("passNo");
		String payPg = request.getParameter("payPg");
		
		map.put("email", email);
		map.put("price", price);
		map.put("passNo", passNo);
		map.put("payPg", payPg);
		System.out.println();
		
		Map<String, Object> result = new HashMap<>();
		int rrr = paymentMapper.insertPayment(map);
		if(rrr > 0) {
			//paymentMapper.();
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
		
		return result;
	}
}
