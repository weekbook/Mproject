package com.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.review.domain.BoardVO;
import com.review.domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public int insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(long l);
	
	public int delete(long l);
	
	public int update(BoardVO board);
	
	public int getTotalCount(Criteria cri);
	
	public int updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
