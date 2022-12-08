package com.gdu.tagmusic.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
@Aspect
public class RequestLoggingAspect {
	
	
	// 로거
	private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingAspect.class);

	
	// 포인트컷 설정
	@Pointcut("within(com.gdu.semi.controller..*)")  
	public void setPointCut() { } 
	
	@Around("com.gdu.semi.aop.RequestLoggingAspect.setPointCut()")  
	public Object executeLogging(ProceedingJoinPoint joinPoint) throws Throwable { 
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, String[]> map = request.getParameterMap();
		String params = "";
		if(map.isEmpty()) {
			params += "[No Parameter]";
		} else {
			for(Map.Entry<String, String[]> entry : map.entrySet()) {				
				params += "[" + entry.getKey() + "=" + String.format("%s", (Object[])entry.getValue()) + "]";
			}
		}
		
		Object result = null;
		try {
			result = joinPoint.proceed(joinPoint.getArgs());
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info("{} {} {} > {}", request.getMethod(), request.getRequestURI(), params, request.getRemoteHost());
		}
		
		return result;
		
	}
	
}
