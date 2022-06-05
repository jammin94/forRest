package com.mvc.forrest.dao.img;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Img;


@Mapper
public interface ImgDAO {
	
	public void addImg(Img img) throws Exception;
	
	public List<String>getListImg(Map<String,Object> map)  throws Exception;
	
	public void updateImg(Map<String,String> map)  throws Exception;
	
	public void deleteImg(Map<String,String> map) throws Exception;
	
}
