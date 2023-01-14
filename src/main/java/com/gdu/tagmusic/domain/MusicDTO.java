package com.gdu.tagmusic.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
	private String musicOrigin;
	private String musicFilesystem;
	private int downloadCnt;
	private String imgPath;
	private String musicPath;
	private int hasThumbNail;
	private ActiveLogDTO activeLogDTO;
	private MusicLikeDTO musicLikeDTO;
	private UserDTO userDTO;

}
