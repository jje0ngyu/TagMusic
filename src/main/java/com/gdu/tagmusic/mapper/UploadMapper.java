package com.gdu.tagmusic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.tagmusic.domain.AttachDTO;
import com.gdu.tagmusic.domain.UploadDTO;

@Mapper
public interface UploadMapper {
	
	public int selectUploadListCount();
	public List<UploadDTO> selectUploadListByMap(Map<String, Object> map);
	public int insertUpload(UploadDTO upload);
	public int insertAttach(AttachDTO attach);
	public UploadDTO selectUploadByNo(int uploadNo);
	public List<AttachDTO> selectAttachList(int uploadNo);
	public int updateDownloadCnt(int attachNo);
	public AttachDTO selectAttachByNo(int attachNo);
	public int updateUpload(UploadDTO upload);
	public int deleteAttach(int attachNo);
	public int deleteUpload(int uploadNo);
	public List<AttachDTO> selectAttachListInYesterday();
	public int selectAttachCnt(int uploadNo);
}