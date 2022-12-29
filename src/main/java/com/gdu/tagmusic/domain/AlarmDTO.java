package com.gdu.tagmusic.domain;

import java.util.Date;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AlarmDTO {
	private int alarmNo;  //PK
	private String email; //받을 사람의 이메일
	private int alarmStatus; //알람을 확인했는지 0이면 확인 1이면 확인안함
	private String alarmContent; //알람내용
	private Date alarmArrivalTime; //알람 받은 시간
}
