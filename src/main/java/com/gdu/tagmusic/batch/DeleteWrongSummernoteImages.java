package com.gdu.tagmusic.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.tagmusic.domain.SummernoteImageDTO;
import com.gdu.tagmusic.mapper.BoardMapper;
import com.gdu.tagmusic.util.MyFileUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableScheduling
@Component
public class DeleteWrongSummernoteImages {

	private BoardMapper boardMapper;
	private MyFileUtil myFileUtil;
	
	@Scheduled(cron="0 0 4 * * *")  
	
	public void execute() {

		
		String path = myFileUtil.getSummernotePath();
		
		
		List<SummernoteImageDTO> summernoteImageList = boardMapper.selectAllSummernoteImageList();
		
		
		List<Path> pathList = new ArrayList<Path>();
		if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
			for(SummernoteImageDTO summernoteImage : summernoteImageList) {
				pathList.add(Paths.get(path, summernoteImage.getFilesystem()));
			}
		}
		
		
		File dir = new File(path);
		File[] wrongSummernoteImages = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !pathList.contains(new File(dir, name).toPath());
			}
		});
	
		
		if(wrongSummernoteImages != null) {
			for(File wrongSummernoteImage : wrongSummernoteImages) {
				wrongSummernoteImage.delete();
			}
		}
		
	}
	
}