package kr.icia.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.icia.domain.Criteria;
import kr.icia.domain.ReplyPageDTO;
import kr.icia.domain.ReplyVO;

public interface ReplyService {

	public int register(ReplyVO vo);

	public ReplyVO get(Long vo);

	public int remove(Long rno);

	public int modify(ReplyVO reply);

	// 페이지 정보와 게시물 번호를 전달.
	public List<ReplyVO> getList(@Param("cri") Criteria cri, @Param("bno") Long bno);

	// 덧글의 목록과 게시물의 갯수.
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
