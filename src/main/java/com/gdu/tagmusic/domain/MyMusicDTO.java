package com.gdu.tagmusic.domain;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyMusicDTO {
	
	private int myMusicNo;
	private int listNo;
	private int musicNo;
	private MusicDTO musicDTO;

}
