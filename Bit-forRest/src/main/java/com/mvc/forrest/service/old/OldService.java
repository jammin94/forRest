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
	
	
	public Map<String, Object> getOldList(Search search) throws Exception{
		System.out.println("getOldList 성공");
		List<Old> list= oldDAO.getOldList(search);
		
		//int totalCount = oldDAO.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
	//	map.put("totalCount", totalCount);
		
		return map;
}
	
	public List<Old> getOldListForIndex() throws Exception{
							
		return oldDAO.getOldListForIndex();
	
	}
	
		
	}
	
