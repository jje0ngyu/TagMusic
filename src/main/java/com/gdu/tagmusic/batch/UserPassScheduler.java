package com.gdu.tagmusic.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.tagmusic.mapper.PaymentMapper;
import com.gdu.tagmusic.service.PaymentService;

@EnableScheduling
@Component
public class UserPassScheduler{

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Scheduled(cron="0 0 4 * * *")
	public void execute() {
		paymentMapper.deletePass();
	}
	
}

