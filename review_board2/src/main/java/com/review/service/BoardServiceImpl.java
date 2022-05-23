package com.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.review.domain.BoardAttachVO;
import com.review.domain.BoardVO;
import com.review.domain.Criteria;
import com.review.mapper.BoardAttachMapper;
import com.review.mapper.BoardMapper;
import com.review.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("���" + board);
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		board.getAttachList().forEach(attach->{
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("�б�"+ bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		log.info("���� " + board);
		
		boolean modifyResult = false;
		modifyResult = mapper.update(board) == 1;
		int attachList = 0;
		if (board.getAttachList() != null) {
			attachList = board.getAttachList().size();
		}
		
		long bno = board.getBno();
		attachMapper.deleteAll(bno);
		
		if(modifyResult && attachList > 0) {
			List<BoardAttachVO> inputList = board.getAttachList();
			
			for(BoardAttachVO bav : inputList) {
				bav.setBno(bno);
				attachMapper.insert(bav);
			}
		}
		return modifyResult;
	}

	@Transactional
	@Override
	public boolean remove(Long bno) {
		log.info("����" + bno);
		replyMapper.deleteAll(bno);
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList() {
		log.info("���");
		return mapper.getList();
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("boardServiceImp cri...." + cri);
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("첨부 파일 게시물 번호 :" + bno);
		return attachMapper.findByBno(bno);
	}

}
