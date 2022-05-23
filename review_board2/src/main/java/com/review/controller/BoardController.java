package com.review.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.review.domain.BoardAttachVO;
import com.review.domain.BoardVO;
import com.review.domain.Criteria;
import com.review.domain.pageDTO;
import com.review.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

	private BoardService service;

	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("list");
		model.addAttribute("list", service.getList(cri));

		int total = service.getTotal(cri);

		model.addAttribute("pageMaker", new pageDTO(cri, total));
	}

	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {

	}

	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("등록" + board);
		service.register(board);

		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}

		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:/board/list";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("get, modify");
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/modify")
	@PreAuthorize("principal.username==#board.writer")
	public String modify(BoardVO board, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("modify:" + board);
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

//		rttr.addAttribute("pageNum",cri.getPageNum());
//		rttr.addAttribute("amount",cri.getAmount());
//		rttr.addAttribute("keyword",cri.getKeyword());
//		rttr.addAttribute("type",cri.getType());

		return "redirect:/board/list" + cri.getListLink();
	}

	@PostMapping("/remove")
	@PreAuthorize("principal.username==#writer")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri,
			String writer) {
		log.info("삭제" + bno);
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}

//		rttr.addAttribute("pageNum",cri.getPageNum());
//		rttr.addAttribute("amount",cri.getAmount());
//		rttr.addAttribute("keyword",cri.getKeyword());
//		rttr.addAttribute("type",cri.getType());
		return "redirect:/board/list" + cri.getListLink();
	}

	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		log.info("getAttachList : " + bno);
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}

	private void deleteFiles(List<BoardAttachVO> attachList) {
		if (attachList == null || attachList.size() == 0) {
			return;
		}
		log.info("첨부파일 삭제...");
		log.info(attachList);

		attachList.forEach(attach -> {
			try {
				Path filePath = Paths.get(
						"c:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());
				Files.deleteIfExists(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
