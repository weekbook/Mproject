package kr.icia.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;
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
//		// ���� for : �迭�� �迭 1���� ���� ����
//		// ���ٽ� : -> �� �������� ������ ���ް�, ������ ó��
//		// ����� �Խù� ������(�迭), ���� 1���� board�� ���� ������
//		// �ش� ������ �α׷� ���, �迭 ���Ұ� ������ ���� �ݺ�.
//	}
	
	
	// 1.mapper.xml ���� ���̹�Ƽ�� ������ �����
	// 2.�׽�Ʈ �ڵ忡�� �� �޼ҵ� ȣ��
	// 3.���� �������̽��� �޼ҵ� ���� ����
//	@Test
//	public void testInsert() {
//		BoardVO board = new BoardVO();
//		board.setTitle("���� �ۼ��ϴ� ��");
//		board.setContent("���� �ۼ��ϴ� ����");
//		board.setWriter("���ο� �ۼ���");
//		
////		int cnt = mapper.insert(board);
//		mapper.insert(board);
//		log.info(board);
//	}
	
	
//	@Test
//	public void testInsertSelectKey() {
//		BoardVO board = new BoardVO();
//		board.setTitle("���� �ۼ��ϴ� ��SelectKey");
//		board.setContent("���� �ۼ��ϴ� ����SelectKey");
//		board.setWriter("���ο� �ۼ���SelectKey");
//		
//		mapper.insertSelectKey(board);
//		log.info(board);
//	}
	
//	@Test
//	public void testRead() {
//		BoardVO board = mapper.read(4L);
//		// L�� bno�� long Ÿ���̶�� ���� �˸�.
//		
//		log.info(board);
//	}
	
//	@Test
//	public void testDelete() {
//		log.info("delete cnt: " + mapper.delete(5L));
//		// ���� ������ ������ ���ڵ� ���� ����,
//		// ���� ���н� 0 ����
//	}
	
//	@Test
//	public void testUpdate() {
//		BoardVO board = new BoardVO();
//		board.setBno(4L);
//		board.setTitle("������ ����");
//		board.setContent("������ ����");
//		board.setWriter("user00");
//		
//		// ���� ������ ������ ���ڵ� ���� ����,
//		// ���� ���н� 0 ����
//		
//		int count = mapper.update(board);
//		log.info("update cnt : " + count);
//	}
	
	
	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(1); // ������ ��ȣ
		cri.setAmount(10); // �������� ������ �Խù� ����.
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		// 10�� �Խù� ����
		list.forEach(board -> log.info(board.getBno()));
		// ��Ʈ�� ó���� �Խù� ��ȣ�� �α׷� ���.
	}
	
	
	
}
