package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // AOP를 해줌? 런타임익셉션은 다 일로옴  이것도 스캐닝이 된다 스캐닝이 되도록 해줘야함
public class GlobalExceptionHandler // 모든 컨트롤러의 익셉션 처리
{
	
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class) // 어떤 익셉션을 처리해라 매핑을 해줌
	public ModelAndView handlerException(HttpServletRequest request, Exception e) // 컨트롤러가 아니기때문에 기술이 들어와야함
	{
		// 1. 로깅작업
		StringWriter errors = new StringWriter(); // 화면에 뿌리기위해서  메모리에 연결되어있음
		e.printStackTrace(new PrintWriter(errors)); // 화면에다 뿌리게 되어있는 메모리에 뿌리게함
		//LOG.error(errors.toString());  //파일로 저장하기위해선 string으로 바꿔야해서
		
		// 2. 시스템 오류 안내 페이지 전환
		ModelAndView mav = new ModelAndView();
		mav.addObject("errors", errors.toString());
		mav.setViewName("error/exception");
		return mav;
	}
}
