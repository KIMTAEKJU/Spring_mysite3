<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!-- 메세지 form 태그 spring message보다 편함 -->​​

<!DOCTYPE html>
<html>
<head>
<title>${siteTitle }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
	<!-- $(function() {
		
		$("#join-form").submit(function() {

			// 1. 이름 체크
			if ($("#name").val() == "") {
				alert("이름은 필수 입력 항목입니다");
				$("#name").focus();
				return false;
			}

			// 2-1. 이메일이 비어있는지 확인
			if ($("#email").val() == "") {
				alert("이메일은 필수 입력 항목입니다");
				$("#email").focus();
				return false;
			}

			// 2-2. 이메일 중복체크 유무
			if (!$("#img-checkemail").is(":visible")) {
				alert("이메일 중복 체크를 해야 합니다.");
				return false;
			}

			// 3. 비밀번호 확인
			if ($("input[type='password']").val() == "") {
				alert("비밀번호는 필수 입력 항목입니다");
				$("input[type='password']").focus();
				return false;
			}

			// 4. 약관동의
			if (!$("#agree-prov").is(":checked")) {
				alert("약관 동의를 해야 합니다.");
				return false;
			}

			return true;
		});
		

		
		$("#email").change(function() {
			$("#btn-checkemail").show();
			$("#img-checkemail").hide();
		});
		

		
		$("#btn-checkemail")
				.click(
						function() {

							var email = $("#email").val();
							if (email == "") {
								return;
							}

							$
									.ajax({
										url : "${pageContext.servletContext.contextPath }/user/api/checkemail?email="
												+ email,
										type : "GET",
										datatype : "json",
										data : "",

										success : function(response) {
											console.log(response);
											if (response.result == "fail") {
												console.error(response.message);
												return;
											}
											if (response.data == true) {
												alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요");
												$("#email").val("").focus();

												return;
											}

											// 사용가능한 이메일
											$("#btn-checkemail").hide();
											$("#img-checkemail").show();
										},
										error : function(xhr, status, e) {
											console.error(status + ":" + e);
										}
									})

						});
		

	}); -->
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form 
					modelAttribute="userVo" 
					id="join-form" 
					name="joinForm" 
					method="post"
					action="${pageContext.servletContext.contextPath }/user/join">
					<input type="hidden" name="a" value="join" /> 
					<label class="block-label" for="name"> 이름 </label> 
					<input id="name" name="name" type="text" value="${userVo.name }">
					<!-- form태그를 사용하려면 항상 userVo가 넘어와야함 -->
					
					<!-- 이 스프링태그가 쉬운태그가아님--> <!-- 굉장히 길기때문에 잘안씀? -->
					<spring:hasBindErrors name="userVo"> <!-- userVo라는 객체를 사용할수있음  -->

						<c:if test="${errors.hasFieldErrors('name') }">
							<p style = "padding: 0; text-align: left; color: red">
								<strong>
									<spring:message 
										code = "${errors.getFieldError( 'name' ).codes[0]}"
										text = "${errors.getFieldError( 'name' ).defaultMessage }"
									/> <!-- code에 해당되는 메세지가없으면 --> <!-- resouce파일이있으면 에러메세지를 수정할수있음 -->
						
									
								</strong>
							</p>
						</c:if>

					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label> 
					<form:input path="email"/> <!-- 자동으로 id, name을 email로 다만들어줌 form에있는 input태그다 -->
					
					<p style = "margin: 0; padding: 0; font-weight: bold; color: red; text-align: left">
						<form:errors path="email"/> <!-- 알아서 email 관련 에러들을 띄워줌 -->
					</p>
					
						<input
						id="btn-checkemail" type="button" value="이메일확인"> <img
						id="img-checkemail" style="width: 25px; display: none"
						src="${pageContext.servletContext.contextPath }/assets/images/check.png" />
					
					<!-- 
					<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('email') }">
							<strong style="color:red">
								<spring:message
									code = "${errors.getFieldError( 'email' ).codes[0] }"
									text = "${errors.getFieldError( 'email' ).defaultMessage }"
								/>
							</strong>
						</c:if>
					</spring:hasBindErrors> -->
					
					<!--  
					<spring:hasBindErrors name="userVo"> 

						<c:if test="${errors.hasFieldErrors('email') }">
							<strong>${errors.getFieldError( 'email' ).defaultMessage }</strong>
						</c:if>

					</spring:hasBindErrors>
					-->

					<label class="block-label">패스워드</label> 
					<form:input path="password"/>

					<fieldset>
						<legend>성별</legend>
					
						<label>여</label> 
						<input type="radio" name="gender" value="female" checked="checked"> 
						
						<label>남</label> 
						<input type="radio" name="gender" value="male">
						
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>