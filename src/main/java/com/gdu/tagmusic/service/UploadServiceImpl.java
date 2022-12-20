package com.gdu.tagmusic.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.AttachDTO;
import com.gdu.tagmusic.domain.UploadDTO;
import com.gdu.tagmusic.mapper.UploadMapper;
import com.gdu.tagmusic.util.MyFileUtil;

import net.coobird.thumbnailator.Thumbnails;



@Service
public class UploadServiceImpl implements UploadService {
	
	
	@Autowired
	private UploadMapper uploadMapper;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	@Override
	public List<UploadDTO> getUploadList() {
		return uploadMapper.selectUploadList();
	}
	
	@Transactional
	@Override
	public void save(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		
		/*  UPLOAD 테이블에 저장하기 */
		
		// 파라미터
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
		
		// DB로 보낼 UploadDTO
		UploadDTO upload = UploadDTO.builder()
				.title(title)
				.content(content)
				.build();
		
		// DB에 UploadDTO 저장
		int uploadResult = uploadMapper.insertUpload(upload);  // <selectKey>에 의해서 인수 upload에 uploadNo값이 저장된다.
		
		/* ATTACH 테이블에 저장하기 */
		
		// 첨부된 파일 목록
		List<MultipartFile> files = multipartRequest.getFiles("files");  // <input type="file" name="files">
		
		// 첨부 결과
		int attachResult;
		if(files.get(0).getSize() == 0) {  // 첨부가 없는 경우 (files 리스트에 [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 이렇게 저장되어 있어서 files.size()가 1이다.
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		
		// 첨부된 파일 목록 순회(하나씩 저장)
		for(MultipartFile multipartFile : files) {
			
			try {
				
				// 첨부가 있는지 점검
				if(multipartFile != null && multipartFile.isEmpty() == false) {  // 둘 다 필요함
					
					// 원래 이름
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1);  // IE는 origin에 전체 경로가 붙어서 파일명만 사용해야 함
					
					// 저장할 이름
					String filesystem = myFileUtil.getFilename(origin);
					
					// 저장할 경로
					String path = myFileUtil.getTodayPath();
					
					// 저장할 경로 만들기
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					// 첨부할 File 객체
					File file = new File(dir, filesystem);
					
					// 첨부파일 서버에 저장(업로드 진행)
					multipartFile.transferTo(file);

					// AttachDTO 생성
					AttachDTO attach = AttachDTO.builder()
							.path(path)
							.origin(origin)
							.filesystem(filesystem)
							.hasThumbnail(0)
							.uploadNo(upload.getUploadNo())
							.build();
					
					// 첨부파일의 Content-Type 확인
					String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type(image/jpeg, image/png, image/gif)

					// 첨부파일이 이미지이면 썸네일을 만듬
					if(contentType != null && contentType.startsWith("image")) {
					
						// 썸네일을 서버에 저장
						Thumbnails.of(file)
							.size(50, 50)
							.toFile(new File(dir, "s_" + filesystem));  // 썸네일의 이름은 s_로 시작함
						
						// 썸네일이 있는 첨부로 상태 변경
						attach.setHasThumbnail(1);
					
					}
					
					// DB에 AttachDTO 저장
					attachResult += uploadMapper.insertAttach(attach);
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}  // for
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(uploadResult > 0 && attachResult == files.size()) {
				out.println("<script>");
				out.println("alert('업로드 되었습니다.');");
				out.println("location.href='" + multipartRequest.getContextPath() + "/upload/list'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('업로드 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void getUploadByNo(int uploadNo, Model model) {
		model.addAttribute("upload", uploadMapper.selectUploadByNo(uploadNo));
		model.addAttribute("attachList", uploadMapper.selectAttachList(uploadNo));
	}
	
	@Override
	public ResponseEntity<byte[]> display(int attachNo) {
		
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		File file = new File(attach.getPath(), attach.getFilesystem());

		ResponseEntity<byte[]> result = null;

		try {

			if(attach.getHasThumbnail() == 1) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				File thumbnail = new File(attach.getPath(), "s_" + attach.getFilesystem());
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), null, HttpStatus.OK);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	@Override
	public ResponseEntity<Resource> download(String userAgent, int attachNo) {
		
		// 다운로드 할 첨부 파일의 정보(경로, 이름)
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		File file = new File(attach.getPath(), attach.getFilesystem());
		
		// 반환할 Resource
		Resource resource = new FileSystemResource(file);
		
		// Resource가 없으면 종료 (다운로드할 파일이 없음)
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		// 다운로드 횟수 증가
		uploadMapper.updateDownloadCnt(attachNo);
		
		// 다운로드 되는 파일명(브라우저 마다 다르게 세팅)
		String origin = attach.getOrigin();
		try {
			
			// IE (userAgent에 "Trident"가 포함되어 있음)
			if(userAgent.contains("Trident")) {
				origin = URLEncoder.encode(origin, "UTF-8").replaceAll("\\+", " ");
			}
			// Edge (userAgent에 "Edg"가 포함되어 있음)
			else if(userAgent.contains("Edg")) {
				origin = URLEncoder.encode(origin, "UTF-8");
			}
			// 나머지
			else {
				origin = new String(origin.getBytes("UTF-8"), "ISO-8859-1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 다운로드 헤더 만들기
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + origin);
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	@Override
	public ResponseEntity<Resource> downloadAll(String userAgent, int uploadNo) {
		
		// /storage/temp 디렉터리에 임시 zip 파일을 만든 뒤 이를 다운로드 받을 수 있음
		// com.gdu.app14.batch.DeleteTempFiles에 의해서 /storage/temp 디렉터리의 임시 zip 파일은 주기적으로 삭제됨
		
		// 다운로드 할 첨부 파일들의 정보(경로, 이름)
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		
		// zip 파일을 만들기 위한 스트림
		FileOutputStream fout = null;
		ZipOutputStream zout = null;   // zip 파일 생성 스트림
		FileInputStream fin = null;
		
		// /storage/temp 디렉터리에 zip 파일 생성
		String tempPath = myFileUtil.getTempPath();
		
		File tempDir = new File(tempPath);
		if(tempDir.exists() == false) {
			tempDir.mkdirs();
		}
		
		// zip 파일명은 타임스탬프 값으로 생성
		String tempName =  System.currentTimeMillis() + ".zip";
		
		try {
			
			fout = new FileOutputStream(new File(tempDir, tempName));
			zout = new ZipOutputStream(fout);
			
			// 첨부가 있는지 확인
			if(attachList != null && attachList.isEmpty() == false) {

				// 첨부 파일 하나씩 순회
				for(AttachDTO attach : attachList) {
					
					// zip 파일에 첨부 파일 추가
					ZipEntry zipEntry = new ZipEntry(attach.getOrigin());
					zout.putNextEntry(zipEntry);
					
					fin = new FileInputStream(new File(attach.getPath(), attach.getFilesystem()));
					byte[] buffer = new byte[1024];
					int length;
					while((length = fin.read(buffer)) != -1){
						zout.write(buffer, 0, length);
					}
					zout.closeEntry();
					fin.close();

					// 각 첨부 파일 모두 다운로드 횟수 증가
					uploadMapper.updateDownloadCnt(attach.getAttachNo());
					
				}
				
				zout.close();

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		// 반환할 Resource
		File file = new File(tempDir, tempName);
		Resource resource = new FileSystemResource(file);
		
		// Resource가 없으면 종료 (다운로드할 파일이 없음)
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		// 다운로드 헤더 만들기
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + tempName);  // 다운로드할 zip파일명은 타임스탬프로 만든 이름을 그대로 사용
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	@Transactional
	@Override
	public void modifyUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		
		/*  UPLOAD 테이블 수정하기 */
		
		// 파라미터
		int uploadNo = Integer.parseInt(multipartRequest.getParameter("uploadNo"));
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
		
		// DB로 보낼 UploadDTO
		UploadDTO upload = UploadDTO.builder()
				.uploadNo(uploadNo)
				.title(title)
				.content(content)
				.build();
		
		// DB 수정
		int uploadResult = uploadMapper.updateUpload(upload);
		
		/* ATTACH 테이블에 저장하기 */
		
		// 추가하려는 첨부 파일 목록
		List<MultipartFile> files = multipartRequest.getFiles("files");  // <input type="file" name="files">

		// 첨부 결과
		int attachResult;
		if(files.get(0).getSize() == 0) {  // 첨부가 없는 경우 (files 리스트에 [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]] 이렇게 저장되어 있어서 files.size()가 1이다.
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		
		// 첨부된 파일 목록 순회(하나씩 저장)
		for(MultipartFile multipartFile : files) {
			
			try {
				
				// 첨부가 있는지 점검
				if(multipartFile != null && multipartFile.isEmpty() == false) {  // 둘 다 필요함
					
					// 원래 이름
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1);  // IE는 origin에 전체 경로가 붙어서 파일명만 사용해야 함
					
					// 저장할 이름
					String filesystem = myFileUtil.getFilename(origin);
					
					// 저장할 경로
					String path = myFileUtil.getTodayPath();
					
					// 저장할 경로 만들기
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					// 첨부할 File 객체
					File file = new File(dir, filesystem);
					
					// 첨부파일 서버에 저장(업로드 진행)
					multipartFile.transferTo(file);

					// AttachDTO 생성
					AttachDTO attach = AttachDTO.builder()
							.path(path)
							.origin(origin)
							.filesystem(filesystem)
							.hasThumbnail(0)
							.uploadNo(upload.getUploadNo())
							.build();
					
					// 첨부파일의 Content-Type 확인
					String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type(image/jpeg, image/png, image/gif)

					// 첨부파일이 이미지이면 썸네일을 만듬
					if(contentType != null && contentType.startsWith("image")) {
					
						// 썸네일을 서버에 저장
						Thumbnails.of(file)
							.size(50, 50)
							.toFile(new File(dir, "s_" + filesystem));  // 썸네일의 이름은 s_로 시작함
						
						// 썸네일이 있는 첨부로 상태 변경
						attach.setHasThumbnail(1);
					
					}
					
					// DB에 AttachDTO 저장
					attachResult += uploadMapper.insertAttach(attach);
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}  // for
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(uploadResult > 0 && attachResult == files.size()) {
				out.println("<script>");
				out.println("alert('수정 되었습니다.');");
				out.println("location.href='" + multipartRequest.getContextPath() + "/upload/detail?uploadNo=" + uploadNo + "'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('수정 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeAttachByAttachNo(int attachNo) {
		
		// 삭제할 Attach 정보 가져오기
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		
		// DB에서 Attach 정보 삭제
		int result = uploadMapper.deleteAttach(attachNo);
		
		// 첨부 파일 삭제
		if(result > 0) {
			
			// 첨부 파일을 File 객체로 만듬
			File file = new File(attach.getPath(), attach.getFilesystem());
			
			// 원본 이미지 삭제
			if(file.exists()) {
				file.delete();
			}
			
			// 첨부파일이 이미지이면 썸네일을 삭제
			if(attach.getHasThumbnail() == 1) {
				
				// 썸네일 이미지 삭제
				File thumbnail = new File(attach.getPath(), "s_" + attach.getFilesystem());
				if(thumbnail.exists()) {
					thumbnail.delete();
				}
				
			}

		}
		
	}
	
	@Override
	public void removeUpload(HttpServletRequest multipartRequest, HttpServletResponse response) {
		
		// 파라미터
		int uploadNo = Integer.parseInt(multipartRequest.getParameter("uploadNo"));
		
		// 삭제할 Upload에 첨부된 첨부파일 목록 가져오기
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		
		// DB에서 Upload 정보 삭제
		int result = uploadMapper.deleteUpload(uploadNo);
		
		// 첨부 파일 삭제
		if(result > 0) {
			if(attachList != null && attachList.isEmpty() == false) {
				// 순회하면서 하나씩 삭제
				for(AttachDTO attach : attachList) {
					// 첨부 파일을 File 객체로 만듬
					File file = new File(attach.getPath(), attach.getFilesystem());
					// 원본 이미지 삭제
					if(file.exists()) {
						file.delete();
					}
					// 첨부파일이 이미지이면 썸네일을 삭제
					if(attach.getHasThumbnail() == 1) {
						// 썸네일 이미지 삭제
						File thumbnail = new File(attach.getPath(), "s_" + attach.getFilesystem());
						if(thumbnail.exists()) {
							thumbnail.delete();
						}
					}
				}
			}
		}
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				out.println("<script>");
				out.println("alert('삭제 되었습니다.');");
				out.println("location.href='" + multipartRequest.getContextPath() + "/upload/list'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('삭제 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}