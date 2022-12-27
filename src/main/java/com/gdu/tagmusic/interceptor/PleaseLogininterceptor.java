package com.gdu.tagmusic.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PleaseLogininterceptor implements HandlerInterceptor {
	
	// 로그인한 유저가 아니면 기능정지
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		if(request.getSession().getAttribute("loginUser") == null) {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("if(confirm('해당 기능은 회원만 이용가능합니다. 로그인하시겠습니까?')) {");
			out.println("location.href='/user/login/form';");
			out.println("} else {");
			out.println("location.href='/'; }");
			out.println("</script>");
			out.close();
			
			return false;  // 컨트롤러의 요청이 처리되지 않는다.
			
		} else {
			
			return true;   // 컨트롤러의 요청이 처리된다.
			
		}
		
	}
	
}
