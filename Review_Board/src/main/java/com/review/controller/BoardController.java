package com.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.review.domain.BoardVO;
import com.review.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
@Controller
public class BoardController {

	private BoardService service;

	@GetMapping("/list")
	private void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());

		// ��Ʈ�ѷ� >> ���� >> ���� >> mybatis
	}

	
	@GetMapping("/register")
	public void register() {
	// �̵��� �ּҸ� �������� �ʴ´ٸ�, ��û�� �̸������� jsp ������ ã��.
	}

	// �۾���
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		// @Controller ������̼��� �ٰ�,
		// ������Ʈ ��ĵ�� ��Ű���� �����Ǿ� �ִٸ�,
		// �Ű����� ���ڵ��� �������� �ڵ����� ���� �Ҵ� ��.
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		// �����̷�Ʈ ��Ű�鼭 1ȸ�� ���� ����.
		return "redirect:/board/list";
	}

	// ���б�
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, Model model) {
		// @RequestParam : ��û ���ް����� �۹�ȣ �̿�.
		log.info("/get");
		model.addAttribute("board", service.get(bno));
		// jsp���� request.setAttribute �ϴ� �Ͱ� ���.
		// ���ް����� ���ø� �ϸ� �������� �ڵ� ó��.
		// ����ϴ� �κи� �߰� ����.
	}

	// �� ����
	// post ��û���� /modify�� �´ٸ�, �Ʒ� �޼ҵ� ����.
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify:" + board);
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		// ������ �����ϸ� success �޼����� ���ԵǾ� �̵�.
		// �����ص� �޼��� ���� �̵�.
		return "redirect:/board/list";
	}

	// �� ����
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove..." + bno);
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
}