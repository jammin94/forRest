package com.mvc.forrest.common.utils;



import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
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
    	
    	
    	Image image;
        int imageWidth;
        int imageHeight;
        double ratio;
        int w;
        int h;
        
        int newWidth = 600;                                  // 변경 할 넓이
        int newHeight = 600;                                 // 변경 할 높이
        String mainPosition = "X";                             // W:넓이중심, H:높이중심, X:설정한 수치로(비율무시)
        
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
//            	File file = new File(temDir , fileName);//학원 에서 쓸 때
                File file = new File(fileRealPath+"uploadFiles/", fileName);//집에서 쓸 때
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

            } catch (IOException e) {

            } 
            
            try{
                // 원본 이미지 가져오기
                image = ImageIO.read( new File(fileRealPath+"uploadFiles/", fileName));
     
                // 원본 이미지 사이즈 가져오기
                imageWidth = image.getWidth(null);
                imageHeight = image.getHeight(null);
     
                if(mainPosition.equals("W")){    // 넓이기준
     
                    ratio = (double)newWidth/(double)imageWidth;
                    w = (int)(imageWidth * ratio);
                    h = (int)(imageHeight * ratio);
     
                }else if(mainPosition.equals("H")){ // 높이기준
     
                    ratio = (double)newHeight/(double)imageHeight;
                    w = (int)(imageWidth * ratio);
                    h = (int)(imageHeight * ratio);
     
                }else{ //설정값 (비율무시)
     
                    w = newWidth;
                    h = newHeight;
                }
     
                // 이미지 리사이즈
                // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
                // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
                // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
                // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
                // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
                Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
     
                // 새 이미지  저장하기
                BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = newImage.getGraphics();
                g.drawImage(resizeImage, 0, 0, null);
                g.dispose();
                ImageIO.write(newImage, FileNameUtils.getExtension(origFilename), new File(fileRealPath+"uploadFiles/", fileName));
     
            }catch (Exception e){
     
                e.printStackTrace();
     
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