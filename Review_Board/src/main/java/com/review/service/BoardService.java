package com.review.service;

import java.util.List;

import com.review.domain.BoardVO;

public interface BoardService {
public void register(BoardVO board);// ���.
public BoardVO get(Long bno);// �б�
public boolean modify(BoardVO board);// ����
public boolean remove(Long bno);// ����
public List<BoardVO> getList();// ���
}