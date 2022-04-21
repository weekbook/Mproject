package kr.icia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;
import kr.icia.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j // lombok �α� �̿�.
@Service // �� Ŭ������ ���� ������ �ô´ٰ� �˸�.
@AllArgsConstructor // ��� �Ű������� ���� ������ ����.
public class BoardServiceImp implements BoardService {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		log.info("register......" + board);
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get......" + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("modify......" + board);
		return mapper.update(board)==1;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("remove......" + bno);
		return (mapper.delete(bno)) ==1 ;
	}

	@Override
	public List<BoardVO> getList() {
		log.info("getList......");
		// ��� ���ڵ� ����
		return mapper.getList();
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getListWithPaging....." + cri);
		// ����¡ ���Ǵ�� 10���� ����.
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}
}
