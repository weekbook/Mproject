package kr.icia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.icia.domain.Criteria;
import kr.icia.domain.ReplyPageDTO;
import kr.icia.domain.ReplyVO;
import kr.icia.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController // json�̳� xml�� ������ ��ȯ
// �������� GET�� POST�� ��������� REST ��Ű��ó�� 2���� ���ؼ� PUT,DELETE�� ���� �޼��嵵 �߰��Ͽ� ���
@Log4j
@AllArgsConstructor
@RequestMapping("/replies/")
public class ReplyController {
	private ReplyService service;

	// ��û�� /replies/new �� ����,
	// ������ ��ȸ�ؼ� ���� �ϴµ�, ���� ���´� json�̰�, ���� �������
	// ����� ���ڿ� ����
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		// @RequestBody �� json ���·� ���� ���� ��ü�� ��ȯ
		log.info("ReplyVO : " + vo);
		int insertCount = service.register(vo);
		log.info("Reply insert count : " + insertCount);

		return insertCount == 1 ?
				new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(
						HttpStatus.INTERNAL_SERVER_ERROR);
				// ResponseEntity : �������� ����(�����ڵ� ,��� ,����, ������)

				// 3�� ������ �̿�
				// HttpStatus������ ���¸� ����.
				// ���Ͽ� �ڵ�� ������, Ǯ���ϸ�,
				// ���� ó���Ǹ� ���� ó���� status �����ϰ�, �ƴϸ� ���� status ����
	}

	// bno�� �Խù� ��ȣ, page�� ������ ������.
	// ����� ���, �Խù� ���� ����� �ٸ���.
	@GetMapping(value = "/pages/{bno}/{page}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page
			, @PathVariable("bno") Long bno) {
		// @PathVariable : url�� �Ѱܹ��� �� �̿�
		log.info("getList.....");
		Criteria cri = new Criteria(page , 10);
		log.info(cri);

		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
		// T<List<ReplyVO>> t = new T<>();
		// ��� ����� �⤩���ϰ�, ���� ó�� ���¸� ����
	}

	// ��� 1�� �б�
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("get: " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{rno}"
			, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(
			@PathVariable("rno") Long rno) {
		log.info("remove: " + rno);

		return service.remove(rno) == 1 ?
				new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(
						HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(method= {RequestMethod.PUT,
			RequestMethod.PATCH}, value="/{rno}",
			consumes="application/json",
			produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(
			@RequestBody ReplyVO vo,
			@PathVariable("rno") Long rno){
		// put, patch �Ѵ� ���� ó�� ����Ŵ.
		// �����Ǵ� ������ ���´� json �� �Ϲ����� ���ڿ� �̿�.
		// @RequestBody : json���� ������ ������ ��üȭ.
		vo.setRno(rno);
		log.info("rno: "+rno);
		log.info("modify: "+vo);
		return service.modify(vo)==1
				? new ResponseEntity<>("success"
						, HttpStatus.OK)
						:new ResponseEntity<>(
								HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
