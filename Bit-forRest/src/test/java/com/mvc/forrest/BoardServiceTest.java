package com.mvc.forrest;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvc.forrest.service.board.BoardService;
import com.mvc.forrest.service.domain.Board;
import com.mvc.forrest.service.domain.Search;

/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("boardService")
	private BoardService boardService;

	@Test
	public void testListBoard() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(1);
		Board board = new Board();
		board.setBoardFlag("A");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("search", search);
		
		System.out.println(boardService.getListBoard(map));
		Assert.assertEquals("tesasdtUserId", boardService.getListBoard(map));
	}
	

}