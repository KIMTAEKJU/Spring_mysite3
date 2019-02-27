package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
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
	private FileuploadService fileuploadService;

	@Autowired
	private SiteService siteService;

	//@Auth(Role.ADMIN) // 여기서 이렇게 테스트해보고 어느정도 되면 클래스에다 달아줌
	@RequestMapping(value = {"", "/main"}, method = RequestMethod.GET)
	public String main(Model model)
	{
		model.addAttribute("siteVo", siteService.getSite().get(0));
		return "admin/main";
	}

	//@Auth(Role.ADMIN)
//	@RequestMapping(value = "/main", method = RequestMethod.POST )
//	public String main(@ModelAttribute SiteVo vo)
//	{
//		System.out.println("siteVo profile : " + vo.getProfile());
//		siteService.update(vo);
//		return "redirect:/admin";
//	}

	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public String main(@ModelAttribute SiteVo siteVo,
			@RequestParam(value = "upload-profile") MultipartFile multipartFile)
	{
		String profile = fileuploadService.restore(multipartFile);
		System.out.println("profile : " + profile);
		siteVo.setProfile(profile);

		siteService.update(siteVo);

		return "redirect:/admin/main";
	}

	//@Auth(Role.ADMIN)
	@RequestMapping("/board")
	public String board()
	{
		return "admin/board";
	}

}
