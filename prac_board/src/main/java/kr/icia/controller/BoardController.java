package kr.icia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.icia.domain.BoardVO;
import kr.icia.domain.Criteria;
import kr.icia.domain.PageDTO;
import kr.icia.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
	private BoardService service;
	
	/*
	 * @GetMApping : ������ ��û ����� get�� ���  .method = RequestMethod.GET
	 * @PostMapping : ������ ��û ����� post�� ���.
	 */
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		
		log.info("list");
		
		model.addAttribute("list",service.getList(cri));
		
		int total = service.getTotal(cri);
		
		// /WEB-INF/views/list.jsp
		// ��Ʈ�ѷ����� �����ϴ� ���ڿ��� ���ٸ� ��û�� URL�� ��Ī�Ǵ� jsp�� �켱 �˻�.
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		// ���Ƿ� �ѰԽù� 190�� ����.
	}
	
	
	@GetMapping("/register")
	public void register() {
		// �̵��� �ּҸ� �������� �ʴ´ٸ�, ��û�� �̸������� jsp������ ã��.
		
	}
	
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		// @Controller ������̼��� �ٰ�,
		// ������Ʈ ��ĵ�� ��Ű���� �����Ǿ� �ִٸ�,
		// �Ű����� ���ڵ��� �������� �ڵ����� ���� �Ҵ� ��.
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result",board.getBno());
		// �����̷�Ʈ ��Ű�鼭 1ȸ�� ���� ����.
		
		return "redirect:/board/list";
	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, Model model
			, @ModelAttribute("cri") Criteria cri) {
		// @ModelAttribute("cri") Criteria cri == model.addAttribute("cri",cri);
		// @RequestParam : ��û ���ް����� �۹�ȣ �̿�.
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));
		// ���ް����� ��ø� �ϸ� �������� �ڵ� ó��.
		// ����ϴ� �κи� �߰� ����
	}
	
	
	// post��û���� /modify�� �´ٸ�, �Ʒ� �޼ҵ� ����
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr, Criteria cri) {
		log.info("modify: " + board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		// ������ �����ϸ� success �޽����� ���ԵǾ� �̵�.
		// �����ص� �޽��� ���� �̵�.
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr, 
			Criteria cri) {
		
		log.info("remove..." + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
