package com.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.review.domain.Criteria;
import com.review.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO vo);
	
	public ReplyVO read(Long rno);
	
	public int delete(Long rno);
	
	public int update(ReplyVO reply);
	
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
	// 페이지 정보와 게시물 번호를 전달.
}
