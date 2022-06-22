package com.mvc.forrest.dao.old;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Search;

@Repository
@Mapper
public interface OldDAO {
	public void addOld(Old old) throws Exception ;
	
	public Old getOld(String oldNo) throws Exception ;
	
	public void updateOld(Old old) throws Exception ;
	
	public void deleteOld(String oldNo) throws Exception ;	

	public List<Old> getOldList(Search search) throws Exception; 
	
	public List<Old> getOldListHasUser(Map<String,Object> map) throws Exception; 
	
	public List<Old> getOldListForIndex( ) throws Exception;
	
	public List<Old> getOldListCategory(Old old) throws Exception;
	
	public List<Old> getOldListOthers(Old old) throws Exception;
	
	public List<Old> getOldListForUser(String userId) throws Exception;
	
	public void updateOldDate(String oldNo) throws Exception ;	
	
	public void updateOldOnSale(String oldNo) throws Exception ;
	
	public void updateOldSold(String oldNo) throws Exception ;
	
	public void updateViewCnt(String oldNo) throws Exception ;
}
