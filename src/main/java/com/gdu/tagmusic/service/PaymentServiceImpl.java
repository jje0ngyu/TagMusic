package com.gdu.tagmusic.service;

import java.util.HashMap;
import java.util.Map;

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
}
