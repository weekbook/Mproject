package kr.icia.mapper;

import java.util.List;

import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);

	public int insert(BoardVO board);
	// bno�� ������ �ڵ� �������� ������ ���� �Է�.
	// ���ο� �Խù� 1�� �߰�.

	public void insertSelectKey(BoardVO board);
	// �����Ǵ� ������ ���� Ȯ���ϰ� ������ �� �Է�.
	// ���ο� �Խù� 1�� �߰��� �ٸ� ���.

	public BoardVO read(long l);

	public int delete(long l);

	public int update(BoardVO board);
	
	public int getTotal(Criteria cri);
	
}
