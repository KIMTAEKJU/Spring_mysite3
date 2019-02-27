package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.AuthUser;

@Controller
public class MainController 
{	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"", "/main"})
	public String main(Model model, HttpSession session)
	{
		List<SiteVo> siteVo = siteService.getSite();
		model.addAttribute("site", siteVo.get(0));
		session.setAttribute("siteTitle", siteVo.get(0).getTitle());
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello()
	{
		return "<h1> 안녕하세요</h1>";
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public JSONResult hello2()
	{
		return JSONResult.success(new UserVo());
	}
	
}
