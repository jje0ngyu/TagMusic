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
public class PaymentDTO {
	private int payNo;
	private String email;
	private int passNo;
	private Date expirationDate;
}
