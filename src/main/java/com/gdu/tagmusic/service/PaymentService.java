
package com.gdu.tagmusic.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PaymentService {
	public Map<String, Object> getPassList();
	public Map<String, Object> buyPass(HttpServletRequest request);
	public Map<String, Object> getRemainindperiod(HttpServletRequest request);
	public Map<String, Object> selectRecipientByEmail(HttpServletRequest request);
	public Map<String, Object> presentPass(HttpServletRequest request);
	
	public Map<String, Object> getLogList(HttpServletRequest request);
	public Map<String, Object> removeLog(List<String> payLogNo);
	
	
}
