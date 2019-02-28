<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!-- 메세지 form 태그 spring message보다 편함 -->​​


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
				<form:form modelAttribute="boardVo" class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/reply?no=${param.no}&page=${param.page}&kwd=${param.kwd}">
					
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td>
								<form:input path="title" value="${boardVo.title }"/>
								<p style = "color: red; padding: 0; margin: 0; text-align: left">
									<form:errors path="title"/>
								</p>	
							</td>
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
						<a href="${pageContext.servletContext.contextPath }/board/view?boardNo=${param.no}&page=${param.page}&kwd=${param.kwd}">취소</a>
						<input type="submit" value="등록">
					</div>
				</form:form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>