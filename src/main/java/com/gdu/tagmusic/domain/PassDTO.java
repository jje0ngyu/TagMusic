package com.gdu.tagmusic.domain;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PassDTO {
	private int passNo;
	private String passName;
	private int passPrice;
}
