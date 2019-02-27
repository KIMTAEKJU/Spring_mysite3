package com.douzone.security;

import java.util.logging.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth.Role;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception 
	{
		//1. Handler 종류 확인  디폴트서블릿은 auth와 관련이없음 그래서 디폴트서블릿은 그냥 통과시켜줘야함
		// assets 밑에는 예외로 설정해두었지만 그외위치에있는 다른파일들에 대해서는 디폴트서블릿이 작동할수있으니 막아야함
		if( handler instanceof HandlerMethod == false) // 디폴트 서블릿이면
		{
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Method에 @Auth 받아오기   핸들러매핑에 어노테이션정보도 있으니까 핸들러메소드에서 정보를 가져옴
		Auth auth = handlerMethod.getMethodAnnotation( Auth.class); // 없으면 null
		
		// 3-1. Method에 @Auth가 안 붙어있으면 class(type)의 @Auth 받아오기
		if( auth == null)
		{
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation( Auth.class);
		}
		// 클래스 타입에 에 붙은걸 가져올려면
		// auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation( Auth.class);
		
		// 4. method 와 class에 @Auth가 안 붙어있으면 
		if( auth == null)
		{
			return true;
		}
		
		// Role role = auth.value(); auth에다가 ADMIN, USER를 구분하는 접근제어가 필요할때 사용
		
		// 5. @Auth 붙어 있기 때문에 로그인 여부(인증여부 Authorization)를 확인해야 한다.
		HttpSession session = request.getSession();
		UserVo authUser = null;
		
		if( session != null)
		{
			authUser = (UserVo)session.getAttribute("authuser");
		}
		
		if( authUser == null)
		{
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 5-1. Role 비교작업 (권한 체크)
		Role role = auth.value();
		
		System.out.println("role : " + role);
		System.out.println("authUser.getRole() : " + authUser.getRole());
		
		if( authUser.getRole().equals("ADMIN") == false && String.valueOf(role).equals(authUser.getRole()) == false)
		{
			return false;
		}
		
		// 6. 접근허용
		return true;	
	}
}
