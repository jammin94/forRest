package com.mvc.forrest.service.oldlike;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.oldlike.OldLikeDAO;
import com.mvc.forrest.service.domain.OldLike;

@Service
public class OldLikeService {
	
	@Autowired
	private OldLikeDAO oldLikeDAO;
	
	//찜하기 추가
	public void addOldLike(OldLike oldLike) throws Exception{
		System.out.println("addOldLike 실행 됨");
		oldLikeDAO.addOldLike(oldLike);
	}
	
	public List<OldLike> getOldLikeList(String userId) throws Exception{
		System.out.println("getOldLikeList 실행 됨");
		return oldLikeDAO.getOldLikeList(userId);
	}
	
	public void deleteOldLike(int oldLikeNo) throws Exception{
		System.out.println("deleteOldLike 실행 됨");
		oldLikeDAO.deleteOldLike(oldLikeNo);
	}	
}
