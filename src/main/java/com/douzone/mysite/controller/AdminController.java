package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Auth(Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController 
{
	@Autowired
	private SiteService siteService;

	//@Auth(Role.ADMIN) // 여기서 이렇게 테스트해보고 어느정도 되면 클래스에다 달아줌
	@RequestMapping({"", "/main"})
	public String main(Model model)
	{
		model.addAttribute("siteVo", siteService.getSite().get(0));
		return "admin/main";
	}
	
	//@Auth(Role.ADMIN)
	@RequestMapping("/board")
	public String board()
	{
		return "admin/board";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping("/main/update")
	public String update(@ModelAttribute SiteVo vo)
	{
		siteService.update(vo);
		return "redirect:/admin";
	}
}
