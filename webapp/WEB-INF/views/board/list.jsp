<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="com.douzone.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>${siteTitle }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board?page=1" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd}">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${list}" var="vo" varStatus = "status">
					
							<tr>
								<td>${(status.index + (BoardPagingFrameWorkVo.page - 1) * BoardPagingFrameWorkVo.listCount) + 1} </td>
								<td style="padding-left:${20 * vo.depth}px">
								
									<c:if test="${vo.oNo != 1 }">
										<img src="/mysite2/assets/images/reply.png"/>
									</c:if>
								
								<a href="${pageContext.servletContext.contextPath }/board/view?boardNo=${vo.no}&page=${BoardPagingFrameWorkVo.page}&kwd=${kwd}">${vo.title} <span style="color: red;">(${vo.commentCount})</span></a></td>
								
								<td>${vo.name}</td>
								<td>${vo.hit}</td>
								<td>${vo.write_Date}</td>
								<td><a href="${pageContext.servletContext.contextPath }/board/delete?no=${vo.no }&page=${BoardPagingFrameWorkVo.page}&kwd=${param.kwd}" class="del"></td>
							</tr>
					</c:forEach>
				</table>
				
				<div class="pager">
					<ul>
					<li><a href="${pageContext.servletContext.contextPath }/board?page=1&kwd=${kwd}">◁◀ </a></li>
			
					<li><a href="${pageContext.servletContext.contextPath }/board?page=${BoardPagingFrameWorkVo.page - 1}&kwd=${kwd}">◀</a></li>
					
						<c:choose>
							<c:when test="${BoardPagingFrameWorkVo.endPage < BoardPagingFrameWorkVo.totalPage}">
								<c:set var = "end" value="${BoardPagingFrameWorkVo.endPage }" />
							</c:when>
							
							<c:otherwise>
								<c:set var = "end" value="${BoardPagingFrameWorkVo.totalPage }" />
							</c:otherwise>
						</c:choose>
												
						<c:forEach var="i" begin="${BoardPagingFrameWorkVo.startPage}" end="${end}" step="1" >
						
							<c:choose>
								<c:when test="${i == BoardPagingFrameWorkVo.page }">
									<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?page=${i}&kwd=${kwd}">${i}</a></li>	
								</c:when>
								
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/board?page=${i}&kwd=${kwd}">${i}</a></li>
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					
					<li><a href="${pageContext.servletContext.contextPath }/board?page=${BoardPagingFrameWorkVo.page + 1}&kwd=${kwd}">▶</a></li>
				
					<li><a href="${pageContext.servletContext.contextPath }/board?page=${BoardPagingFrameWorkVo.totalPage}&kwd=${kwd}"> ▶▷</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
			
				<c:if test="${!empty authuser }">
						<a href="${pageContext.servletContext.contextPath }/board/write?page=${BoardPagingFrameWorkVo.page}&kwd=${kwd}" id="new-book">글쓰기</a>
				</c:if>
				
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>	
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>