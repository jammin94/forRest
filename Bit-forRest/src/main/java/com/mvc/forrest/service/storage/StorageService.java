package com.mvc.forrest.service.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.storage.StorageDAO;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Storage;

@Service
public class StorageService {
	
	@Autowired
	private StorageDAO storageDAO;
	
		public void addStorage(Storage storage) throws Exception{
			storageDAO.addStorage(storage);
		}
		
		public Storage getStorage(int tranNo) throws Exception{
			return storageDAO.getStorage(tranNo);
		}
		
		
		//controller에서 map에 search와 userId담아서 맵으로 보내기
		public Map<String, Object> getStorageList(Map<String,Object> map) throws Exception{
			
			List<Storage> list = storageDAO.getStorageList(map);
			int totalCount = storageDAO.getTotalCount((Search) map.get("search"));
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
		
		//관리자를 위한 보관리스트 출력
		public Map<String, Object> getStorageListForAdmin(Search search) throws Exception{
			
			List<Storage> list = storageDAO.getStorageListForAdmin(search);
			int totalCount = storageDAO.getTotalCount(search);
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
			
		}
	
}
