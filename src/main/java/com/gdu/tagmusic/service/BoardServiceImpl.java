package com.gdu.tagmusic.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.tagmusic.domain.BoardDTO;
import com.gdu.tagmusic.domain.SummernoteImageDTO;
import com.gdu.tagmusic.mapper.BoardMapper;
import com.gdu.tagmusic.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	
	private BoardMapper boardMapper;
	private PageUtil pageUtil;
	
	
	@Override
	public void getBoardList(Model model) {
		
		// Model에 저장된 request 꺼내기
		Map<String, Object> modelMap = model.asMap();  // model을 map으로 변신
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		// page 파라미터
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("recordPerPage"));
		int recordPerPage = Integer.parseInt(opt2.orElse("10"));
		
		// 전체 블로그 개수
		int totalRecord = boardMapper.selectBoardListCount();
		
		// 페이징 처리에 필요한 변수 계산
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		// 조회 조건으로 사용할 Map 만들기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		// 뷰로 전달할 데이터를 model에 저장하기 
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("boardList", boardMapper.selectBoardListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging("/board/list"));
		model.addAttribute("recordPerPage", recordPerPage);
	}

@Override
	public void saveBoard(HttpServletRequest request, HttpServletResponse response) {
		
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
	String ip = opt.orElse(request.getRemoteAddr());
	
	// DB로 보낼 BlogDTO
	BoardDTO board = BoardDTO.builder()
			.boardTitle(title)
			.boardContent(content)
			.ip(ip)
			.build();
	
	
	int result = boardMapper.insertBoard(board);
	
	// 응답
	try {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		if(result > 0) {
			
			// 파라미터 summernoteImageNames
			String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");
			
			// DB에 SummernoteImage 저장
			if(summernoteImageNames !=  null) {
				for(String filesystem : summernoteImageNames) {
					SummernoteImageDTO summernoteImage = SummernoteImageDTO.builder()
							.boardNo(board.getBoardNo())
							.filesystem(filesystem)
							.build();
					boardMapper.insertSummernoteImage(summernoteImage);
				}
			}
			
			out.println("alert('삽입 성공');");
			out.println("location.href='" + request.getContextPath() + "/board/list';");
		} else {
			out.println("alert('삽입 실패');");
			out.println("history.back();");
		}
		out.println("</script>");
		out.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
		}
		
			}
