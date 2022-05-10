package com.toyclone.ex.service;

import com.toyclone.ex.domain.BoardVO;

import java.util.List;

public interface BoardService {
    public List<BoardVO> getList();
    public void register(BoardVO board);
    public BoardVO get(Long bno);
    public boolean modify(BoardVO board);
    public boolean remove(Long bno);
}
