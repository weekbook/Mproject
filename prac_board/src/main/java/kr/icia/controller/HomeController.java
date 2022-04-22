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
	// get(url을 이용하여 전송 :보안 취약) / post(노출x 보안 o)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		// 개발 콘솔창에 표시하는 메시지
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		// 스프링에서 생성한 현재 날짜와 시간.
		
		String formattedDate = dateFormat.format(date);
		// 날짜 포맷을 문자열 포맷으로 전환
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("myName","이기훈");
		// 생성된 날짜값을 serverTime 이라는 변수에 담아서 전달.
		
		return "home";
	}
	
}
