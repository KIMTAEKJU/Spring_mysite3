package com.douzone.mysite.controller;

import java.nio.channels.SeekableByteChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
					   HttpSession session)
	{
		System.out.println("page : " + page);
		System.out.println("kwd : " + kwd);
		Map<String, Object> map = boardService.list(page, kwd);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("BoardPagingFrameWorkVo", map.get("BoardPagingFrameWorkVo"));
		model.addAttribute("kwd", map.get("kwd"));
		model.addAttribute("session", session.getAttribute("authuser"));
		return "board/list";
	}
	
	@RequestMapping("/view")
	public String view(@ModelAttribute BoardVo bVo, 
					   @RequestParam(value = "page", required = false, defaultValue = "1") String page,
					   @RequestParam(value = "boardNo", required = false) String boardNo,
					   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
					   Model model, 
					   HttpSession session)
	{
		Map<String, Object> map = boardService.view(Long.parseLong(boardNo));
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("listComment", map.get("listComment"));
		model.addAttribute("session", session.getAttribute("authuser"));
		model.addAttribute("no", boardNo);
		model.addAttribute("page", page);
		return "board/view";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@RequestParam(value = "page", required = false) String page,
						@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						Model model)
	{
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		return "/board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo bVo, 
						@RequestParam(value = "page", required = false) String page,
						@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						HttpSession session)
	{
		System.out.println("!@#@!# kwd : " + kwd);
		return "redirect:/board/view?boardNo=" + 
				boardService.write(bVo, (UserVo)session.getAttribute("authuser")) + 
				"&page=" + page + 
				"&kwd=" + kwd;
	}
	
	@RequestMapping("/delete")
	public String delete(HttpSession session, 
						 @RequestParam(value = "page", required = false) String page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						 @ModelAttribute BoardVo bVo)
	{
		if( session.getAttribute("authuser") == null)
			return "redirect:/board";
		boardService.delete(bVo, (UserVo)session.getAttribute("authuser"));
		return "redirect:/board?&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping({"/commentWrite"})
	public String commentWrite(@ModelAttribute CommentVo cVo,
			 				   @RequestParam(value = "page", required = false) String page,
			 				   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
			 				   HttpSession session)
	{
		String password = null;
		if( session.getAttribute("authuser") != null)
			password = boardService.getPassword( ((UserVo)session.getAttribute("authuser")).getNo());
		
		System.out.println("password : " + password);
		
		
		System.out.println("cVo contents : " + cVo.getContents());
		System.out.println("cVo boardNo : " + cVo.getBoardNo());
		System.out.println("cVo name : " + cVo.getName());
		System.out.println("cVo userNo : " + cVo.getUserNo());

		boardService.commentWrite(cVo);
		System.out.println("cVo boardNo : " + cVo.getBoardNo());
		return "redirect:/board/view?boardNo=" + cVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping(value = "/commentModify", method = RequestMethod.GET)
	public String commentModify(Model model, 
								@ModelAttribute CommentVo cVo,
								@RequestParam(value = "page", required = false) String page,
				 				@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
								HttpSession session)
	{
		model.addAttribute("commentNo", cVo.getCommentNo());
		model.addAttribute("boardNo", cVo.getBoardNo());
		model.addAttribute("session", session.getAttribute("authuser"));
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		System.out.println("여기까진 오겠지");
		return "/board/commentModify";
	}
	
	@RequestMapping(value = "/commentModify", method = RequestMethod.POST)
	public String commentModify(@ModelAttribute CommentVo cVo, 
								@RequestParam(value = "page", required = false) String page,
								@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
								HttpSession session)
	{	
		boardService.commentModify(cVo, (UserVo)session.getAttribute("authuser"));
		return "redirect:/board/view?boardNo=" + cVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping(value = "/commentDelete", method = RequestMethod.GET )
	public String commentDelete(HttpSession session, 
								@ModelAttribute CommentVo cVo, 
								@RequestParam(value = "page", required = false) String page,
								@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
								Model model)
	{
//		UserVo sessionVo = (UserVo)session.getAttribute("authuser");
//		System.out.println("cVo password : " + cVo.getPassword());
//		System.out.println("cVo contents : " + cVo.getContents());

//		if( sessionVo == null || cVo.getPassword() != null)
//		{	
			model.addAttribute("commentNo", cVo.getCommentNo());
			model.addAttribute("boardNo", cVo.getBoardNo());
			model.addAttribute("page", page);
			model.addAttribute("kwd", kwd);

			return "board/commentDelete";
		//}
//		else
//		{
//			cVo.setUserNo(String.valueOf(sessionVo.getNo()));
//			boardService.commentDelete(cVo);
//			return "redirect:/board/view?boardNo=" + cVo.getBoardNo() + "&page=" + page + "&kwd=" + kwd;
//		}
	}
	
	@RequestMapping(value = {"/commentDelete/{commentNo}/{boardNo}/{userNo}", "/commentDelete/{commentNo}/{boardNo}"}, method = RequestMethod.POST )
	public String commentDelete(@ModelAttribute CommentVo cVo, HttpSession session)
	{	
		System.out.println("시발");
		System.out.println("cVo : " + cVo.getBoardNo());
		System.out.println("cVo : " + cVo.getCommentNo());

		boardService.commentDelete(cVo);
		return "redirect:/board/view/" + cVo.getBoardNo();
	}
	
	@RequestMapping(value = "/commentReply/{commentNo}/{boardNo}", method = RequestMethod.GET)
	public String commentReply(HttpSession session, Model model, @ModelAttribute CommentVo cVo)
	{
		if( session.getAttribute("authuser") == null)
		{
			model.addAttribute("commentNo", cVo.getCommentNo());
			model.addAttribute("boardNo", cVo.getBoardNo());
			return "board/commentReply";
		}
		else
		{
			UserVo sessionVo = (UserVo)session.getAttribute("authuser");
			model.addAttribute("commentNo", cVo.getCommentNo());
			model.addAttribute("boardNo", cVo.getBoardNo());
			model.addAttribute("session", sessionVo);
			return "board/commentReply";
		}
	}
	
	@RequestMapping(value = "/commentReply/{commentNo}/{boardNo}", method = RequestMethod.POST)
	public String commentReply(@ModelAttribute CommentVo cVo)
	{
		boardService.commentReply(cVo);
		return "redirect:/board/view/" + cVo.getBoardNo(); 
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@ModelAttribute BoardVo bVo,
						 @RequestParam(value = "page", required = false) String page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						Model model)
	{
		model.addAttribute("no", bVo.getNo());
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo bVo,
						 @RequestParam(value = "page", required = false) String page,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						 HttpSession session)
	{
		System.out.println("#$%#$%$#%#$% kwd : " + kwd);
		boardService.modify(bVo, session);
		return "redirect:/board/view?boardNo=" + bVo.getNo() + "&page=" + page + "&kwd=" + kwd;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(@ModelAttribute BoardVo bVo,
						@RequestParam(value = "page", required = false) String page,
						@RequestParam(value = "kwd", required = false) String kwd,
						Model model)
	{
		model.addAttribute("no", bVo.getNo());
		model.addAttribute("page", page);
		model.addAttribute("kwd", kwd);
		return "board/reply";
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo bVo, 
						@RequestParam(value = "page", required = false) String page,
						@RequestParam(value = "kwd", required = false) String kwd,
						Model model,
						HttpSession session)
	{		
		System.out.println("$@#$@#$@#$ kwd : " + kwd);
		return "redirect:/board/view?boardNo=" + boardService.reply(bVo, session) + "&page=" + page + "&kwd=" + kwd;
	}
	
	
	
}
