package com.gdu.tagmusic.domain;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MusicLikeDTO {
	
	private int likeNo;
	private String email;
	private int musicNo;
	
	

}
