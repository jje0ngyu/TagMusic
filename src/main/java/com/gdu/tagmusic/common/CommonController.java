package com.gdu.tagmusic.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

	// # page : main 페이지 이동
	@GetMapping("/")
	public String index() {
		return "index";
	}

}
