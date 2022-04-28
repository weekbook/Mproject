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

	// ������ ������ �Խù� ��ȣ�� ����.
	public List<ReplyVO> getList(@Param("cri") Criteria cri, @Param("bno") Long bno);

	// ������ ��ϰ� �Խù��� ����.
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
