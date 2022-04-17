package com.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.domain.BoardVO;
import com.review.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j // lombok 로그 이용.
@Service // 이 클래스가 서비스 계층을 맡는다고 알림.
@AllArgsConstructor // 모든 매개변수에 대한 생성자 생성.(생성자 여러개 아님)
public class BoardServiceImp implements BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		log.info("register....." + board);
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get....." + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("modify...... " + board);
		return mapper.update(board)==1;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("remove......" + bno);
		return (mapper.delete(bno))==1;
	}

	@Override
	public List<BoardVO> getList() {
		log.info("getList.....0");
		return mapper.getList();
	}

}
