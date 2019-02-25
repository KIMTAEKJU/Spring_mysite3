package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor02 extends HandlerInterceptorAdapter // 기본적인건 다구현해놨으니 필요한것만 하면된다
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//return super.preHandle(request, response, handler);  부모메소드가 private일때 뜯어 고침
															// private로 해둔건 이건 고치지 말고 오버라이드해서 써라는뜻
	
		System.out.println("MyInterceptor02:preHandle");
		return true;
	}
}
