package com.douzone.mysite.controller;

import java.nio.channels.SeekableByteChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController 
{
	@Autowired
	private BoardService boardService;

	@RequestMapping({""})
	public String list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
					   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
					   Model model, 
					   @AuthUser UserVo userVo)
	{
		System.out.println("page : " + page);
		System.out.println("kwd : " + kwd);
		Map<String, Object> map = boardService.list(page, kwd);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("BoardPagingFrameWorkVo", map.get("BoardPagingFrameWorkVo"));
		model.addAttribute("kwd", map.get("kwd"));
		model.addAttribute("session", userVo);
		return "board/list";
	}
	
	@RequestMapping(value = "/view")
	public String view(@AuthUser UserVo userVo,
					   @ModelAttribute CommentVo commentVo,
					   Model model)
	{
		Map<String, Object> map = boardService.view(commentVo.getBoardNo());

		model.addAttribute("list", map.get("list"));
		model.addAttribute("listComment", map.get("listComment"));
		model.addAttribute("session", userVo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Model model,
						@ModelAttribute BoardVo bVo)
	{
		return "/board/write";
	}
	
	@Transactional
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo userVo,
						@RequestParam(value = "page", required = false) String page,
						@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						@ModelAttribute @Valid BoardVo bVo, 
						BindingResult result,
						Model model)
	{
		if( result.hasErrors())
		{
			model.addAllAttributes(result.getModel());
			return "/board/write";
		}
		//System.out.println("!@#@!# kwd : " + kwd);
		return "redirect:/board/view?boardNo=" + 
				boardService.write(bVo, userVo) + 
				"&page=" + page + 
				"&kwd=" + kwd;
	}
	
	@Auth
	@RequestMapping("/delete")
	public String delete(@AuthUser UserVo userVo, 
						 @RequestParam(value = "page", required = false) String page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						 @ModelAttribute BoardVo bVo)
	{
		if( userVo == null)
			return "redirect:/board";
		boardService.delete(bVo, userVo);
		return "redirect:/board?&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping({"/commentWrite"})
	public String commentWrite(@AuthUser UserVo userVo,
							   @ModelAttribute @Valid CommentVo commentVo,
							   BindingResult result,
							   Model model,
			 				   @RequestParam(value = "page", required = false) String page,
			 				   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd)
	{
//		System.out.println("cVo contents : " + cVo.getContents());
//		System.out.println("cVo boardNo : " + cVo.getBoardNo());
//		System.out.println("cVo name : " + cVo.getName());
//		System.out.println("cVo userNo : " + cVo.getUserNo());
//		System.out.println("cVo password : " + cVo.getPassword());
		
		if( result.hasErrors())
		{
			model.addAllAttributes(result.getModel());
			
			Map<String, Object> map = boardService.view(commentVo.getBoardNo());

			model.addAttribute("list", map.get("list"));
			model.addAttribute("listComment", map.get("listComment"));
			model.addAttribute("session", userVo);
			return "board/view";
		}
		
		boardService.commentWrite(commentVo, userVo);
		//System.out.println("cVo boardNo : " + cVo.getBoardNo());
		return "redirect:/board/view?boardNo=" + commentVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd;
		//return "redirect:/board/view/"+cVo.getBoardNo();
	}
	
	@RequestMapping(value = "/commentModify", method = RequestMethod.GET)
	public String commentModify(Model model, 
								@ModelAttribute CommentVo cVo,
								@AuthUser UserVo userVo)
	{
		model.addAttribute("session", userVo);
		return "/board/commentModify";
	}
	
	@RequestMapping(value = "/commentModify", method = RequestMethod.POST)
	public String commentModify(@AuthUser UserVo userVo,
								@ModelAttribute @Valid CommentVo commentVo,
								BindingResult result,
								Model model,
								@RequestParam(value = "page", required = false) String page,
								@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd)
	{	
		if( result.hasErrors())
		{
			System.out.println("여기~");
			model.addAllAttributes(result.getModel());
			model.addAttribute("session", userVo);
			return "/board/commentModify";
		}
		
		boardService.commentModify(commentVo, userVo);
		return "redirect:/board/view?boardNo=" + commentVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping(value = "/commentDelete", method = RequestMethod.GET )
	public String commentDelete(@ModelAttribute CommentVo cVo)
	{
		return "board/commentDelete";
	}
	
	@RequestMapping(value = "/commentDelete", method = RequestMethod.POST )
	public String commentDelete(@AuthUser UserVo userVo,
								@ModelAttribute @Valid CommentVo commentVo,
								BindingResult result,
								Model model,
								@RequestParam(value = "page", required = false) String page,
								@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd)
	{	
		System.out.println("commetVo password : " + commentVo.getPassword());
		
		if( result.hasErrors() )
		{
			model.addAllAttributes(result.getModel());
			return "/board/commentDelete";
		}
		
		System.out.println("삭제");
		boardService.commentDelete(commentVo);
		return "redirect:/board/view?boardNo=" + commentVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping(value = "/commentReply", method = RequestMethod.GET)
	public String commentReply(@AuthUser UserVo userVo, 
							   Model model, 
							   @ModelAttribute CommentVo cVo)
	{
		if( userVo == null)
		{
			return "board/commentReply";
		}
		else
		{
			model.addAttribute("session", userVo);
			return "board/commentReply";
		}
	}
	
	@RequestMapping(value = "/commentReply", method = RequestMethod.POST)
	public String commentReply( @AuthUser UserVo userVo,
								@RequestParam(value = "page", required = false) String page,
			   					@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
								@ModelAttribute @Valid CommentVo commentVo,
								BindingResult result,
								Model model)
			  				  
	{
		if( result.hasErrors())
		{
			model.addAllAttributes(result.getModel());
			model.addAttribute("session", userVo);
			return "/board/commentReply";
		}
		
		boardService.commentReply(commentVo, userVo);
		return "redirect:/board/view?boardNo=" + commentVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd; 
	}
	
	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@ModelAttribute BoardVo bVo)
	{
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@AuthUser UserVo userVo,
						 @ModelAttribute @Valid BoardVo bVo,
						 BindingResult result,
						 @RequestParam(value = "page", required = false) String page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						 Model model)
	{
		if( result.hasErrors())
		{
			model.addAllAttributes(result.getModel());
			return "/board/modify";
		}
		
		boardService.modify(bVo, userVo);
		return "redirect:/board/view?boardNo=" + bVo.getNo() + "&page=" + page + "&kwd=" + kwd;
	}
	
	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(@ModelAttribute BoardVo bVo)
	{
		return "board/reply";
	}
	
	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@AuthUser UserVo userVo,
						@ModelAttribute @Valid BoardVo bVo, 
						BindingResult result,
						@RequestParam(value = "page", required = false) String page,
						@RequestParam(value = "kwd", required = false) String kwd,
						Model model)
	{		
		if( result.hasErrors())
		{
			model.addAllAttributes(result.getModel());
			return "/board/reply";
		}
		
		//System.out.println("$@#$@#$@#$ kwd : " + kwd);
		return "redirect:/board/view?boardNo=" + boardService.reply(bVo, userVo) + "&page=" + page + "&kwd=" + kwd;
	}
	
	
	
}
