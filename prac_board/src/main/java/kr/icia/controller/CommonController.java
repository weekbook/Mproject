package kr.icia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		if(error != null)
			model.addAttribute("error","������ Ȯ���� �ּ���");
		if(logout != null)
			model.addAttribute("logout","�α׾ƿ�");
	}
}
