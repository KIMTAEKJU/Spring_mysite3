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
	<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript">
		$(function () {
		
			$("#join-form").submit( function() {
				
				// 1. 이름 체크
				if( $("#name").val() == ""){
					alert("이름은 필수 입력 항목입니다");
					$("#name").focus();
					return false;
				}
				
				// 2-1. 이메일이 비어있는지 확인
				if( $("#email").val() == ""){
					alert("이메일은 필수 입력 항목입니다");
					$("#email").focus();
					return false;
				}
				
				// 2-2. 이메일 중복체크 유무
				if( !$("#img-checkemail").is(":visible"))
				{
					alert("이메일 중복 체크를 해야 합니다.");
					return false;
				}
				
				// 3. 비밀번호 확인
				if( $("input[type='password']").val() == ""){
					alert("비밀번호는 필수 입력 항목입니다");
					$("input[type='password']").focus();
					return false;
				}
				
				// 4. 약관동의
				if( !$("#agree-prov").is(":checked"))
				{
					alert("약관 동의를 해야 합니다.");
					return false;
				}
				
				return true;
			});
			
			$("#email").change( function () {
				$("#btn-checkemail").show();
				$("#img-checkemail").hide();
			});
			
			$("#btn-checkemail").click( function() {

				var email = $("#email").val();
				if( email == "")
				{
					return;	
				}
				
				$.ajax({
					url: "${pageContext.servletContext.contextPath }/user/api/checkemail?email=" + email,
					type: "GET",
					datatype: "json",
					data: "",
					
					success: function (response) {
						console.log(response);
						if( response.result == "fail")
						{
							console.error(response.message);
							return;
						}
						if( response.data == true)
						{
							alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요");
							$("#email")
							.val("")
							.focus();
							
							return;
						}
						
						// 사용가능한 이메일
						$("#btn-checkemail").hide();
						$("#img-checkemail").show();
					},
					error: function (xhr, status, e) {
						console.error(status + ":" + e);
					}
				})
				
				
			});
			
		});
	
		
		
	</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="<%=request.getContextPath() %>/user/join">
					<input type="hidden" name="a" value="join"/>
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input id="btn-checkemail" type="button" value="이메일확인">
					<img id="img-checkemail" style="width:25px; display:none" src="${pageContext.servletContext.contextPath }/assets/images/check.png"/>
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>