package com.review.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.review.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper;

	@Test // add Junit library	
	public void testGetTime3() {
		log.info("xml 테스트 : " + timeMapper.getTime3());
	}
//	timeMapper : 인터페이스
//	getTime3 : 메소드 선언
//	인터페이스를 implements 받아서 자식 클래스를 생성하고 메소드를 오버라이드 하는 처리를
//	스프링 컴파일러가 자동으로 처리.
//	getTime3 매칭되는 xml 파일의 mybatis 값을 읽어서 결과를 리턴

}
