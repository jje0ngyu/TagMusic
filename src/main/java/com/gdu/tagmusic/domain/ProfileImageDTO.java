package com.gdu.tagmusic.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileImageDTO {

	private int profileImageNo;
	private String email;
	private String profileImagePath;
	private String profileImageOrigin;
	private String profileImageFilesystem;
	
}
