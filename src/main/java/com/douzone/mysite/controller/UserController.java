package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.ws.BindingType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	public String join(@ModelAttribute UserVo userVo)
	{
		//tag.form 태그를 사용하면 userVo가 필요한데 여긴없으니까 에러가 발생
		return "user/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, // @Valid는 객체단위로 확인
					   BindingResult result,
					   Model model)  // 모델에 에러를 넣어서 보냄
	{ // binding 리절트안에 form에서 온값들을 가져옴
		// 자바스크립트로 하면 요청이 들어가기전에 체크를 할수있음 
		
		if( result.hasErrors()) // 하나라도 잘못되어있으면 form으로  값을 되돌림
		{
//			List<ObjectError> list = result.getAllErrors();
//			
//			for( ObjectError error : list) // 여기서 이렇게 출력하는건 노가다라서 도와주는 태그들이 있음 자동으로 파싱해서 화면에뿌려주는
//			{
//				System.out.println(error);
//			}
//			
			model.addAllAttributes(result.getModel()); // map으로 보내버림 키값은 몰라도됨 알아서해주는 스프링태그가 있음
			// 에러를 넘겼을뿐인데 userVo를 사용할수있는건 @ModelAttribute를 달았기때문에 자동으로 포워딩할때 userVo를 넘겨줌
			// 그래서 컨트롤러에서는 @ModelAttribute를 달아주는게 좋다
			return "user/join";
		}
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess()
	{
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute UserVo userVo)
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
