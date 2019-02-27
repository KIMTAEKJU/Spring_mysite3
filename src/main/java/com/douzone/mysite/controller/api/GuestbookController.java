package com.douzone.mysite.controller.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestBookVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;


@Controller("GuestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController 
{
	@Autowired
	com.douzone.mysite.service.api.GuestbookService guestbookService;
	
	@RequestMapping("")
	public String AjaxList()
	{
		return "guestbook/index-ajax";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONResult insert(@ModelAttribute GuestBookVo guestbookVo) throws UnsupportedEncodingException, JsonProcessingException, IOException
	{
		//System.out.println("guestbookVo name : " + guestbookVo.getName());
		//System.out.println("guestbookVo password : " + guestbookVo.getPassword());
		//System.out.println("guestbookVo message : " + guestbookVo.getMessage());

		long no = guestbookService.write(guestbookVo);
		//System.out.println("no : " + no);
		
		if( no != 0)
		{
			guestbookVo = guestbookService.ajaxSelect(no);
			//System.out.println("guestbookVo : " + guestbookVo);
		
			return JSONResult.success(guestbookVo);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/delete/{no}/{password}")
	public JSONResult delete(@ModelAttribute GuestBookVo guestbookVo)
	{
		System.out.println("guestbookVo no : " + guestbookVo.getNo());
		System.out.println("guestbookVo password : " + guestbookVo.getPassword());
		
		if( guestbookService.delete(guestbookVo) == 0)
		{
			return JSONResult.success(-1);
		}
		
		return JSONResult.success(guestbookVo.getNo());
	}
	
	@ResponseBody
	@RequestMapping("/list/{page}")
	public JSONResult list(@PathVariable("page") String page)
	{
		//System.out.println("page : " + page);
		
		return JSONResult.success(guestbookService.getTimelineSelect(page));
	}
}
