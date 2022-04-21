package kr.icia.mapper;

import java.util.List;

import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);

	public int insert(BoardVO board);
	// bno는 시퀸스 자동 생성으로 나머지 값만 입력.
	// 새로운 게시물 1개 추가.

	public void insertSelectKey(BoardVO board);
	// 생성되는 시퀸스 값을 확인하고 나머지 값 입력.
	// 새로운 게시물 1개 추가의 다른 방식.

	public BoardVO read(long l);

	public int delete(long l);

	public int update(BoardVO board);
	
	public int getTotal(Criteria cri);
	
}
