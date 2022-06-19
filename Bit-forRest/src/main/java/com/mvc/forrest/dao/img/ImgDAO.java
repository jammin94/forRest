package com.mvc.forrest.dao.img;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Img;


@Mapper
public interface ImgDAO {
	
	public void addImg(Img img) throws Exception;
	
	public List<Img>getOLdImgList(String contentsNo)  throws Exception;
	
	public List<Img>getProductImgList(String contentsNo)  throws Exception;
	
	
}
