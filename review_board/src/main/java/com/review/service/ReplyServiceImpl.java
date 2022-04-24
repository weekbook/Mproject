package com.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.domain.Criteria;
import com.review.domain.ReplyVO;
import com.review.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{
	@Setter(onMethod_ =@Autowired)
	private ReplyMapper mapper;
	
	@Override
	public int register(ReplyVO vo) {
		log.info("register...." + vo);
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		log.info("get...." + rno);
		return mapper.read(rno);
	}

	@Override
	public int remove(Long rno) {
		log.info("remove....." + rno);
		return mapper.delete(rno);
	}

	@Override
	public int modify(ReplyVO reply) {
		log.info("modify....." + reply);
		return mapper.update(reply);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		log.info("get Reply list " + bno);
		return mapper.getListWithPaging(cri, bno);
	}

}
