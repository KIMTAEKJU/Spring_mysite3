package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor01 implements HandlerInterceptor {

	@Override // 핸들러 가기전에
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception 
	{
		// 핸들러 호출 전 (요청 들어가기전)
		// 핸들러 호출 여부를 결정 (boolean 반환값에 따라)  request는 여기서 건드림
		// 체인필터 들어가기전   
		// 요청전에 뭔가해야한다면 여기서
		// 핸들러인터셉터 어댑터를 사용한다 prehandle만 처리하고싶다면 
		// 인터셉터는 3개다 구현해야한다
		
		System.out.println("MyInterceptor01:preHandle");
		return false; // 뒤에애들이 다 안불러짐 false를 하면
	}

	@Override // 보내고나서  렌더링까지 다끝나고나서 불림
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception 
	{
		// 핸들러 호출이 된 후 ( 핸들러 작업이 다끝난후) 여기서 핸들러메소드를 건드림,  (응답중)
		// 체인필터 다음
		// 요청후에도 뭔가해야한다면 여기
		System.out.println("MyInterceptor01:postHandle");

	}

	@Override // 응답까지 다끝나고 나서
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception 
	{
		//view 렌더링까지 다끝난후  (응답후)
		System.out.println("MyInterceptor01:afterCompletion");
	}

}
