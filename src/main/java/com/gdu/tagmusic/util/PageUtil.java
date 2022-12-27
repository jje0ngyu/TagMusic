package com.gdu.tagmusic.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class PageUtil {

	private int page;                // 현재 페이지(파라미터로 받아온다)
	private int totalRecord;         // 전체 레코드 개수(DB에서 구해온다)
	private int recordPerPage;       // 페이지에 표시할 레코드 개수(파라미터로 받아온다)
	private int begin;               // 가져올 목록의 시작 번호(계산한다)
	private int end;                 // 가져올 목록의 끝 번호(계산한다)
	
	private int totalPage;           // 전체 페이지 개수(계산한다)
	private int pagePerBlock = 5;    // 블록에 표시할 페이지 개수(임의로 정한다)
	private int beginPage;           // 블록의 시작 페이지 번호(계산한다)
	private int endPage;             // 블록의 끝 페이지 번호(계산한다)
	
	public void setPageUtil(int page, int recordPerPage, int totalRecord) {
		
		// page, recordPerPage, totalRecord 필드 저장
		this.page = page;						
		this.recordPerPage = recordPerPage;
		this.totalRecord = totalRecord;
		
		// begin, end 계산
		begin = (page - 1) * recordPerPage + 1;
		end = begin + recordPerPage - 1;
		if(end > totalRecord) {
			end = totalRecord;
		}
		
		// totalPage 계산
		totalPage = totalRecord / recordPerPage;
		if(totalRecord % recordPerPage != 0) {
			totalPage++;
		}
		
		// beginPage, endPage 계산
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock - 1;
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
	}
	
	public String getPaging(String path) {
		
		StringBuilder sb = new StringBuilder();
		
		if(path.contains("?")) {
			path += "&";
		} else {
			path += "?";
		}
		
		// 이전블록 : 1block이 아니면 이전블록이 있다
		if(beginPage != 1) {
			sb.append("<a class=\"page_left\" href=\"" + path + "page=" + (beginPage - 1) + "\">이전 ◀</a>");
		}
		
		// 페이지번호 : 현재 페이지는 링크가 없다
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				sb.append("<span class=\"paging\">" + p + "</span>");
			} else {
				sb.append("<a class=\"paging\" href=\"" + path + "page=" + p + "\">" + p + "</a>");
			}
		}
		
		// 다음블록 : 마지막 블록이 아니면 다음블록이 있다
		if(endPage != totalPage) {
			sb.append("<a class=\"page_right\" href=\"" + path + "page=" + (endPage + 1) + "\">다음 ▶</a>");
		}
		
		return sb.toString();
		
	}
	
	public String getPagingForAjax(String path) {
		
		StringBuilder sb = new StringBuilder();
		
		if(path.contains("?")) {
			path += "&";
		} else {
			path += "?";
		}
		
		// 이전블록 : 1block이 아니면 이전블록이 있다
		if(beginPage != 1) {
			sb.append("<a class=\"page_right\" onclick=\"fn_page_list(" + (beginPage - 1) + ");\">이전 ◀</a>");
		}
		
		
		// 페이지번호 : 현재 페이지는 링크가 없다
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				sb.append("<span class=\"paging\">" + p + "</span>");
			} else {
				sb.append("<a class=\"paging\" onclick=\"fn_page_list("+ p +");\">" + p + "</a>");
			}
		}
		
		
		// 다음블록 : 마지막 블록이 아니면 다음블록이 있다
		if(endPage != totalPage) {
			sb.append("<a class=\"page_right\" onclick=\"fn_page_list(" + (endPage + 1) + ");\">다음 ▶</a>");
		}
		
		return sb.toString();
		
	}
	
}
