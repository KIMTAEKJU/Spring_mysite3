<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>${siteTitle }</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<style type="text/css">
		#dialog-delete-form p {
			padding: 10px;
			font-weight: bold;
			font-size: 1.0em;
		}
		
		#dialog-delete-form input[type="password"]{
			padding: 5px;
			outline: none;
			width: 180px;
			border: 1px solid #888;
		}
	</style>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript">
	
		// jquery plug-in
		(function($){
			$.fn.hello = function(){
				console.log( $(this).attr("id") + "----> hello");	
			};
		})(jQuery);
	
		var page = 0;
		var isEnd = false;
		
		var insertAndFormValidation = () => {
			
			// validate form data
			var name = $("#input-name").val();
			var password = $("#input-password").val();
			var message = $("#tx-content").val();
			
			if( name == "")
			{
				messageBox("이름", "이름은 필수 입력 항목입니다.");
				//alert("이름은 필수 입력 항목입니다.");
				return;
			}
			if( password == "")
			{
				messageBox("비밀번호", "비밀번호는 필수 입력 항목입니다.");
				return;
			}
			if( message == "")
			{
				messageBox("메세지", "메세지는 필수 입력 항목입니다.");
				return;
			}
			
			$.ajax({
				url: "/mysite3/guestbook/api/insert",
				type: "POST",
				dataType: "json",
				data: "name=" + name +
					  "&password=" + password +
					  "&message=" + message,
				
				success: function(response){
					console.log(response);
					render(response.data, true);
					
				},
				error: function(xhr, status, e) {
					console.error("error : " + e);
				}
			})
		};
		
		var messageBox = function(title, message){
			$("#dialog-message").attr({
				title: title
			});
			$("#dialog-message p").text(message);
			$("#dialog-message").dialog({
				modal: true,
				buttons: {
					"확인": function(){
						$(this).dialog("close");
					}
				},
			});
		};
		
		var render = function(vo, mode) {
			// 현업에 가면 이렇게안한다 -> template
			// ex) ejs, underscore, mustache
			
			
			var htmls = 
				"<li data-no='" + vo.no + "'>" +
					"<strong>" + vo.name + "</strong>" +
					"<p>" + vo.message.replace(/\n/g, "<br>") + "</p>" +
					"<strong></strong>" +
					"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
				"</li>"; 
			/*var htmls =*/ 
			
			if( mode == true)
			{
				$("#list-guestbook").prepend(htmls);			
			}
			else
			{
				$("#list-guestbook").append(htmls);			
			}
			
			$("#input-name").val('');
			$('#input-password').val('');
			$('#tx-content').val('');
		};
		
		
		var fetchList = function() { 
			
			if( isEnd == true)
			{
				return;
			}
			
			++page;
			$.ajax({
				url: "/mysite3/guestbook/api/list/" + page,
				type: "get",
				dataType: "json",
				data: "",
				success: function(response) {
					console.log(response);
					
					if( response.result == "fail")
					{
						console.warn(response.data);
						return;
					}
					
					console.log(response.data);
					
					// 페이지 끝을 검출
					if( response.data.length < 5)
					{
						isEnd = true;
						$("#btn-next").prop("disabled", true); // attr은 웹에세팅되지만 property는 웹에 세팅못하고 js로만
					}
					
					
					//rendering html을 만드는 작업
					$.each( response.data, function(index, vo) {
						//console.log(vo);
						render(vo, false);
					});
				},
				error: function(xhr, status, e) {
					console.error(status + ":" + e);
				}
			});
		};
		
		$(function (){
			var dialogDelete = $("#dialog-delete-form").dialog({
				autoOpen: false,
				modal: true,
				buttons: {
					"삭제": function(){
						console.log("ajax 삭제 작업!!");
						console.log($("#dialog-delete-form input[type='hidden']").val());
						
						$.ajax({ // 지워졌다는 결과를 보기위해  결과를 보고 ok하고 오면 close 비밀번호 틀리면 다시입력하게끔 해줘야함
							url: "/mysite3/guestbook/api/delete/" + $("#hidden-no").val() + "/" + $("#password-delete").val(),
							type: "get",
							dataType: "json",
							data: "",
							success: function(response) {
								console.log(response);
								console.log(response.data);
								
								if( response.data != -1)
								{
									console.log("삭제되었음!!");
									$("#list-guestbook li[data-no='" + response.data +"']").remove();
									$("#dialog-delete-form input[type='password']").val('');
									dialogDelete.dialog("close");
								}
								else
								{
									console.log("비밀번호 틀림");
									console.log($(".error").attr("style", "display:block").html());
									$("#dialog-delete-form input[type='password']").val('');

								}
							},
							error: function(xhr, status, e) {
								console.error("error:" + e);								
							}
						});
					},
					
					"취소": function() {
						dialogDelete.dialog("close");
						console.log("ajax 삭제 작업");
					}
				},
				close: function(){
					console.log("close시 뒤처리..");
					
					$(".error").attr("style", "display:none");
				},
			
			});
			
			// 라이브 이벤트(live event) 미래에 생길지 모르는걸 미리 이벤트 등록?  굉장히 중요한 개념
			//element에 걸어놓을수없으니 일단 document에 걸어놓음  element와 함수를 전해주고 이 element에 무슨일일어나면 함수 실행시켜달라고 전달
			
			$(document).on("click", "#list-guestbook li a", function(event) {
				event.preventDefault();
				console.log("clicked!!" + $(this).data("no"));
				$("#hidden-no").attr("value",  $(this).data("no"));
				
				dialogDelete.dialog("open");
			}); 
					
			$("#btn-next").click( function() {
				$(this).hello();
				fetchList();
			});
			
			fetchList(); // 최초 리스트 가져오기
		
			// 메세지 등록 폼 submit 이벤트 처리
			$("#add-form").submit( function(event) {
				
				// submit의 기본동작(post)
				// 막아야 한다.
				
				event.preventDefault();
				insertAndFormValidation();
			});
			
			// 스크롤 이벤트
			$(window).scroll( function(){
				var $window = $(this);'                                              '
				var scrollTop = $window.scrollTop();
				var windowHeight = $window.height();
				var documentHeight = $(document).height();
				
				//console.log(scrollTop + ":" + windowHeight + ":" + documentHeight);
				
				if( scrollTop + windowHeight + 10 > documentHeight)
				{
					fetchList();
				}
			});
		});	
		
	</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
		
			<div id="guestbook">
			
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				
				<ul id="list-guestbook"></ul>
				
				<button id="btn-next">다음</button>
				
			</div>
			
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p> <!-- 비밀번호가 틀리면 보여줘야함 -->
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>		
							
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>