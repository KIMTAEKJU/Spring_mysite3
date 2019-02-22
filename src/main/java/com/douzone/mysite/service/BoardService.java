package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.BoardPagingFrameWorkDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.BoardPagingFrameWorkVo;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService 
{
	@Autowired
	private BoardPagingFrameWorkDao bpfd;
	@Autowired
	private BoardDao bDao;
	@Autowired
	private CommentDao cDao;
	@Autowired
	private UserDao uDao;

	//private String kwdAttr = "";

	public Map<String, Object> list(Integer paramPage, String paramKwd)
	{
		String kwd = paramKwd;
//
//		if( kwd == null)
//		{
//			kwd = kwdAttr;
//		}
//		else
//		{
//			kwdAttr = kwd;
//		}
		
		//if( paramPage == null)
			//paramPage = 1;

		kwd = kwd.replaceAll(" ", "");

		long totalCount = bpfd.getTotalCount(kwd);
		
		// 화면에 보여줄 게시물수
		int listCount = 5;

		// 필요한 총 페이지수
		long totalPage = (totalCount % listCount > 0) ?   
				(totalCount / listCount) + 1 : 
					totalCount / listCount;
		
		// 화면에 보여줄 페이지수
		int pageCount = 5; 

		// 현재 페이지

		// 값을 가져와서 체크
		int getPage = paramPage;
		System.out.println("getPage : " + getPage);

		// 범위를 벗어난값을 바로 잡아줌
		int page = (int) ((getPage < 1) ? 1 : (getPage > totalPage) ? totalPage : getPage);
		System.out.println("page : " + page);

		// 시작 페이지
		int startPage = (( (page-1) / pageCount) * pageCount) + 1;

		// 마지막 페이지
		int endPage = startPage + pageCount - 1;
		
		System.out.println("totalCount : " + totalCount);
		System.out.println("listCount : " + listCount);
		System.out.println("totalPage : " + totalPage);
		System.out.println("pageCount : " + pageCount);
		System.out.println("page : " + page);
		System.out.println("startPage : " + startPage);
		System.out.println("endPage : " + endPage);
		
		List<BoardVo> list = bDao.get(kwd, ((page - 1) * listCount) + 1, listCount);

		BoardPagingFrameWorkVo result = new BoardPagingFrameWorkVo();
		result.setTotalCount(totalCount);
		result.setListCount(listCount);
		result.setTotalPage(totalPage);
		result.setPageCount(pageCount);
		result.setGetPage(getPage);
		result.setPage(page);
		result.setStartPage(startPage);
		result.setEndPage(endPage);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("BoardPagingFrameWorkVo", result);
		map.put("kwd", kwd);

		return map;
	}

	public Map<String, Object> view(long no)
	{
		Map<String, Object> map = new HashMap<>();

		List<BoardVo> list = bDao.get(no);
		List<CommentVo> commentList = cDao.get(no);

		map.put("list", list);
		map.put("listComment", commentList);
		return map;
	}

	public String write(BoardVo bVo, UserVo uVo)
	{
		bDao.insert(bVo, uVo);
		return bDao.getMaxBoardNo();
	}

	public void delete(BoardVo bVo, UserVo uVo)
	{
		bVo.setUserNo(uVo.getNo());
		System.out.println("bVo no : " + bVo.getNo());
		System.out.println("bVo userNo : " + bVo.getUserNo());
		System.out.println("삭제 : " + bDao.delete(bVo));
	}

	public void commentWrite(CommentVo cVo)
	{
		if( cVo.getUserNo().equals(""))
			cVo.setUserNo(null);
		System.out.println("%$#%@^#@^ cVo name : " + cVo.getName());
		cDao.insert(cVo);
	}

	public void commentModify(CommentVo cVo, UserVo uVo)
	{
		if( uVo == null)
			cVo.setUserNo(null);
		else
			cVo.setUserNo( String.valueOf(uVo.getNo()));
		
		cDao.update(cVo);
	}

	public void commentDelete(CommentVo cVo)
	{
		cDao.delete(cVo);
	}

	public void commentReply(CommentVo cVo)
	{
		List<CommentVo> list = cDao.get(String.valueOf(cVo.getCommentNo()));

		System.out.println("list size: " + list.size());

		long gNo = list.get(0).getgNo(); // g_no
		long oNo = list.get(0).getoNo(); // o_no
		long depth = list.get(0).getDepth(); // depth
		
		CommentVo vo = new CommentVo();
		vo.setgNo(gNo);
		vo.setoNo(oNo);
		vo.setDepth(depth);
		
		String check = cDao.check(vo);
		
		if (check == null) // 맨밑으로 댓글
		{
			oNo = cDao.getMaxONO(gNo).get(0).getoNo(); // 가장 큰 oNo를 받아서 저장
		}
		else // 대댓글
		{
			oNo = Long.parseLong(check);
			System.out.println("Update : " + cDao.updateONOGreaterThanEqual(vo));
		}

		depth++;

		cVo.setgNo(gNo);
		cVo.setoNo(oNo);
		cVo.setDepth(depth);

		cDao.insertReplyComment(cVo);
	}

	public void modify(BoardVo bVo, HttpSession session)
	{
		bDao.update(bVo, ((UserVo)session.getAttribute("authuser")).getNo());
	}

	public String reply(BoardVo bVo, HttpSession session)
	{		
		List<BoardVo> list = bDao.get(String.valueOf(bVo.getNo()));
		
		long gNo = list.get(0).getgNo();
		long oNo = list.get(0).getoNo() + 1; // oNo + 1
		long depth = list.get(0).getDepth() + 1; // depth + 1

		bVo.setgNo(gNo);
		bVo.setoNo(oNo);
		bVo.setDepth(depth);
		bVo.setUserNo( ((UserVo)session.getAttribute("authuser")).getNo() ); 

		bDao.update(bVo);
		bDao.insert(bVo);
		return bDao.getMaxBoardNo();
	}
	
	public String getPassword(long userNo)
	{
		return uDao.getPassword(userNo);		
	}
}
