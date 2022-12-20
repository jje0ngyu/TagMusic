package com.gdu.tagmusic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SummernoteImageDTO {
	private int boardNo;
	private String path;
	private String filesystem;
}