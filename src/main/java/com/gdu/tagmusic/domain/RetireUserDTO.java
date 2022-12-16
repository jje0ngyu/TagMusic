package com.gdu.tagmusic.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RetireUserDTO {

		private int userNo;
		private String email;
		private String artist;
		private Date retireDate;
	
}
