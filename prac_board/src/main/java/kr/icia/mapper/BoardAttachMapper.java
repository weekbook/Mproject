package kr.icia.mapper;

import java.util.List;

import kr.icia.domain.BoardAttachVO;

public interface BoardAttachMapper {
	public void insert(BoardAttachVO vo); // ÷������ ���
	
	public void delete(String uuid); // ÷������ ����
	
	public List<BoardAttachVO> findByBno(Long bno); // ÷������ ���
	
	public void deleteAll(Long bno);
	// ÷������ ������ �Ѳ����� ����
	
	public List<BoardAttachVO> getOldFiles();
	// 111.zip Ŀ�´�Ƽ�� ����ϴ� ����ڵ���
	// �ߺ� ���ϸ��� ����� ���� ����.
	// �ý����� ���� ���ϸ� ���ؼ� ���������� �����ϴ� �ٸ� �̸��� ����.
}
