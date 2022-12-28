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
public class CouponDTO {
	private int couponNo;
	private String email;
	private String couponCode;
	private int couponStatus;
	private Date couponDate; 
}
