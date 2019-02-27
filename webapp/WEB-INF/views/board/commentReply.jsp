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
			
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/commentReply/${commentNo }/${boardNo }">
					
					<table class="tbl-ex">
						<tr>
							<th colspan="2">댓글달기</th>
						</tr>
						<tr>
							<td class="label">닉네임</td>
							<c:choose>
								<c:when test="${session == null }">
									<td><input type="text" name="name" value="" ></td>
								</c:when>
								
								<c:otherwise>
									<td>${session.name }</td>
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
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>