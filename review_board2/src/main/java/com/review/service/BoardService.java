package com.review.service;

import java.util.List;

import com.review.domain.BoardAttachVO;
import com.review.domain.BoardVO;
import com.review.domain.Criteria;

public interface BoardService {
	public void register(BoardVO board);
	public BoardVO get(Long bno);
	public boolean modify(BoardVO board);
	public boolean remove(Long bno);
	public List<BoardVO> getList();
	public List<BoardVO> getList(Criteria cri);
	public int getTotal(Criteria cri);
	
	public List<BoardAttachVO> getAttachList(Long bno);
}
