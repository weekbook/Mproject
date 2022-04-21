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
	
	// ������̼ǿ� ���ް��� �������� �迭 ���¶�� {} �̿�
	@Setter(onMethod_ = { @Autowired })
	private BoardService service;
	
	@Test
	public void testResgister() {
		BoardVO board = new BoardVO();
		board.setTitle("���� �ۼ��ϴ� ��");
		board.setContent("���� �ۼ��ϴ� ����");
		board.setWriter("���ο� �ۼ���");
		
		service.register(board);
		log.info("������ �Խù� ��ȣ + " + board.getBno());
		
		// ���� >> ���� >> mybatis query, �ٸ� �޼ҵ� �׽�Ʈ�� �����.
	}
}
