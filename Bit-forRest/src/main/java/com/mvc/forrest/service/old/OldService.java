package com.mvc.forrest.service.old;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.old.OldDAO;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Search;

@Service
public class OldService {

	@Autowired
	private OldDAO oldDAO;
	
	public void addOld(Old old) throws Exception{
		System.out.println("addOld 성공");
		oldDAO.addOld(old);
	}
	
	public Old getOld(String oldNo) throws Exception{
		System.out.println("getOld 성공");
		return oldDAO.getOld(oldNo);
		
	}
	
//	public Old getOld(String loginuserId, String oldNo) throws Exception{
//		System.out.println(loginuserId);
//		System.out.println(oldNo);
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("loginuserId",loginuserId);
//		map.put("oldNo",oldNo);
//		return oldDAO.getOld(map);
//		
//	}
	
	
	
	
	public void updateOld(Old old) throws Exception{
		System.out.println("updateOld 성공");
		oldDAO.updateOld(old);
	}
	
	public void deleteOld(String oldNo) throws Exception{
		System.out.println("deleteOld 성공");
		oldDAO.deleteOld(oldNo);
	}
	
	public List<Old> getOldListHasUser(Search search, String userId) throws Exception{
		System.out.println("getOldList 성공");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("search", search);
		
		return oldDAO.getOldListHasUser(map);
	}
	
	
	public List<Old> getOldList(Search search) throws Exception{
		System.out.println("getOldList 성공");
		System.out.println("search in service"+ search);
		
		return oldDAO.getOldList(search);
	}
	
	public List<Old> getOldListForIndex() throws Exception{
							
		return oldDAO.getOldListForIndex();
	
	}
	
	public List<Old> getOldListCategory(Old old) throws Exception{
		return oldDAO.getOldListCategory(old);
	}
	
	public List<Old> getOldListOthers(Old old) throws Exception{
		return oldDAO.getOldListOthers(old);
	}
	
	public List<Old> getOldListForUser(String userId) throws Exception{
		return oldDAO.getOldListForUser(userId);
	}
	
	public void updateOldDate(String oldNo) throws Exception{
		System.out.println("updateOldDate 성공");
		oldDAO.updateOldDate(oldNo);
	}
	
	public void updateOldSold(String oldNo) throws Exception{
		System.out.println("updateOldState 성공");
		oldDAO.updateOldSold(oldNo);
	}
	
	public void updateOldOnSale(String oldNo) throws Exception{
		System.out.println("updateOldState 성공");
		oldDAO.updateOldOnSale(oldNo);
	}
	
	public void updateViewCnt(String oldNo) throws Exception{
		System.out.println("updateViewCnt 성공");
		oldDAO.updateViewCnt(oldNo);
	}
	
	}
	
