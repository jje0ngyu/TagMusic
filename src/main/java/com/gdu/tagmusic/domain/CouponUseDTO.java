package com.gdu.tagmusic.domain;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CouponUseDTO {
	private int couponUseNo;
	private String couponCode;
	private String email;
}
