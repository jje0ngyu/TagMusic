package com.gdu.tagmusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.tagmusic.interceptor.KeepLoginInterceptor;
import com.gdu.tagmusic.interceptor.PleaseLogininterceptor;
import com.gdu.tagmusic.interceptor.PreventCreatePlaylist;
import com.gdu.tagmusic.interceptor.PreventLoginInterceptor;
import com.gdu.tagmusic.interceptor.SleepUserCheckingInterceptor;
import com.gdu.tagmusic.util.MyFileUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private KeepLoginInterceptor keepLoginInterceptor;
	private SleepUserCheckingInterceptor sleepUserCheckingInterceptor;
	private PreventLoginInterceptor preventLoginInterceptor;
	private PleaseLogininterceptor pleaseLogininterceptor;
	private PreventCreatePlaylist preventCreatePlaylist;
	private MyFileUtil myFileUtil;
	
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
			
		
		// 비로그인 유저방지 인터셉터
		registry.addInterceptor(pleaseLogininterceptor)
		.addPathPatterns("/music/move/playlist")	
		.addPathPatterns("/music/move/musicLike")	
		.addPathPatterns("/music/move/musicLastly")	
		.addPathPatterns("/music/move/playlistModal");	// 플레이리스트
		
		// 플레이리스트 5개 초과방지 
		registry.addInterceptor(preventCreatePlaylist)
		.addPathPatterns("/music/move/playlistCreate");	// 플레이리스트
		

		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/load/image/**")
		.addResourceLocations("file:" + myFileUtil.getSummernotePath() + "/");
	}
	
}
