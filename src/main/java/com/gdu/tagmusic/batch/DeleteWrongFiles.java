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

import com.gdu.tagmusic.domain.AttachDTO;
import com.gdu.tagmusic.mapper.UploadMapper;
import com.gdu.tagmusic.util.MyFileUtil;

@EnableScheduling
@Component
public class DeleteWrongFiles {

	
	// 필드 2개를 @Autowired 대신 @AllArgsConstructor로 처리
		private MyFileUtil myFileUtil;
		private UploadMapper uploadMapper;
		
		// 삭제되는지 테스트는 아래 크론식으로 확인
		// cron="0 0/1 * * * *"(1분마다 잘못 업로드 된 파일을 지움)
		
		@Scheduled(cron="0 0/1 * * * *")  // 새벽 4시마다 동작
		public void execute() {
			
			// 어제 업로드 된 파일의 경로
			String path = myFileUtil.getYesterdayPath();
			
			// 어제 업로드 된 파일 목록(DB에 기록된 파일 목록)
			List<AttachDTO> dbList = uploadMapper.selectAttachListInYesterday();
			System.out.println(dbList.toString());
			
			// 어제 업로드 된 파일(경로 + 파일명) 목록과 썸네일을 List<Path>로 생성
			List<Path> pathList = new ArrayList<Path>();
			if(dbList != null && dbList.isEmpty() == false) {
				for(AttachDTO attach : dbList) {
					pathList.add(Paths.get(path, attach.getFilesystem()));  // 원본 파일
					if(attach.getHasThumbnail() == 1) {
						pathList.add(Paths.get(path, "s_" + attach.getFilesystem()));  // 썸네일					
					}
				}
			}
			
			// System.out.println("1   " + pathList.toString());     // 어제 저장되었다고 DB에 기록된 파일들
			
			// 어제 업로드 된 파일 목록 중 DB에 기록된 파일이 아닌 목록
			File dir = new File(path);
			File[] wrongFiles = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return pathList.contains(new File(dir, name).toPath()) == false;
				}
			});
			
			/*
			File dir = new File(path);
			File[] wrongFiles = dir.listFiles( (directory, file) -> pathList.contains(new File(directory, file).toPath()) == false );
			*/
			
			// System.out.println("2   " + Arrays.toString(wrongFiles));  // 어제 저장된 파일 중 DB에 기록되어 있지 않은 파일들
			
			// 삭제
			if(wrongFiles != null) {
				for(File wrong : wrongFiles) {
					wrong.delete();
				}
			}
			
		}
		
	}