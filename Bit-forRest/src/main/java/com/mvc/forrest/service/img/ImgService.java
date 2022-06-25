package com.mvc.forrest.service.img;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.img.ImgDAO;
import com.mvc.forrest.service.domain.Img;

@Service
public class ImgService {
	
	@Autowired
	private ImgDAO imgDAO;
	
	public List<Img> getProductImgList(String prodNo) throws Exception{
		System.out.println("getProductImgList 실행 됨");
		return imgDAO.getOLdImgList(prodNo);
	}
	
	public List<Img> getOLdImgList(String oldNo) throws Exception{
		System.out.println("getOLdImgList 실행 됨");
		return imgDAO.getOLdImgList(oldNo);
	}
	
	public List<Img> getAnnounceImgList(String boardNo) throws Exception{
		System.out.println("getAnnounceImgList 실행 됨");
		return imgDAO.getAnnounceImgList(boardNo);
	}
	
}