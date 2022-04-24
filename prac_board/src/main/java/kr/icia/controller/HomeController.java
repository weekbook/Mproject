package kr.icia.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// get(url�� �̿��Ͽ� ���� :���� ���) / post(����x ���� o)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		// ���� �ܼ�â�� ǥ���ϴ� �޽���
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		// ���������� ������ ���� ��¥�� �ð�.
		
		String formattedDate = dateFormat.format(date);
		// ��¥ ������ ���ڿ� �������� ��ȯ
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("myName","�̱���");
		// ������ ��¥���� serverTime �̶�� ������ ��Ƽ� ����.
		
		return "home";
	}
	
}
