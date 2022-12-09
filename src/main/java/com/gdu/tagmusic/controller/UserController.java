package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.tagmusic.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/login/form")
	public String loginForm(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getHeader("referer"));
		return "user/login";
	}
	
	@PostMapping("/user/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		userService.login(request, response);
	}
}
