package kr.icia.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.icia.domain.BoardAttachVO;
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
	 * @GetMApping : 페이지 요청 방식이 get일 경우  .method = RequestMethod.GET
	 * @PostMapping : 페이지 요청 방식이 post일 경우.
	 */
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		
		log.info("list");
		
		model.addAttribute("list",service.getList(cri));
		
		int total = service.getTotal(cri);
		
		// /WEB-INF/views/list.jsp
		// 컨트롤러에서 리턴하는 문자열이 없다면 요청한 URL과 매칭되는 jsp를 우선 검색.
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		// 임의로 총게시물 190로 설정.
	}
	
	
	@GetMapping("/register")
	public void register() {
		// 이동할 주소를 리턴하지 않는다면, 요청한 이름으로의 jsp파일을 찾음.
		
	}
	
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		// @Controller 어노테이션이 붙고,
		// 컴포넌트 스캔에 패키지가 지정되어 있다면,
		// 매개변수 인자들은 스프링이 자동으로 생성 할당 함.
		log.info("register : " + board);
		service.register(board);
		// Controller에서는 첨부파일을 처리하지 않고,
		// 서비스 계층에서 처리할 예정 이다.
		// 그러므로, 첨부파일 정보에 bno가 널인 것은 상관 없음.
		
		if(board.getAttachList() != null) {
			// 첨부파일이 있다면,
			board.getAttachList().forEach(attach -> log.info(attach));
			// 첨부파일의 각 요소를 로그로 출력.
		}
		
		rttr.addFlashAttribute("result",board.getBno());
		// 리다이렉트 시키면서 1회용 값을 전달.
		
		return "redirect:/board/list";
	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, Model model
			, @ModelAttribute("cri") Criteria cri) {
		// @ModelAttribute("cri") Criteria cri == model.addAttribute("cri",cri);
		// @RequestParam : 요청 전달값으로 글번호 이용.
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));
		// 전달값으로 명시만 하면 스프링이 자동 처리.
		// 사용하는 부분만 추가 구현
	}
	
	
	// post요청으로 /modify가 온다면, 아래 메소드 수행
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr, Criteria cri) {
		log.info("modify: " + board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		// 수정이 성공하면 success 메시지가 포함되어 이동.
		// 실패해도 메시지 빼고 이동.
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
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			
			deleteFiles(attachList); // 서버디스크의 파일 정보 삭제.
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// 글 내용을 읽으면서 ajax 호출로 정부파일 목록 표서드.
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		log.info("getAttachList: " + bno);
		
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
	
	
	// 첨부파일 삭제 처리 메소드.
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if (attachList == null || attachList.size() == 0) {
			return;
		}
		log.info("delete attach file....");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get(
						"c:\\upload\\"+attach.getUploadPath()+"\\"
						+ attach.getUuid() + "_" + attach.getFileName());
				Files.deleteIfExists(file);
				// 해당 파일이 존재한다면 삭제 처리.
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
