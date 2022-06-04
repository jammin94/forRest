package com.mvc.forrest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mvc.forrest.service.board.BoardService;
import com.mvc.forrest.service.domain.Board;
import com.mvc.forrest.service.domain.Search;


@SpringBootTest
public class BoardServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
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
		assertEquals("tesasdtUserId", boardService.getListBoard(map));
	}
	

}