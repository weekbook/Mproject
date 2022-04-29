package kr.icia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.icia.domain.BoardAttachVO;
import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;
import kr.icia.mapper.BoardAttachMapper;
import kr.icia.mapper.BoardMapper;
import kr.icia.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j // lombok 로그 이용.
@Service // 이 클래스가 서비스 계층을 맡는다고 알림.
@AllArgsConstructor // 모든 매개변수에 대한 생성자 생성.
public class BoardServiceImp implements BoardService {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper; // 덧글
	
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("register......" + board);
		mapper.insertSelectKey(board);
		// 게시물은 부모, 첨부파일은 자식의 개념
		
		if(board.getAttachList() == null ||
				board.getAttachList().size() <= 0) {
			// 첨부파일 객체가 널이거나 첨부파일 객체의 크기가 0 이라하면 메소드 중지.
			return;
		}
		
		board.getAttachList().forEach(attach->{
			attach.setBno(board.getBno()); // 게시물 번호를 할당하고
			attachMapper.insert(attach); // 첨부파일 정보 디비에 등록.
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get......" + bno);
		return mapper.read(bno);
	}

	   @Transactional
	   @Override
	   public boolean modify(BoardVO board) {
	      log.info("modify......" + board);
	      boolean modifyResult = false; // 게시물 수정 성공 여부.
	      modifyResult = mapper.update(board) == 1;
	      int attachList = 0;// 첨부파일 갯수.
	      if (board.getAttachList() != null) {
	         attachList = board.getAttachList().size();
	      }
	      
	      long bno = board.getBno();
	      attachMapper.deleteAll(bno);
	      // 파일은 삭제 했어도, 디비 정보가 남아 있는 부분을 해소.
	      
	      if (modifyResult && attachList > 0) {
	         // 등록하려는 첨부파일 목록(11,22)
	         List<BoardAttachVO> inputList = board.getAttachList();
	         // 디비에 등록되어 있는 첨부파일 목록(22,33)
	         // List<BoardAttachVO> dbList = attachMapper.findByBno(board.getBno());
	         
	         for (BoardAttachVO bav : inputList) {
	            bav.setBno(bno);
	            attachMapper.insert(bav);
	         }
	      }
	      return modifyResult;
	   }

	@Transactional
	@Override
	public boolean remove(Long bno) {
		log.info("remove......" + bno);
		replyMapper.deleteAll(bno);
		attachMapper.deleteAll(bno); // 게시물 삭제시 해당 게시물의 첨부파일 모두 삭제.
		
		return (mapper.delete(bno)) ==1 ;
	}
	
	@Override
	public List<BoardVO> getList() {
		log.info("getList......");
		// 모든 레코드 추출
		return mapper.getList();
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getListWithPaging....." + cri);
		// 페이징 조건대로 10개씩 추출.
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("get Attach list by bno : " + bno);
		return attachMapper.findByBno(bno);
		// 게시물 번호를 전달하고,
		// 게시물 번호와 일치하는 첨부파일을 모두 리턴.
	}

}
