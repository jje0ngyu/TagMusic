package com.gdu.tagmusic.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.tagmusic.domain.RetireUserDTO;
import com.gdu.tagmusic.domain.SleepUserDTO;
import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.UserMapper;
import com.gdu.tagmusic.util.JavaMailUtil;
import com.gdu.tagmusic.util.MyFileUtil;
import com.gdu.tagmusic.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	private MyFileUtil myFileUtil;
	private SecurityUtil securityUtil;
	private JavaMailUtil javaMailUtil;
	
	// 로그인
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) {
		
		String url = request.getParameter("url");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		pw = securityUtil.sha256(pw);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("pw", pw);
		UserDTO loginUser = userMapper.selectUserByMap(map);
		System.out.println("loginUser" + loginUser);
		// id, pw가 일치하는 회원이 있다 : session에 loginUser 저장하기 + 로그인 기록 남기기 
		if(loginUser != null) {
			
			// 로그인 유지 처리는 keepLogin 메소드가 따로 처리함
			keepLogin(request, response);
			
			// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
			request.getSession().setAttribute("loginUser", loginUser);
			/*
			// 로그인 기록 남기기
			int updateResult = userMapper.updateAccessLog(email);
			if(updateResult == 0) {
				userMapper.insertAccessLog(email);
			}
			*/
			// 이동 (로그인페이지 이전 페이지로 되돌아가기)
			try {
				response.sendRedirect(url);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		// id, pw가 일치하는 회원이 없다 : 로그인 페이지로 돌려 보내기
		else {
			
			// 응답
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('일치하는 회원 정보가 없습니다.');");
				out.println("location.href='/user/login/form';");
				out.println("</script>");
				out.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	// 로그인 - 로그인 유지
	@Override
	public void keepLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String email = request.getParameter("email");
		String keepLogin = request.getParameter("keepLogin");
		
		if(keepLogin != null) {
			String sessionId = request.getSession().getId();
			Cookie cookie = new Cookie("keepLogin", sessionId);
			cookie.setMaxAge(60 * 60 * 24 * 15);  // 15일
			cookie.setPath("/");
			response.addCookie(cookie);
			
			UserDTO user = UserDTO.builder()
					.email(email)
					.sessionId(sessionId)
					.sessionLimitDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))  // 현재타임스탬프 + 15일에 해당하는 타임스탬프
					.build();

			userMapper.updateSessionInfo(user);
			
		}
		else {
			Cookie cookie = new Cookie("keepLogin", "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}
	
	// 로그인 - 자동로그인
	@Override
	public UserDTO getUserBySessionId(Map<String, Object> map) {
		return userMapper.selectUserByMap(map);
	}
	
	// 회원가입
	@Override
	public Map<String, Object> isReduceEmail(String email) {
		
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isUser", userMapper.selectUserByMap(map) != null);
		return result;
		
	}
	
	@Override
	public Map<String, Object> sendAuthCode(String email) {
		
		// 인증코드 만들기
		String authCode = securityUtil.getAuthCode(6);  // String authCode = securityUtil.generateRandomString(6);
		// System.out.println("발송된 인증코드 : " + authCode);
		
		// 메일 전송
		javaMailUtil.sendJavaMail(email, "[Application] 인증요청", "인증번호는 <strong>" + authCode + "</strong>입니다.");
		
		// join.jsp로 생성한 인증코드를 보내줘야 함
		// 그래야 사용자가 입력한 인증코드와 비교를 할 수 있음
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("authCode", authCode);
		System.out.println(result);
		return result;
		
	}
	
	@Transactional  // INSERT,UPDATE,DELETE 중 2개 이상이 호출되는 서비스에서 필요함
	@Override
	public void join(HttpServletRequest request, HttpServletResponse response) {
		
		// 파라미터
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String artist = request.getParameter("artist");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String birthyear = request.getParameter("birthyear");
		String birthmonth = request.getParameter("birthmonth");
		String birthdate = request.getParameter("birthdate");
		String postcode = request.getParameter("postcode");
		String roadAddress = request.getParameter("roadAddress");
		String jibunAddress = request.getParameter("jibunAddress");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");
		String location = request.getParameter("location");
		String promotion = request.getParameter("promotion");
		
		// 일부 파라미터는 DB에 넣을 수 있도록 가공
		pw = securityUtil.sha256(pw);
		name = securityUtil.preventXSS(name);
		String birthday = birthmonth + birthdate;
		detailAddress = securityUtil.preventXSS(detailAddress);
		int agreeCode = 0;  // 필수 동의
		if(!location.isEmpty() && promotion.isEmpty()) {
			agreeCode = 1;  // 필수 + 위치
		} else if(location.isEmpty() && !promotion.isEmpty()) {
			agreeCode = 2;  // 필수 + 프로모션
		} else if(!location.isEmpty() && !promotion.isEmpty()) {
			agreeCode = 3;  // 필수 + 위치 + 프로모션
		}
		
		
		// DB로 보낼 UserDTO 만들기
		UserDTO user = UserDTO.builder()
				.email(email)
				.pw(pw)
				.name(name)
				.artist(artist)
				.gender(gender)
				.mobile(mobile)
				.birthyear(birthyear)
				.birthday(birthday)
				.postcode(postcode)
				.roadAddress(roadAddress)
				.jibunAddress(jibunAddress)
				.detailAddress(detailAddress)
				.extraAddress(extraAddress)
				.agreeCode(agreeCode)
				.build();
				
		// 회원가입처리
		int result = userMapper.insertUser(user);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				
				// 조회 조건으로 사용할 Map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("email", email);
				
				// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
				request.getSession().setAttribute("loginUser", userMapper.selectUserByMap(map));
				
				/*
				// 로그인 기록 남기기
				int updateResult = userMapper.updateAccessLog(email);
				if(updateResult == 0) {
					userMapper.insertAccessLog(email);
				}
				*/
				out.println("<script>");
				out.println("alert('회원 가입되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('회원 가입에 실패했습니다.');");
				out.println("history.go(-2);");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 회원정보 수정 - 프로필 이미지 가져오기
	@Override
	public Map<String, Object> getImage(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 회원정보 수정 - 닉네임
	@Override
	public void modifyArtist(HttpServletRequest request, HttpServletResponse response) {
		
		// 파라미터
		// 정보 일치용 (where)
		String email = request.getParameter("email");
		String artist = request.getParameter("artist");
		
		// 일부 파라미터는 DB에 넣을 수 있도록 가공
		artist = securityUtil.preventXSS(artist);
		
		// DB로 보낼 UserDTO 만들기
		UserDTO user = UserDTO.builder()
				.email(email)
				.artist(artist)
				.build();
				
		// 회원정보수정
		int result = userMapper.updateUser(user);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			if(result > 0) {
				// 조회 조건으로 사용할 Map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("email", email);
				// session에 올라간 정보를 수정된 내용으로 업데이트
				request.getSession().setAttribute("loginUser", userMapper.selectUserByMap(map));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 회원정보수정 - 실명
	@Override
	public void modifyName(HttpServletRequest request, HttpServletResponse response) {
		
		// 파라미터
		// 정보 일치용 (where)
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		
		// DB로 보낼 UserDTO 만들기
		UserDTO user = UserDTO.builder()
				.email(email)
				.name(name)
				.build();
				
		// 회원정보수정
		int result = userMapper.updateUser(user);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			if(result > 0) {
				// 조회 조건으로 사용할 Map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("email", email);
				// session에 올라간 정보를 수정된 내용으로 업데이트
				request.getSession().setAttribute("loginUser", userMapper.selectUserByMap(map));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 회원정보수정 - 휴대폰
	@Override
	public void modifyMobile(HttpServletRequest request, HttpServletResponse response) {
		
		// 파라미터
		// 정보 일치용 (where)
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		
		// DB로 보낼 UserDTO 만들기
		UserDTO user = UserDTO.builder()
				.email(email)
				.mobile(mobile)
				.build();
				
		// 회원정보수정
		int result = userMapper.updateUser(user);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			if(result > 0) {
				// 조회 조건으로 사용할 Map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("email", email);
				// session에 올라간 정보를 수정된 내용으로 업데이트
				request.getSession().setAttribute("loginUser", userMapper.selectUserByMap(map));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 휴면
	@Transactional
	@Override
	public void sleepUserHandle() {
		int insertCount = userMapper.insertSleepUser();
		if(insertCount > 0) {
			userMapper.deleteUserForSleep();
		}
	}
	@Override
	public SleepUserDTO getSleepUserById(String id) {
		return userMapper.selectSleepUserById(id);
	}
	
	// 로그아웃
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		// 로그아웃 처리
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") != null) {
			session.invalidate();
		}
		
		// 로그인 유지 풀기
		Cookie cookie = new Cookie("keepLogin", "");
		cookie.setMaxAge(0);  // 쿠키 유지 시간이 0이면 삭제를 의미함
		cookie.setPath("/");
		response.addCookie(cookie);
		
	}
	
	// 탈퇴
	@Transactional
	@Override
	public Map<String, Object> retire(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		// 비밀번호 일치하는지 확인
		String email = ((UserDTO)session.getAttribute("loginUser")).getEmail();
		String pw = securityUtil.sha256(request.getParameter("pw"));
		
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("pw", pw);
		UserDTO selectUser = userMapper.selectUserByMap(map);
		
		// 탈퇴 결과
		Map<String, Object> result = new HashMap<>();
		
		// 검색된 결과가 있으면 탈퇴 진행
		if (selectUser != null) {
				// 탈퇴시킬 유저정보 저장
				UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
				
				// 탈퇴할 회원 RetireUserDTO 생성
				RetireUserDTO retireUser = RetireUserDTO.builder()
						.userNo(loginUser.getUserNo())
						.email(loginUser.getEmail())
						.artist(loginUser.getArtist())
						.build();
				
				// 탈퇴처리
				int deleteResult = userMapper.deleteUser(loginUser.getUserNo());
				int insertResult = userMapper.insertRetireUser(retireUser);
				
				// 응답
				try {
					response.setContentType("text/html; charset=UTF-8");
					if(deleteResult > 0 && insertResult > 0) {
						// session 초기화(로그인 사용자 loginUser 삭제를 위해서)
						session.invalidate();
						result.put("resData", 1);
					}
					
				} catch(Exception e) {
					result.put("resData", 1);
					e.printStackTrace();
				}
		// 검색된 결과가 없다
		} else {
			result.put("resData", 0);
		}
		
		return result;
	}

}
