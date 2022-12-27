package com.gdu.tagmusic.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.gdu.tagmusic.util.PageUtil;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class UploadServiceImpl implements UploadService {
	
	private UploadMapper uploadMapper;
	private PageUtil pageUtil;
	private MyFileUtil myFileUtil;
	
	@Autowired
	public void set(UploadMapper uploadMapper, PageUtil pageUtil, MyFileUtil myFileUtil) {
		this.uploadMapper = uploadMapper;
		this.pageUtil = pageUtil;
		this.myFileUtil = myFileUtil;
	}	
	
	@Override
	public void getUploadList(Model model) {
		
		Map<String, Object> modelMap = model.asMap();  
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("recordPerPage"));
		int recordPerPage = Integer.parseInt(opt2.orElse("10"));
		
		int totalRecord = uploadMapper.selectUploadListCount();
		
		pageUtil.setPageUtil(page, recordPerPage, totalRecord);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("uploadList", uploadMapper.selectUploadListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/upload/list"));
		model.addAttribute("recordPerPage", recordPerPage);
		
	}
	
	
	
	@Transactional
	@Override
	public void save(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
		
		UploadDTO upload = UploadDTO.builder()
				.title(title)
				.content(content)
				.build();
		
		int uploadResult = uploadMapper.insertUpload(upload); 
		
		List<MultipartFile> files = multipartRequest.getFiles("files");  
		
		
		int attachResult;
		if(files.get(0).getSize() == 0) {  
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		
		
		for(MultipartFile multipartFile : files) {
			
			try {
				
				if(multipartFile != null && multipartFile.isEmpty() == false) {  
					
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1); 
					
					String filesystem = myFileUtil.getFilename(origin);
					
					String path = myFileUtil.getTodayPath();
					
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					File file = new File(dir, filesystem);
					
					multipartFile.transferTo(file);

					AttachDTO attach = AttachDTO.builder()
							.path(path)
							.origin(origin)
							.filesystem(filesystem)
							.hasThumbnail(0)
							.uploadNo(upload.getUploadNo())
							.build();
					
					String contentType = Files.probeContentType(file.toPath()); 
					if(contentType != null && contentType.startsWith("image")) {
					
						Thumbnails.of(file)
							.size(50, 50)
							.toFile(new File(dir, "s_" + filesystem));  
						
						attach.setHasThumbnail(1);
					
					}
				
					attachResult += uploadMapper.insertAttach(attach);
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}  
		
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
		model.addAttribute("attachCnt", uploadMapper.selectAttachCnt(uploadNo));
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
		
		
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		File file = new File(attach.getPath(), attach.getFilesystem());
		
		
		Resource resource = new FileSystemResource(file);
		
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		
		uploadMapper.updateDownloadCnt(attachNo);
		
		
		String origin = attach.getOrigin();
		try {
			
			
			if(userAgent.contains("Trident")) {
				origin = URLEncoder.encode(origin, "UTF-8").replaceAll("\\+", " ");
			}
			
			else if(userAgent.contains("Edg")) {
				origin = URLEncoder.encode(origin, "UTF-8");
			}
			
			else {
				origin = new String(origin.getBytes("UTF-8"), "ISO-8859-1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + origin);
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	@Override
	public ResponseEntity<Resource> downloadAll(String userAgent, int uploadNo) {
		
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		
		FileOutputStream fout = null;
		ZipOutputStream zout = null;   
		FileInputStream fin = null;
		
		String tempPath = myFileUtil.getTempPath();
		
		File tempDir = new File(tempPath);
		if(tempDir.exists() == false) {
			tempDir.mkdirs();
		}
		
		String tempName =  System.currentTimeMillis() + ".zip";
		
		try {
			
			fout = new FileOutputStream(new File(tempDir, tempName));
			zout = new ZipOutputStream(fout);
			
			if(attachList != null && attachList.isEmpty() == false) {

				for(AttachDTO attach : attachList) {
					
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

					uploadMapper.updateDownloadCnt(attach.getAttachNo());
					
				}
				
				zout.close();

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file = new File(tempDir, tempName);
		Resource resource = new FileSystemResource(file);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + tempName);  
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	@Transactional
	@Override
	public void modifyUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		
		int uploadNo = Integer.parseInt(multipartRequest.getParameter("uploadNo"));
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
	
		UploadDTO upload = UploadDTO.builder()
				.uploadNo(uploadNo)
				.title(title)
				.content(content)
				.build();
		
		int uploadResult = uploadMapper.updateUpload(upload);
		
		List<MultipartFile> files = multipartRequest.getFiles("files"); 

		int attachResult;
		if(files.get(0).getSize() == 0) { 
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		
		for(MultipartFile multipartFile : files) {
			
			try {
				
				if(multipartFile != null && multipartFile.isEmpty() == false) { 
					
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1); 
					
					String filesystem = myFileUtil.getFilename(origin);
					
					String path = myFileUtil.getTodayPath();
				
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					File file = new File(dir, filesystem);
					
					multipartFile.transferTo(file);

					AttachDTO attach = AttachDTO.builder()
							.path(path)
							.origin(origin)
							.filesystem(filesystem)
							.hasThumbnail(0)
							.uploadNo(upload.getUploadNo())
							.build();
					
					String contentType = Files.probeContentType(file.toPath()); 

					if(contentType != null && contentType.startsWith("image")) {
					
						Thumbnails.of(file)
							.size(50, 50)
							.toFile(new File(dir, "s_" + filesystem));
						
						attach.setHasThumbnail(1);
					
					}
					
					attachResult += uploadMapper.insertAttach(attach);
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}  
		
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
		
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		
		int result = uploadMapper.deleteAttach(attachNo);
		
		if(result > 0) {
			
			File file = new File(attach.getPath(), attach.getFilesystem());
			
			if(file.exists()) {
				file.delete();
			}
			
			if(attach.getHasThumbnail() == 1) {
				
				File thumbnail = new File(attach.getPath(), "s_" + attach.getFilesystem());
				if(thumbnail.exists()) {
					thumbnail.delete();
				}
				
			}

		}
		
	}
	
	@Override
	public void removeUpload(HttpServletRequest multipartRequest, HttpServletResponse response) {
	
		int uploadNo = Integer.parseInt(multipartRequest.getParameter("uploadNo"));
		
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		
		int result = uploadMapper.deleteUpload(uploadNo);
		
		if(result > 0) {
			if(attachList != null && attachList.isEmpty() == false) {
				
				for(AttachDTO attach : attachList) {
					
					File file = new File(attach.getPath(), attach.getFilesystem());
					
					if(file.exists()) {
						file.delete();
					}
					
					if(attach.getHasThumbnail() == 1) {
						
						File thumbnail = new File(attach.getPath(), "s_" + attach.getFilesystem());
						if(thumbnail.exists()) {
							thumbnail.delete();
						}
					}
				}
			}
		}
		
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
	
	@Override
	public int increseHit(int uploadNo) {
		return uploadMapper.updateHit(uploadNo);		
	}
}