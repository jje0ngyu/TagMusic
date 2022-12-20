package com.gdu.tagmusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.tagmusic.interceptor.KeepLoginInterceptor;
import com.gdu.tagmusic.interceptor.PreventLoginInterceptor;
import com.gdu.tagmusic.interceptor.SleepUserCheckingInterceptor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private KeepLoginInterceptor keepLoginInterceptor;
	private SleepUserCheckingInterceptor sleepUserCheckingInterceptor;
	private PreventLoginInterceptor preventLoginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(keepLoginInterceptor)
			.addPathPatterns("/")
			.addPathPatterns("/customerService")
			.addPathPatterns("/admin/*")
			.excludePathPatterns("/login");

		registry.addInterceptor(sleepUserCheckingInterceptor)
			.addPathPatterns("/user/login");
		
		registry.addInterceptor(preventLoginInterceptor)
			.addPathPatterns("/user/login/form")
			.addPathPatterns("/user/join/*");

	}
	
<<<<<<< HEAD
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/load/image/**")
			.addResourceLocations("file:///C:/summernoteImage/");
	}
=======
	
	
	
	
	
	
>>>>>>> main
	
}
