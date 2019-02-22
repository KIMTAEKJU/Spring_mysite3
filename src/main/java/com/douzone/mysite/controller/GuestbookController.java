package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController 
{
	@Autowired
	private GuestbookService gService;
	
	@RequestMapping({"", "/list"})
	public String list(Model model)
	{
		model.addAttribute("list", gService.list().get("list"));
		return "guestbook/list";
	}
	
	@RequestMapping("/write")
	public String write(@ModelAttribute GuestBookVo gVo)
	{
		gService.write(gVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@ModelAttribute GuestBookVo gVo, Model model)
	{
		model.addAttribute("no", gVo.getNo());
		return "guestbook/delete";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.POST)
	public String delete(@ModelAttribute GuestBookVo gVo)
	{
		gService.delete(gVo);
		return "redirect:/guestbook";
	}
}
