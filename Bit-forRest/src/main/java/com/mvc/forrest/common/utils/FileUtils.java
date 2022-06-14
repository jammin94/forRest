package com.mvc.forrest.common.utils;



import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.dao.img.ImgDAO;
import com.mvc.forrest.service.domain.Img;

@Service
public class FileUtils {
	
	public final static String temDir ="C:\\Users\\bitcamp\\git\\forRest\\Bit-forRest\\src\\main\\resources\\static\\images\\uploadFiles";
	
	@Autowired
	private ImgDAO imgDAO;
	
    public void uploadFiles(List<MultipartFile> multipartFiles, String id, String flag) throws Exception {
        
    	System.out.println("uploadFiles start");
    	
    	// 파일 업로드 경로 생성
      

        for (MultipartFile multipartFile : multipartFiles) {
        	
        	System.out.println(multipartFile.getOriginalFilename());
            String origFilename = multipartFile.getOriginalFilename();
            if (origFilename == null || "".equals(origFilename)) continue;
            String fileName = FileNameUtils.fileNameConvert(origFilename);
            
            Img img = new Img();
            img.setContentsFlag(flag);
            img.setContentsNo(id);
            img.setFileName(fileName);
            
            imgDAO.addImg(img);
            
            try {
                File file = new File(temDir, fileName);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

            } catch (IOException e) {

            } 
        }//for 문 end
        
    }//uploadFiles end
}//class end