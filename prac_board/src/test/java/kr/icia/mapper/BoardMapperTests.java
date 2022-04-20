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
//		// 향상된 for : 배열과 배열 1개를 담을 변수
//		// 람다식 : -> 를 기준으로 좌항은 전달값, 우항은 처리
//		// 목록은 게시물 여러개(배열), 그중 1개를 board에 담은 다음에
//		// 해당 내용을 로그로 출력, 배열 원소가 끝날때 까지 반복.
//	}
	
	
	// 1.mapper.xml 에서 마이바티스 쿼리문 만들기
	// 2.테스트 코드에서 위 메소드 호출
	// 3.관련 인터페이스에 메소드 원형 구현
//	@Test
//	public void testInsert() {
//		BoardVO board = new BoardVO();
//		board.setTitle("새로 작성하는 글");
//		board.setContent("새로 작성하는 내용");
//		board.setWriter("새로운 작성자");
//		
////		int cnt = mapper.insert(board);
//		mapper.insert(board);
//		log.info(board);
//	}
	
	
//	@Test
//	public void testInsertSelectKey() {
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
//		BoardVO board = mapper.read(4L);
//		// L은 bno가 long 타입이라는 것을 알림.
//		
//		log.info(board);
//	}
	
//	@Test
//	public void testDelete() {
//		log.info("delete cnt: " + mapper.delete(5L));
//		// 삭제 성공시 삭제된 레코드 갯수 리턴,
//		// 삭제 실패시 0 리턴
//	}
	
//	@Test
//	public void testUpdate() {
//		BoardVO board = new BoardVO();
//		board.setBno(4L);
//		board.setTitle("수정된 제목");
//		board.setContent("수정된 내용");
//		board.setWriter("user00");
//		
//		// 수정 성공시 삭제된 레코드 갯수 리턴,
//		// 수정 실패시 0 리턴
//		
//		int count = mapper.update(board);
//		log.info("update cnt : " + count);
//	}
	
	
	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(1); // 페이지 번호
		cri.setAmount(10); // 페이지당 보여줄 게시물 갯수.
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		// 10개 게시물 리턴
		list.forEach(board -> log.info(board.getBno()));
		// 스트림 처리로 게시물 번호만 로그로 출력.
	}
	
	
	
}
