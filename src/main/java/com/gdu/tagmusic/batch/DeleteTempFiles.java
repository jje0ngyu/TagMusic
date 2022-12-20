package com.gdu.tagmusic.batch;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.tagmusic.util.MyFileUtil;

@EnableScheduling
@Component
public class DeleteTempFiles {

	
	@Autowired
	private MyFileUtil myFileUtil;
	
	//@Scheduled(cron="0 0 3 * * *")  // 새벽 3시마다 동작
	@Scheduled(cron="0 0 4 * * *")  // 1분마다 동작
	public void execute() {
		
		String tempPath = myFileUtil.getTempPath();  // 루트/storage
		File tempDir = new File(tempPath);
		
		if(tempDir.exists()) {
			File[] tempFiles = tempDir.listFiles();
			for(File temp : tempFiles) {
				temp.delete();
			}
		}
		
	}
	
}