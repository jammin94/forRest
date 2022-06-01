package com.mvc.forrest.dao.storage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.common.Search;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Storage;


@Mapper
public interface StorageDAO {
	
	
	void addStorage(Storage storage) throws Exception;
	
	Storage getStorage(int tranNo) throws Exception;
	
	List<Storage> getStorageList(int tranNo) throws Exception;
	
	List<Storage> getStorageListForAdmin(Search search) throws Exception;
	
	int getTotalCount(Search search) throws Exception;	
}
