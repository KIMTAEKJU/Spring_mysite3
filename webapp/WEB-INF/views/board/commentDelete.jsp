<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!-- 메세지 form 태그 spring message보다 편함 -->​​

<html>
<head>
<title>${siteTitle }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="guestbook" class="delete-form">
				<form:form modelAttribute="commentVo" method="post" action="${pageContext.servletContext.contextPath}/board/commentDelete?commentNo=${param.commentNo}&boardNo=${param.boardNo}&page=${param.page}&kwd=${kwd}">
					<input type="hidden" name="name" value="null"/>
					<input type="hidden" name="contents" value="null"/>
					
					<label>비밀번호</label>
					<form:input path="password"/>
					<p style = "color: red; padding: 0; margin: 0; text-align: left">
						<form:errors path="password"/>
					</p>	
					<input type="submit" value="확인">
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>