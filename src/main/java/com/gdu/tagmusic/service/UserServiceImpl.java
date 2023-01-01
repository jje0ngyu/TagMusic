package com.gdu.tagmusic.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.MusicDTO;
import com.gdu.tagmusic.domain.ProfileImageDTO;
import com.gdu.tagmusic.domain.RetireUserDTO;
import com.gdu.tagmusic.domain.SleepUserDTO;
import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.mapper.AlarmMapper;
import com.gdu.tagmusic.mapper.UserMapper;
import com.gdu.tagmusic.util.JavaMailUtil;
import com.gdu.tagmusic.util.MyFileUtil;
import com.gdu.tagmusic.util.SecurityUtil;

import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	private SecurityUtil securityUtil;
	private JavaMailUtil javaMailUtil;
	private MyFileUtil myFileUtil;
	private AlarmMapper alarmMapper;
	
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
		
		// id, pw가 일치하는 회원이 있다 : session에 loginUser 저장하기 + 로그인 기록 남기기 
		if(loginUser != null) {
			
			// 로그인 유지 처리는 keepLogin 메소드가 따로 처리함
			keepLogin(request, response);
			
			// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
			request.getSession().setAttribute("loginUser", loginUser);
			
			// 로그인 기록 남기기
			int updateResult = userMapper.updateAccessLog(email);
			if(updateResult == 0) {
				userMapper.insertAccessLog(email);
			}
			
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
				out.println("history.back();");
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
	
	// 로그인 - 네이버 간편로그인 1
	@Override
	public String getNaverLoginApiURL(HttpServletRequest request) {
	    
		String apiURL = null;
		
		try {
			
			String clientId = "mtNSZZEJIiSUesKY51WB";
			String redirectURI = URLEncoder.encode("http://localhost:9090/user/naver/login", "UTF-8");  // 네이버 로그인 Callback URL에 작성한 주소 입력 
			SecureRandom random = new SecureRandom();
			String state = new BigInteger(130, random).toString();
			
			apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
			apiURL += "&client_id=" + clientId;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&state=" + state;
			
			HttpSession session = request.getSession();
			session.setAttribute("state", state);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiURL;
		
	}
	// 로그인 - 네이버 간편로그인 2
	@Override
	public String getNaverLoginToken(HttpServletRequest request) {
		// access_token 받기
		String clientId = "mtNSZZEJIiSUesKY51WB";
		String clientSecret = "sCWaP84Z8a";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String redirectURI = null;
		try {
			redirectURI = URLEncoder.encode("http://localhost:9090/", "UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		StringBuffer res = new StringBuffer();  // StringBuffer는 StringBuilder과 동일한 역할 수행
		try {
			String apiURL;
			apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;
			
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			con.disconnect();
			
			/*
				res.toString()
				
				{
					"access_token":"AAAANipjD0VEPFITQ50DR__AgNpF2hTecVHIe9v-_uoyK5eP1mfdYX57bM3VTF_x4cWgz0v2fQlZsOOjl9uS0j8CLI4",
					"refresh_token":"2P9T9LTrnjaBf8XwF87a2UNUL4isfvk3QyLF8U1MDmju5ViiSXNSxii80ii8kvZWDiiYSiptFFYsuwqWl6C8n59NwoAEU6MmipfIis2htYMnZUlutzvRexh0PIZzzqqK3HlGYttJ",
					"token_type":"bearer",
					"expires_in":"3600"
				}
			*/
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject(res.toString());
		String access_token = obj.getString("access_token");
		return access_token;
	}
	// 로그인 - 네이버 간편로그인 3
	@Override
	public UserDTO getNaverLoginProfile(String access_token) {
		// access_token을 이용해서 profile 받기
		String header = "Bearer " + access_token;
		
		StringBuffer sb = new StringBuffer();
		
		try {
			
			String apiURL = "https://openapi.naver.com/v1/nid/me";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			con.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 받아온 profile을 UserDTO로 만들어서 반환
		UserDTO user = null;
		try {
			
			JSONObject profile = new JSONObject(sb.toString()).getJSONObject("response");
			String id = profile.getString("id");
			String name = profile.getString("name");
			String gender = profile.getString("gender");
			String email = profile.getString("email");
			String mobile = profile.getString("mobile").replaceAll("-", "");
			String birthyear = profile.getString("birthyear");
			String birthday = profile.getString("birthday").replace("-", "");
			
			user = UserDTO.builder()
					.artist(id)
					.name(name)
					.gender(gender)
					.email(email)
					.mobile(mobile)
					.birthyear(birthyear)
					.birthday(birthday)
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return user;
	}
	// 로그인 - 네이버 간편로그인4
	@Override
	public UserDTO getNaverUserById(String email) {
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		
		return userMapper.selectUserByMap(map);
	}
	
	@Transactional
	@Override
	public void naverLogin(HttpServletRequest request, UserDTO naverUser) {
		// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
		request.getSession().setAttribute("loginUser", naverUser);
		
		// 로그인 기록 남기기
		String email = naverUser.getEmail();
		int updateResult = userMapper.updateAccessLog(email);
		if(updateResult == 0) {
			userMapper.insertAccessLog(email);
		}
		
	}
	@Override
	public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		String id = request.getParameter("artist");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String birthyear = request.getParameter("birthyear");
		String birthmonth = request.getParameter("birthmonth");
		String birthdate = request.getParameter("birthdate");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		String promotion = request.getParameter("promotion");
		
		// 일부 파라미터는 DB에 넣을 수 있도록 가공
		name = securityUtil.preventXSS(name);
		String birthday = birthmonth + birthdate;
		String pw = securityUtil.sha256(birthyear + birthday);  // 생년월일을 초기비번 8자리로 제공하기로 함
		
		int agreeCode = 0;  // 필수 동의
		if(location != null && promotion == null) {
			agreeCode = 1;  // 필수 + 위치
		} else if(location == null && promotion != null) {
			agreeCode = 2;  // 필수 + 프로모션
		} else if(location != null && promotion != null) {
			agreeCode = 3;  // 필수 + 위치 + 프로모션
		}
		
		// DB로 보낼 UserDTO 만들기
		UserDTO user = UserDTO.builder()
				.artist(id)
				.pw(pw)
				.name(name)
				.gender(gender)
				.email(email)
				.mobile(mobile)
				.birthyear(birthyear)
				.birthday(birthday)
				.agreeCode(agreeCode)
				.snsType("naver")  // 네이버로그인으로 가입하면 naver를 저장해 두기로 함
				.build();
				
		// 회원가입처리
		int result = userMapper.insertNaverUser(user);
		
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
				
				// 로그인 기록 남기기
				int updateResult = userMapper.updateAccessLog(email);
				if(updateResult == 0) {
					userMapper.insertAccessLog(email);
				}
				
				out.println("<script>");
				out.println("alert('회원 가입되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('회원 가입에 실패했습니다.');");
				out.println("location.href='/user/login/form';");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 - 이메일/비밀번호 찾기
	@Override
	public Map<String, Object> findUser(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("findUser", userMapper.selectUserByMap(map));
		return result;
	}
	// 로그인 - 임시 비밀번호 발송
	@Override
	public Map<String, Object> sendTemporaryPassword(UserDTO user) {
		// 9자리 임시 비밀번호
		String temporaryPassword = securityUtil.generateRandomString(9);
		System.out.println("임시비번 : " + temporaryPassword);
		
		// 메일 내용
		String text = "";
		text += "비밀번호가 초기화되었습니다.<br>";
		text += "임시비밀번호 : <strong>" + temporaryPassword + "</strong><br><br>";
		text += "임시비밀번호로 로그인 후에 반드시 비밀번호를 변경해 주세요.";
		
		// 메일 전송
		javaMailUtil.sendJavaMail(user.getEmail(), "[Application] 임시비밀번호", text);
		
		// DB로 보낼 user
		user.setPw(securityUtil.sha256(temporaryPassword));  // user에 포함된 userNo와 pw를 사용
		
		// 임시 비밀번호로 DB 정보 수정하고 결과 반환
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", userMapper.updateUserPassword(user));
		return result;
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
				
				
				// 로그인 기록 남기기
				int updateResult = userMapper.updateAccessLog(email);
				if(updateResult == 0) {
					userMapper.insertAccessLog(email);
				}
				
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
	public ResponseEntity<byte[]> getImage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = ((UserDTO)session.getAttribute("loginUser")).getEmail();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		UserDTO user = userMapper.selectUserByMap(map);
		File image = new File(user.getProfileImage());
		
		ResponseEntity<byte[]> result = null;
		try {
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(image), HttpStatus.OK);
			System.out.println("result : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 회원정보 수정 - 이미지 변경
	@Override
	public void modifyImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		String email = multipartRequest.getParameter("email");
		System.out.println("이미지저장 임플");
		// 첨부된 파일 목록
		MultipartFile imageFile = multipartRequest.getFile("profileImagefile");  // <input type="file" name="file">
		System.out.println("이미지파일 정보 : " + imageFile);
		// 첨부 결과
		int attachResult;
		if(imageFile.getSize() == 0) {  // 첨부가 없는 경우 (files 리스트에 [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 이렇게 저장되어 있어서 files.size()가 1이다.
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		System.out.println("첨부결과 : " + attachResult);
		try {
			
			// 첨부가 있는지 점검
			if(imageFile != null && imageFile.isEmpty() == false) {  // 둘 다 필요함
				
				// 원래 이름
				String origin = imageFile.getOriginalFilename();
				origin = origin.substring(origin.lastIndexOf("\\") + 1);  // IE는 origin에 전체 경로가 붙어서 파일명만 사용해야 함
				
				// 저장할 이름
				String filesystem = myFileUtil.getFilename(origin);
				
				// 저장할 경로
				String ImagePath = myFileUtil.getTodayPath();
				
				// 저장할 경로 만들기
				File dir = new File(ImagePath);
				if(dir.exists() == false) {
					dir.mkdirs();
				}
				
				// 첨부할 File 객체
				File file = new File(dir, filesystem);
				
				// 첨부파일 서버에 저장(업로드 진행)
				imageFile.transferTo(file);

				// UserDTO 생성
				UserDTO user = UserDTO.builder()
						.email(email)
						.profileImage(ImagePath + filesystem)
						.build();
				
				// 첨부파일의 Content-Type 확인
				String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type(image/jpeg, image/png, image/gif)

				// DB에 AttachDTO 저장
				int result = userMapper.updateUser(user);
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				if(result > 0) {
					// 조회 조건으로 사용할 Map
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("email", email);
					// session에 올라간 정보를 수정된 내용으로 업데이트
					multipartRequest.getSession().setAttribute("loginUser", userMapper.selectUserByMap(map));
					
					out.println("<script>");
					out.println("alert('사진이 변경되었습니다.');");
					out.println("location.href='/user/mypage'");
					out.println("</script>");
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	// 회원정보수정 - 비밀번호 체크
	@Override
	public Map<String, Object> checkPw(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		// 비밀번호 일치하는지 확인
		String email = ((UserDTO)session.getAttribute("loginUser")).getEmail();
		String pw = securityUtil.sha256(request.getParameter("originPw"));
		
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("pw", pw);
		UserDTO selectUser = userMapper.selectUserByMap(map);

		Map<String, Object> result = new HashMap<>();
		result.put("result", selectUser != null);
		
		// 회원정보수정
		/*Map<String, Object> result = new HashMap<>();
		result.put("result", userMapper.updateUserPassword(user));
		*/
		return result;		
	}
	// 회원정보수정 - 비밀번호
	@Override
	public void modifyPw(HttpServletRequest request, HttpServletResponse response) {
		
		// 사용자의 아이디
		
		// 파라미터
		String email = request.getParameter("email");
		String pw = securityUtil.sha256(request.getParameter("newPw"));
		
		// DB로 보낼 UserDTO 만들기
		UserDTO user = UserDTO.builder()
				.email(email)
				.pw(pw)
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
	
	// 3개월 - 비밀번호 수정
	@Override
	public void pwHandle() {
	
		Map<String, Object> map = new HashMap<>();
		List<UserDTO> user = userMapper.selectNoticePassword();//3개월이 지난 유저들
		String txt = "";
		txt += "<a href='user/mypage/info'>비밀번호를 변경한지 3개월이 지났습니다.</a>";	
		for(int i=0; i < user.size(); i++) {
			map.put("title", "보안");
			map.put("email", user.get(i).getEmail());
			map.put("content",txt);
			alarmMapper.insertAlarm(map);
		}
		
	

		
		
	}
	
	// 휴면 - 로그인 시, 휴면 체크
	@Override
	public SleepUserDTO getSleepUserByEmail(String email) {
		return userMapper.selectSleepUserByEmail(email);
	}
	
	// 휴면 - 자동 휴면 체크 (스케줄러)
	@Transactional
	@Override
	public void sleepUserHandle() {
		// 11개월 전 로그인 체크
		List<UserDTO> user = userMapper.selectNoticeSleep();
		
		
		for(int i=0; i < user.size(); i++) {
			String email = user.get(i).getEmail();
			// 메일 내용
			String text = "";
			text += "오랫동안 접속하지 않아 1개월 뒤면 휴면 처리됩니다.<br>";
			text += "다시 돌아오셔서 아티스트 활동을 이어가주세요.<br><br>";
			text += "태그뮤직은 <strong>" + user.get(i).getArtist() + "</strong>님을 기다리고 있습니다.<br>";
			text += "태그뮤직 바로가기 > http://localhost:9090/";
			
			// 메일 전송
			javaMailUtil.sendJavaMail(email, "[TagMusic] 휴면 전환 안내", text);
		}
		
		// 휴면 전환
		int insertCount = userMapper.insertSleepUser();
		if(insertCount > 0) {
			userMapper.deleteUserForSleep();
		}
	}
	
	// 휴면 - 정상회원으로 복구
	@Transactional
	@Override
	public void restoreUser(HttpServletRequest request, HttpServletResponse response) {
		
		// 계정 복원을 원하는 사용자의 아이디
		HttpSession session = request.getSession();
		SleepUserDTO sleepUser = (SleepUserDTO)session.getAttribute("sleepUser");
		String email = sleepUser.getEmail();
		
		// 계정복구진행
		int insertCount = userMapper.insertRestoreUser(email);
		int deleteCount = 0;
		if(insertCount > 0) {
			deleteCount = userMapper.deleteSleepUser(email);
		}
		// 응답
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(insertCount > 0 && deleteCount > 0) {
				// session에 저장된 sleepUser 제거
				session.removeAttribute("sleepUser");
				
				out.println("<script>");
				out.println("alert('휴면 계정이 복구되었습니다. 휴면 계정 활성화를 위해 곧바로 로그인을 해 주세요.');");
				out.println("location.href='/';");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('휴면 계정이 복구되지 않았습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
