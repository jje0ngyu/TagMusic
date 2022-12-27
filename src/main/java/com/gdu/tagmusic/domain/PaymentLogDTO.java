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
public class PaymentLogDTO {
	private String payLogNo;
	private String payLogEmail;
	private Date payLogDate;
	private String payLogPg;
	private String payLogName;
	private int payLogPrice;
}
