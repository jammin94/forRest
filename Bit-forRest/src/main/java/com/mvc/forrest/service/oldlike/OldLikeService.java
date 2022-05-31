package com.mvc.forrest.service.oldlike;

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

}
