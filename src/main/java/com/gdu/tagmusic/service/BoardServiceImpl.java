package com.gdu.tagmusic.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.BoardDTO;
import com.gdu.tagmusic.domain.SummernoteImageDTO;
import com.gdu.tagmusic.mapper.AlarmMapper;
import com.gdu.tagmusic.mapper.BoardMapper;
import com.gdu.tagmusic.util.MyFileUtil;
import com.gdu.tagmusic.util.PageUtil;



@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	private PageUtil pageUtil;
	private MyFileUtil myFileUtil;
	private AlarmMapper alarmMapper;
	
	@Autowired
	public void set(BoardMapper boardMapper, PageUtil pageUtil, MyFileUtil myFileUtil, AlarmMapper alarmMapper) {
		this.boardMapper = boardMapper;
		this.pageUtil = pageUtil;
		this.myFileUtil = myFileUtil;
		this.alarmMapper = alarmMapper;
	}	
	
	@Override
	public void getBoardList(Model model) {
		
		Map<String, Object> modelMap = model.asMap();  
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("recordPerPage"));
		int recordPerPage = Integer.parseInt(opt2.orElse("10"));
		
		
		int totalRecord = boardMapper.selectBoardListCount();
		
		
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("end", pageUtil.getEnd());
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("boardList", boardMapper.selectBoardListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/board/list"));
		model.addAttribute("recordPerPage", recordPerPage);
	}
	
	@Override
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest multipartRequest) {
		
		
		MultipartFile multipartFile = multipartRequest.getFile("file");
			
		
		String path = "C:" + File.separator + "summernoteImage";
				
		
		String filesystem = myFileUtil.getFilename(multipartFile.getOriginalFilename());
		
		
		File dir = new File(path);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
		
		File file = new File(path, filesystem);  
		
		
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("src", multipartRequest.getContextPath() + "/load/image/" + filesystem); 
		map.put("filesystem", filesystem); 
		return map;
		
	}
	
	@Transactional
	@Override
	public void saveBoard(HttpServletRequest request, HttpServletResponse response) {
		
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
	String ip = opt.orElse(request.getRemoteAddr());
	
	
	BoardDTO board = BoardDTO.builder()
			.boardTitle(title)
			.boardContent(content)
			.ip(ip)
			.build();
	
	
	int result = boardMapper.insertBoard(board);
	
	
	try {
		
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		if(result > 0) {
			Map<String, Object> map = new HashMap<>();
			map.put("title", "공지");
			map.put("email", "gongji@web.com");
			
			map.put("content", "<a href='/board/list'><b>공지사항</b>을 확인해주세요</a>");
			int inserAlarm = alarmMapper.insertAlarm(map);
			if(inserAlarm == 1) {
				System.out.println("성공");
			}
			
			String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");
			
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
	
	@Override
	public int increseBoardHit(int boardNo) {
		return boardMapper.updateHit(boardNo);		
	}
	
	@Override
	public BoardDTO getBoardByNo(int boardNo) {
	
		BoardDTO board = boardMapper.selectBoardByNo(boardNo);
		
		
		List<SummernoteImageDTO> summernoteImageList = boardMapper.selectSummernoteImageListInBoard(boardNo);
		
		
		if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
			for(SummernoteImageDTO summernoteImage : summernoteImageList) {
				if(board.getBoardContent().contains(summernoteImage.getFilesystem()) == false) {
					File file = new File("C:" + File.separator + "summernoteImage", summernoteImage.getFilesystem());
					if(file.exists()) {
						file.delete();  
					}
					boardMapper.deleteSummernoteImage(summernoteImage.getFilesystem());
				}
			}
		}
		
	
		return board;
		
		}
	
	@Transactional
	@Override
	public void modifyBoard(HttpServletRequest request, HttpServletResponse response) {
		
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		
		BoardDTO board = BoardDTO.builder()
				.boardTitle(title)
				.boardContent(content)
				.boardNo(boardNo)
				.build();
		
		
		int result = boardMapper.updateBoard(board);
		
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				
				
				String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");
				
				
				if(summernoteImageNames != null) {
					for(String filesystem : summernoteImageNames) {
						SummernoteImageDTO summernoteImage = SummernoteImageDTO.builder()
								.boardNo(board.getBoardNo())
								.filesystem(filesystem)
								.build();
						boardMapper.insertSummernoteImage(summernoteImage);
					}
				}
				
				out.println("alert('수정 성공');");
				out.println("location.href='" + request.getContextPath() + "/board/detail?boardNo=" + boardNo + "';");
			} else {
				out.println("alert('수정 실패');");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		 
		List<SummernoteImageDTO> summernoteImageList = boardMapper.selectSummernoteImageListInBoard(boardNo);
		
		
		int result = boardMapper.deleteBoard(boardNo);  
		
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				
				
				if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
					for(SummernoteImageDTO summernoteImage : summernoteImageList) {
						File file = new File("C:" + File.separator + "summernoteImage", summernoteImage.getFilesystem());
						if(file.exists()) {
							file.delete();
						}
					}
				}
				
				out.println("alert('삭제 성공');");
				out.println("location.href='" + request.getContextPath() + "/board/list';");
			} else {
				out.println("alert('삭제 실패');");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
