package com.mvc.forrest.dao.oldlike;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.mvc.forrest.service.domain.OldLike;


@Repository
@Mapper
public interface OldLikeDAO {
	
	void addOldLike(OldLike oldLike) throws Exception;
	
	List<OldLike> getOldLikeList(String userId) throws Exception;
	
	void deleteOldLike(int oldLikeNo) throws Exception;

}