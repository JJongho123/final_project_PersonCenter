<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메세지 보내기</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/clean-blog.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/write.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/read.css"
	rel="stylesheet">
</head>
<script>
	
	
	$(function(){
		$('#send').on('click', message);
	});
	function message(){
		$.ajax({
			url:"send",
			method:"post",
			data:{
				"cus_id":"${loginid}",
				"friend_id":"${trader.friend_id}",
				"message_title":$("#message_title").val(),
				"message":$("#message").val(),
				"board_num":${trader.board_num}
			},
			success:function(friend_id){
				alert(friend_id+"님에게 메세지를 전송하였습니다");
				history.go(-1);	
			}
		});
	}
	function closeWindow(){
		this.close();
	}
</script>


<body>


	<div id="nt_body" class="nt-body">
		<div class="nt-container px-0 px-sm-4 px-xl-0 pt-0 pt-sm-4">

			<!-- 메인 -->
		
				<div class="WritingCommerce">
					<div class="form_box">
						<h4 class="form_label">
							받는 사람<span role="img" aria-label="필수" class="validation">*</span>
						</h4>

					</div>
					<div class="form_box">
						<div class="input-group mb-3">

							<input type="text" class="form-control" id="cus_id" name="cus_id"
								placeholder="제목" required class="form-control"
								value="${trader.friend_id}"
								aria-label="Text input with dropdown button" readonly="readonly"
								style="width: 300px">
						</div>
						<br>
					</div>
					
					<div class="form_box">
						<h4 class="form_label">
							제목<span role="img" aria-label="필수" class="validation">*</span>
						</h4>
					</div>

					<div class="form_box">
						<div class="input-group mb-3">

							<input type="text" class="form-control" id="message_title" name="message_title"
								placeholder="제목" required class="form-control"
								aria-label="Text input with dropdown button" 
								style="width: 100px">
						</div>
						<br>
					</div>


					<div class="form_box">
						<h4 class="form_label">
							내용 입력<span role="img" aria-label="필수" class="validation">*</span>
						</h4>
					</div>
					<h1>ㅤ</h1>
					<textArea id="message" name="message" required
						class="form-control" rows="10"></textArea>
					<h1>ㅤ</h1>
					<input type="button"  id="send" class="btn btn-default"  value="➡️보내기"
						style="float: right;  border-radius: 10px">
					<h1>ㅤ</h1>
				</div>
			
		</div>
	</div>


	<script
		src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/vendor/popper/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/clean-blog.min.js"></script>

</body>

</html>
<%@ include file="../include/footer.jsp"%>
