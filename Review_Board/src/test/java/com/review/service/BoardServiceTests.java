package com.review.service;

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
public class BoardServiceTests {
	
	// 어노테이션에 전달값이 여러개의 배열 형태라면 {} 이용
	@Setter(onMethod_ = { @Autowired })
	private BoardService service;
	
	@Test
	public void testResgister() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("새로운 작성자");
		
		service.register(board);
		log.info("생성된 게시물 번호 + " + board.getBno());
		
		// 서비스 >> 매퍼 >> mybatis query, 다른 메소드 테스트는 비슷함.
	}
}
