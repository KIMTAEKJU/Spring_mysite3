
<%@page import="com.douzone.mysite.vo.UserVo"%>
<%@page import="com.douzone.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div id="board" class="board-form">
			<c:forEach items="${list}" var="vo" varStatus = "status">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${vo.contents}
							</div>
						</td>
					</tr>
			
				</table>
				<div class="bottom">
					<c:if test="${!empty session }">
						<a href="${pageContext.servletContext.contextPath }/board/reply?no=${no}&page=${param.page}&kwd=${param.kwd}">답글달기</a>
					</c:if>
					<a href="${pageContext.servletContext.contextPath }/board?page=${param.page}&kwd=${param.kwd}">글목록</a>
					
					<!-- if (sessionVo != null && list.get(0).getUserNo() == sessionVo.getNo()) -->
					
					<c:if test="${!empty session && vo.userNo == session.no}">
						<a href="${pageContext.servletContext.contextPath }/board/modify?no=${no}&page=${param.page}&kwd=${param.kwd}">글수정</a>
					</c:if>
				</div>
				<table>
					<c:forEach items="${listComment}" var="vo" varStatus="status">
						<tr>
							<td style="padding-left:${20 * vo.depth}px">
								<c:choose>
									<c:when test="${vo.userNo != null }">
										<strong style="color:red;">${vo.name}</strong> ${vo.writeDate } 
									</c:when>
									
									<c:otherwise>
										<strong>${vo.name}</strong> ${vo.writeDate } 
									</c:otherwise>
								</c:choose>
								<a href="${pageContext.servletContext.contextPath }/board/commentModify?commentNo=${vo.no}&boardNo=${vo.boardNo}&page=${param.page}&kwd=${param.kwd}">수정</a> 
								
						
								<a href="${pageContext.servletContext.contextPath }/board/commentDelete?commentNo=${vo.no}&boardNo=${vo.boardNo}&page=${param.page}&kwd=${param.kwd}">삭제</a>
							
								<a href="${pageContext.servletContext.contextPath }/board/commentReply/${vo.no}/${vo.boardNo}">댓글 달기</a><br/>
								
								
								<span>${vo.contents}</span>
								<hr>
								
							</td>
						</tr>
						
					</c:forEach>
				</table>
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/commentWrite?boardNo=${no}&userNo=${session.no}&page=${param.page}&kwd=${param.kwd}">
		
					<table class="tbl-ex">
						<tr>
							<th colspan="2">댓글</th>
						</tr>
						<tr>
							<td class="label">닉네임</td>
							<c:choose>
								<c:when test="${session == null }">
									<td><input type="text" name="name" value=""></td>
								</c:when>
								
								<c:otherwise>
									<td><input type="text" name="name" value="${session.name }" readonly></td>
								</c:otherwise>
							</c:choose>
							
							<c:if test="${session == null }">
								<td class="label">비밀번호</td>
								<td><input type="text" name="password" value=""></td>
							</c:if>	
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="contents"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<input type="submit" value="등록">
					</div>
				</form>	
			</div>
		</div>
		</c:forEach>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>
