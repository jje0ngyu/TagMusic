package com.gdu.tagmusic.domain;

import java.util.Date;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class MusicCommentDTO {

	private int commentNo;
	private int musicNo;
	private String email;
	private String commentContent;
	private Date createDate;
	
	
}
