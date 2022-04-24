package com.review.service;

import java.util.List;

import com.review.domain.BoardVO;
import com.review.domain.Criteria;

public interface BoardService {
public void register(BoardVO board);// 등록.
public BoardVO get(Long bno);// 읽기
public boolean modify(BoardVO board);// 수정
public boolean remove(Long bno);// 삭제
public List<BoardVO> getList();// 목록
public List<BoardVO> getList(Criteria cri); // 페이징 처리
public int getTotal(Criteria cri);
}