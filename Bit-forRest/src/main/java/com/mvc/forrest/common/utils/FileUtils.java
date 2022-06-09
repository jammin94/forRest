package com.mvc.forrest.common.utils;



import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.dao.img.ImgDAO;
import com.mvc.forrest.service.domain.Img;


public class FileUtils {
	
	public final static String temDir ="C:\\Users\\bitcamp\\git\\forRest\\Bit-forRest\\src\\main\\resources\\static\\images\\uploads";
	
	@Autowired
	private ImgDAO imgDAO;
	
    public void uploadFiles(List<MultipartFile> multipartFiles, int id, String flag, String 경로) throws Exception {
        
    	
    	// 파일 업로드 경로 생성
        String savePath = Paths.get(temDir, "files").toString();

        for (MultipartFile multipartFile : multipartFiles) {

            String origFilename = multipartFile.getOriginalFilename();
            if (origFilename == null || "".equals(origFilename)) continue;
            String filename = FileNameUtils.fileNameConvert(origFilename);
            String filePath = Paths.get(savePath, filename).toString();
            
            Img img = new Img();
            img.setContentsFlag(flag);
            img.setContentsNo(id);
            img.setFilename(filename);
            
            imgDAO.addImg(img);
            
            try {
                File file = new File(filePath);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

            } catch (IOException e) {

            } 
        }//for 문 end
        
    }//uploadFiles end
}//class end