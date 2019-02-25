package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter 
{
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception 
	{
		// 컨테이너가 전역범위에 있다   서블릿 컨텍스트에 묶여있다?
//		ApplicationContext ac = 
//				WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
//		UserService userService = ac.getBean(UserService.class); // 컨테이너에 userservice를 가져옴
		// 컨테이너도 웹 어플리케이션 컨텍스트에 생성되기떄문에 위에 코드가 필요없다 주입받으면 되니까
		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// 스프링과 관련없음 userService는 root 웹어플리케이션 컨텍스트가에 있다
		// 여기서 new를 하면 따로 만들어져서 안됨 root에 있는걸 사용해야함
		//new UserService().getUser(email, password);
		
		UserVo paramVo = new UserVo();
		paramVo.setEmail(email);
		paramVo.setPassword(password);
		
		UserVo userVo = userService.login(paramVo);
		
		if( userVo == null)
		{
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 로그인 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authuser", userVo);
		response.sendRedirect(request.getContextPath() + "/");

		return false;
	}
}
