package com.douzone.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice // AOP를 해줌? 런타임익셉션은 다 일로옴  이것도 스캐닝이 된다 스캐닝이 되도록 해줘야함
public class GlobalExceptionHandler // 모든 컨트롤러의 익셉션 처리
{
	
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class) // 어떤 익셉션을 처리해라 매핑을 해줌
	public void handlerException(HttpServletRequest request, 
										 HttpServletResponse response,
										 Exception e) throws ServletException, IOException // 컨트롤러가 아니기때문에 기술이 들어와야함
	{
		// 1. 로깅작업
		StringWriter errors = new StringWriter(); // 화면에 뿌리기위해서  메모리에 연결되어있음
		e.printStackTrace(new PrintWriter(errors)); // 화면에다 뿌리게 되어있는 메모리에 뿌리게함
		LOG.error(errors.toString());  //파일로 저장하기위해선 string으로 바꿔야해서
		
		// 2. 시스템 오류 안내 페이지 전환
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("errors", errors.toString());
//		mav.setViewName("error/exception");
		//return mav;
		
		// json으로 요청했는지 jsp로 요청했는지 여기서 판단해서 응답을 다르게해야함
		
		String accept = request.getHeader("accept"); //accept 내용을 받아와서
		System.out.println("accept : " + accept);
		
		if( accept.equals(".*application/json.*")) // 앞 뒤에 어떤 문자가붙어있던 중간에 어플리케이션/제이슨이있으면 .* : 모든문자열 이 붙어있고
		{ // json 응답
			response.setStatus(HttpServletResponse.SC_OK);
			OutputStream out = response.getOutputStream(); // 바이트로 출력 getWriter는 개행도 쓰고 뭐이것저것 다되지만 이건 바이트로 쭉 보냄
			
			out.write(new ObjectMapper().writeValueAsString(JSONResult.fail(errors.toString())).getBytes("utf-8"));
			out.flush();
			out.close();
		}
		// 모델앤뷰는 jsp로 응답하기만 가능 그래서 response가 필요
		else // html 응답
		{
			System.out.println("html 응답");
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
	}
}
