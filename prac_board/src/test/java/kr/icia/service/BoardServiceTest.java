package kr.icia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.icia.domain.BoardVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {
	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("���� �ۼ��ϴ� �� 0414");
		board.setContent("���� �ۼ��ϴ� ���� 0414");
		board.setWriter("���ο� �ۼ��� 0414");
		
		service.register(board);
		log.info("������ �Խù� ��ȣ : " + board.getBno());
		
		// ���� >> ���� >> mybatis query, �ٸ� �޼ҵ� �׽�Ʈ�� ����ؼ� ����
	}
}
