package com.review.service;

import java.util.List;

import com.review.domain.BoardVO;
import com.review.domain.Criteria;

public interface BoardService {
public void register(BoardVO board);// ���.
public BoardVO get(Long bno);// �б�
public boolean modify(BoardVO board);// ����
public boolean remove(Long bno);// ����
public List<BoardVO> getList();// ���
public List<BoardVO> getList(Criteria cri); // ����¡ ó��
public int getTotal(Criteria cri);
}