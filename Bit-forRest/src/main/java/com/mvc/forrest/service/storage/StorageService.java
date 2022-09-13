package com.mvc.forrest.service.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.storage.StorageDAO;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Storage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
	
	private final StorageDAO storageDAO;
	
	   //보관물품을 추가
		public void addStorage(Storage storage) throws Exception{
			storageDAO.addStorage(storage);
		}
		
		//보관물품정보 상세조회
		public Storage getStorage(String tranNo) throws Exception{
			return storageDAO.getStorage(tranNo);
		}
		
		//기간연장시 변경되는정보 업데이트
		public void updateStorage(Storage storage) throws Exception{
			storageDAO.updateStorage(storage);
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
		
		public Storage setParam(Storage storage, String userId, String prodNo, String paymentNo, String prodImg) {
			storage.setUserId(userId);
			storage.setProdNo(prodNo);
			storage.setPaymentNo(paymentNo);
			storage.setProdImg(prodImg);
			
			return storage;
		}
		
	
}
