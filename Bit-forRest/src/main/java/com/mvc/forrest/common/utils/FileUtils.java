package com.mvc.forrest.common.utils;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.dao.img.ImgDAO;
import com.mvc.forrest.service.domain.Img;

@Service
public class FileUtils {
	
	public final static String temDir ="C:\\Users\\bitcamp\\git\\forRest\\Bit-forRest\\src\\main\\resources\\static\\images\\uploadFiles";
	
	@Value("${file.path}")
	private String fileRealPath;
	
	@Autowired
	private ImgDAO imgDAO;
	
    public String uploadFiles(List<MultipartFile> multipartFiles, String id, String flag) throws Exception {
    	
        
    	System.out.println("uploadFiles start");
    	
    	// 파일 업로드 경로 생성
    	List<String> list = new ArrayList<String>();
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
            list.add(fileName);
            try {
            	File file = new File(temDir , fileName);//학원 에서 쓸 때
//                File file = new File(fileRealPath+"uploadFiles/", fileName);//집에서 쓸 때
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

            } catch (IOException e) {

            } 
            
            
        }//for 문 end
       System.out.println(list);
       String mainImg = list.get(0);
       return mainImg;
    }//uploadFiles end
    
    
	public List<Img> getProductImgList(String prodNo) throws Exception{
		System.out.println("getProductImgList 실행 됨");
		System.out.println(prodNo);
		return imgDAO.getProductImgList(prodNo);
	}
	
	public List<Img> getOLdImgList(String oldNo) throws Exception{
		System.out.println("getOLdImgList 실행 됨");
		return imgDAO.getOLdImgList(oldNo);
	}
	
	public List<Img> getAnnounceImgList(String boardNo) throws Exception{
		System.out.println("getAnnounceImgList 실행 됨");
		return imgDAO.getAnnounceImgList(boardNo);
	}
    
    
}//class end