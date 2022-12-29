package com.gdu.tagmusic.domain;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AlarmDTO {
	private int alarmNo;
	private String email;
	private int alarmStatus;
	private String alarmContent; 
}
