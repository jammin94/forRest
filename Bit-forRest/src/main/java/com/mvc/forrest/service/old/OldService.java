package com.mvc.forrest.service.old;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.common.Search;
import com.mvc.forrest.dao.old.OldDAO;
import com.mvc.forrest.service.domain.Old;

@Service
public class OldService {

	@Autowired
	private OldDAO oldDAO;
	
	public void addOld(Old old) throws Exception{
		System.out.println("addOld 성공");
		oldDAO.addOld(old);
	}
	
	public Old getOld(int oldNo) throws Exception{
		System.out.println("getOld 성공");
		return oldDAO.getOld(oldNo);
		
	}
	
	public void updateOld(Old old) throws Exception{
		System.out.println("updateOld 성공");
		oldDAO.updateOld(old);
	}
	
	public void deleteOld(int oldNo) throws Exception{
		System.out.println("deleteOld 성공");
		oldDAO.deleteOld(oldNo);
	}
	
	public Map<String, Object> getOldList(Search search) throws Exception{
		System.out.println("getOldList 성공");
		List<Old> list= oldDAO.getOldList(search);
		
		int totalCount = oldDAO.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
}
	
	
		
	}
	
