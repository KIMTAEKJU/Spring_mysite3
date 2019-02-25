package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver 
{
	@Override
	public Object resolveArgument(MethodParameter parameter, 
								  ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, // 아규먼트리절브가 돌고있는곳이 톰캣인지 뭔지 모르니까 native
								  WebDataBinderFactory binderFactory) throws Exception 
	{
		if( supportsParameter(parameter) == false) // 확인했을때 false일경우
		{
			return WebArgumentResolver.UNRESOLVED; // null을 리턴
		}
		
		// @AuthUser가 붙어있고 type이 UserVo
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); // 해당되는 was의 서블릿httpRequest를 리턴해줌
		
		HttpSession session = request.getSession();
		
		if( session == null)
		{
			return null;
		}
		
		return session.getAttribute("authuser");
	}
	

	@Override
	public boolean supportsParameter(MethodParameter parameter) // 어노테이션 붙어있는놈의 타입이 리절브를 해도되는놈인지 판단
	{
		// 파라미터 하나씩옴
		AuthUser authUser = parameter.getParameterAnnotation( AuthUser.class);
		
		//@AuthUser가 안붙어 있음
		if( authUser == null)
		{
			return false; // 파라미터가 리절브가 하는애가 아니다
		}
		
		// 파라미터 타입이 UserVo인지 확인
		if( parameter.getParameterType().equals(UserVo.class) == false)
		{
			return false;
		}
		
		return true;
	}

}
