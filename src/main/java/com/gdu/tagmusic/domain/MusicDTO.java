package com.gdu.tagmusic.domain;

import java.sql.Date;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MusicDTO {
	
	private int musicNo;
	private String email;
	private String musicTitle;
	private String musicContent;
	private String musicAlbum;
	private String musicGenre;
	private Date musicUploadDate;
	private Date musicModifyDate;
	private String ip;
	
	private String imgOrigin;
	private String imgFilesystem;
	private String musiOrigin;
	private String musicFilesystem;
	private int downloadCnt;
	private String imagePath;
	private String musicPath;

}
