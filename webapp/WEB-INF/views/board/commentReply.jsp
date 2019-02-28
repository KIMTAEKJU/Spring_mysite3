<%@page import="com.douzone.mysite.vo.UserVo"%>
<%@page import="com.douzone.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!-- 메세지 form 태그 spring message보다 편함 -->​​


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
			
				<form:form modelAttribute="commentVo" class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/commentReply?commentNo=${param.commentNo }&boardNo=${param.boardNo }&userNo=${session.no}&page=${param.page}&kwd=${param.kwd}">
					
					<table class="tbl-ex">
						<tr>
							<th colspan="2">댓글달기</th>
						</tr>
						<tr>
							<td class="label">닉네임</td>
							<c:choose>
								<c:when test="${session == null }">
									<td>
										<form:input path="name"/>
										<p style = "color: red; padding: 0; margin: 0; text-align: left">
											<form:errors path="name"/>
										</p>
									</td>
								</c:when>
								
								<c:otherwise>
									<td><form:input path="name" value="${session.name }" readonly="true"/></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${session == null }">
									<td class="label">비밀번호</td>
									<td>
										<form:input path="password"/>
										<p style = "color: red; padding: 0; margin: 0; text-align: left">
											<form:errors path="password"/>
										</p>
									</td>
								</c:when>
								
								<c:otherwise>
									<input type="hidden" name="password" value="null"/>
								</c:otherwise>
							</c:choose>
							
							
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<form:textarea path="contents" id="content"/>
								<p style = "color: red; padding: 0; margin: 0; text-align: left">
									<form:errors path="contents"/>
								</p>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<input type="submit" value="등록">
					</div>
				</form:form>	
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>