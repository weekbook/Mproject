package kr.icia.service;

import java.util.List;

import kr.icia.domain.BoardAttachVO;
import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board); //등록
	
	public BoardVO get(Long bno); // 읽기
	
	public boolean modify(BoardVO board); // 수정
	
	public boolean remove(Long bno); // 삭제
	
	public List<BoardVO> getList(); // 목록
	
	public List<BoardVO> getList(Criteria cri); // 페이징 목록

	public int getTotal(Criteria cri);
	
	public List<BoardAttachVO> getAttachList(Long bno);
	// 게시물의 정보를 가지고 오면서, 첨부파일의 정보도 포함.
	// 게시물 읽기 시에 첨부파일 목록을 표시하여 다운로드 가능하도록 처리.
}
