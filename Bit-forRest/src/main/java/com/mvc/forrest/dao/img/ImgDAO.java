package com.mvc.forrest.dao.img;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


//@Mapper
public interface ImgDAO {
	
	public void addImg(Map<String, String> map) throws Exception;
	
	public List<String>getListImg(Map<String,Object> map)  throws Exception;
	
	public void updateImg(Map<String,String> map)  throws Exception;
	
	public void deleteImg(Map<String,String> map) throws Exception;
	
}
