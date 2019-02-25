package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.exception.UserDaoException;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join()
	{
		return "user/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo)
	{
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess()
	{
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		return "/user/login";
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(HttpSession session, @ModelAttribute UserVo userVo, Model model)
//	{
//		UserVo authUser = userService.login(userVo);
//		
//		if( authUser == null)
//		{
//			System.out.println("실패!");
//			model.addAttribute("result", "fail");
//			return "/user/login";
//		}
//		
//		session.setAttribute("authuser", authUser);
//		return "redirect:/";
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session)
//	{	
//		if( session.getAttribute("authuser") != null)
//		{
//			session.removeAttribute("authuser");
//			session.invalidate();
//		}
//		
//		return "redirect:/";
//	}
	
	@Auth // 2개 넣고싶다면 속성이름까지 1개면 속성이름 생략
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Model model) // 성능은 살짝 떨어지겠지만 이식성이 좋아짐
	{
		System.out.println(authUser);
		model.addAttribute("vo", authUser);
		return "user/modify";
	}
	
	@RequestMapping(value = {"/modify", "/modify/{no}"}, method = RequestMethod.POST)
	public String modify(@ModelAttribute UserVo uVo, @AuthUser UserVo authUser)
	{
		userService.modify(uVo);
		//session.setAttribute("authuser", uVo);
		authUser.setName(uVo.getName());
		return "redirect:/";
	}
	
	
//	@ExceptionHandler( UserDaoException.class)
//	public String handleUserDaoException()
//	{
//		// 1. 로깅작업
//		
//		// 2. 화면 전환 (사과 페이지)
//		return "error/exception"; 
//	}
	
}
