<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!-- 메세지 form 태그 spring message보다 편함 -->​​

<!doctype html>
<html>
<head>
<title>${siteTitle }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form:form 
							modelAttribute="userVo" 
							id="login-form" 
							name="loginform" 
							method="post" 
							action="${pageContext.servletContext.contextPath }/user/auth">
					
					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
									
					<label class="block-label" >패스워드</label>
					<form:input path="password"/>
					
					<c:set var = "fail" value= "fail"></c:set>
					<c:if test='${fail == param.result}'>
							<p>
								로그인이 실패 했습니다.
							</p>
					</c:if>		
					
					<input type="submit" value="로그인">
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>