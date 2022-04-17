package com.review.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.review.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
//	@Test
//	public void testGetList() {
//		mapper.getList().forEach(board -> log.info(board));
//	}
	

//	@Test
//	public void testInsert() {
//		BoardVO board = new BoardVO();
//		board.setTitle("새로 작성하는 글");
//		board.setContent("새로 작성하는 내용");
//		board.setWriter("새로운 작성자");
//		
//		mapper.insert(board);
//		log.info(board);
//	}
	
//	@Test
//	public void testInserSelectKey() {
//		BoardVO board = new BoardVO();
//		board.setTitle("새로 작성하는 글SelectKey");
//		board.setContent("새로 작성하는 내용SelectKey");
//		board.setWriter("새로운 작성자SelectKey");
//		
//		mapper.insertSelectKey(board);
//		log.info(board);
//	}
	
//	@Test
//	public void testRead() {
//		BoardVO board = mapper.read(5L);
//		// L은 bno가 long타입이라는 것을 알림.
//		log.info(board);
//	}
	
//	@Test
//	public void testDelete() {
//		log.info("delete cnt: " + mapper.delete(24L));
//	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setBno(23L);
		board.setTitle("수정된 제목");
		board.setContent("수정된 내용");
		board.setWriter("user00");
		
		int count = mapper.update(board);
		log.info("update cnt : " + count);
	}
}
