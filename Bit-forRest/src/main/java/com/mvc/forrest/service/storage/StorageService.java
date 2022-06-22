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
	
	   //보관물품을 추가
		public void addStorage(Storage storage) throws Exception{
			storageDAO.addStorage(storage);
		}
		
		//보관물품정보 상세조회
		public Storage getStorage(String tranNo) throws Exception{
			System.out.println("서비스 tranNo:"+tranNo);
			return storageDAO.getStorage(tranNo);
		}
		
		public int getMaxTranNoForStorage() throws Exception{
			return storageDAO.getMaxTranNoForStorage();
		}
		
		//기간연장시 변경되는정보 업데이트
		public void updateStorage(Storage storage) throws Exception{
			storageDAO.updateStorage(storage);
		}
		
		//기간연장시 기존의 보관정보를 삭제
		public void deleteStorage(String tranNo) throws Exception {
			storageDAO.deleteStorage(tranNo);
		}
		
		//유저를 위한 보관리스트 출력
		public Map<String, Object> getStorageList(Map<String,Object> map) throws Exception{
			
			List<Storage> list = storageDAO.getStorageList(map);
			int totalCount = storageDAO.getTotalCount(map);
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
		
		//관리자를 위한 보관리스트 출력
		public Map<String, Object> getStorageListForAdmin(Search search) throws Exception{
			
			String userId = "admin";
			
			Map<String, Object> mapForCount = new HashMap<>();
			mapForCount.put("search", search);
			mapForCount.put("userId", userId);
		
			List<Storage> list = storageDAO.getStorageListForAdmin(search);
			int totalCount = storageDAO.getTotalCount(mapForCount);
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
			
		}
		
		//보관기간이 만료된 물품의 리스트
		public List<Storage> getExpiredStorageList() throws Exception{
			
			return storageDAO.getExpiredStorageList();
			
		}
		
	
}
