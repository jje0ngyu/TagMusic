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

public class ActiveLogDTO {

	private int activeNo;
	private String email;
	private int musicNo;
	private Date lastListenDate;
	private int listenCount;

	
}
