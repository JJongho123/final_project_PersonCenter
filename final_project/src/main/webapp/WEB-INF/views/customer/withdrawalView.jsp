<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}" />


<!doctype html>
<html lang="ko" class="is-pc">
<head>

<meta charset="utf-8">
<meta http-equiv="imagetoolbar" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>회원탈퇴</title>
<meta http-equiv="content-language" content="kr">

</head>
<script type="text/javascript">
   $(document)
         .ready(
               function() {

                  let cus_pw = $('#cus_id').val();

                  $("#delete")
                        .on(
                              "click",
                              function() {

                                 if ($("#cus_pw").val() == "") {
                                    alert("비밀번호를 입력해주세요");
                                    $("#cus_pw").focus();
                                    return false
                                 }

                                 if ($('input:checkbox[name=agree]')
                                       .is(":checked") == false) {

                                    alert("탈퇴약관을 동의해주세요");
                                    return false
                                 }
                                 
                                 $
                                       .ajax({
                                          url : "${pageContext.request.contextPath}/customer/passChk",
                                          type : "POST",
                                          dataType : "json",
                                          data : $("#deleteForm")
                                                .serializeArray(),
                                          success : function(data) {

                                             if (data == 0) {
                                                alert("비밀번호를 확인해주세요.");
                                                return;
                                             } else {
                                                if (confirm("탈퇴하시겠습니까?")) {
                                                   $(
                                                         "#deleteForm")
                                                         .submit();
                                                   //location.href = "${pageContext.request.contextPath}/customer/deleteCustomer";

                                                }

                                             }
                                          },
                                          error : function(
                                                request,
                                                status, error) {

                                             alert("status : "
                                                   + request.status
                                                   + ", message : "
                                                   + request.responseText
                                                   + ", error : "
                                                   + error);
                                             alert("요청실패");
                                          }
                                       })
                              });
               });
</script>
<body class="responsive is-round">

   <style>
.nt-container {
   max-width: 1100px;
}

.nt-container-wide {
   max-width: 1980px;
}

.boxed.wrapper, .boxed.wrapper #nt_menu_wrap.me-sticky nav {
   max-width: 1100px;
}

.no-responsive .wrapper {
   min-width: 1100px;
}
</style>
   <div id="nt_sticky_wrap"></div>


   <div id="nt_header">
      <div id="nt_body" class="nt-body">
         <div class="nt-container my-3 my-sm-4 px-0 px-sm-4 px-xl-0">
            <div class="register m-auto f-de">


               <form action="deleteCustomer" method="post" id="deleteForm"
                  name="deleteForm">

                  <input type="hidden" id="cus_id" name="cus_id" value="${loginid}">
                  <ul class="list-group mb-4">
                     <li></li>
                     <li></li>
                     <li>ㅤ</li>
                     <li>ㅤ</li>
                     <li><h5>탈퇴 약관</h5></li>
                     <li class="list-group-item pt-6 pt-sm-6">
                      <!--  약관동의서 시작-->


                        <div data-bs-spy="scroll" data-bs-target="#navbar-example2"
                           data-bs-root-margin="50px 50px -40%"
                           data-bs-smooth-scroll="true"
                           class="scrollspy-example bg-light p-3 rounded-2" tabindex="0">

                           <p>제1조 (계약 해지)
회원이 서비스 이용 계약을 해지 하고자 할 때는 개인정보수정의 '회원탈퇴' 메뉴에서 회원탈퇴를 신청하시면 됩니다.<br>
 탈퇴를 신청하시면 즉시 탈퇴처리가 완료되며, 탈퇴후 7일 동안은 회사에 다시 가입하실 수 없습니다.<br>
<br>
제2조 (탈퇴 회원의 개인정보 이용)<br>
1.회사는 회원정보를 회원이 탈퇴하는 시점으로부터 1년까지 보유할 수 있습니다.<br>
2.회사가 보관하고 있는 탈퇴 회원의 정보는 회원의 가입이력관리와 지적재산권 관리,개인정보보호를 위해서만 활용할 수 있습니다.<br>
3.단, 관계법령의 규정에 의하여 보존할 필요가 있는 경우 회사는 아래와 같이 관계법령에서 정한 일정한 기간 동안 회원정보를 보관합니다.<br>
-표시/광고에 관한 기록 : 6개월<br>
-계약 또는 청약철회 등에 관한 기록 : 5년<br>
-대금결제 및 재화 등의 공급에 관한 기록 : 5년<br>
제3조 (자격상실)<br>
다음 각 항의 사유에 해당하는 경우 회사는 사전 통보 없이, 이용계약을 해지하거나 기간을 정하여 서비스<br>
 이용을 중지 또는 이용계약 해지 후 무기한 가입제한 할 수 있습니다.<br>
<br>
1.가입시 또는 정보변경시 제6조 3항의 회원정보를 누락시키거나 허위 기재한 경우<br>
2.미풍양속을 저해하는 비속한 아이디, 필명, 별명을 사용한 경우<br>
3.타인의 아이디와 비밀번호, 주민등록번호 등 회원정보를 수집, 저장, 도용한 경우<br>
4.회사 임직원, 운영자 등을 포함한 타인을 사칭하는 행위<br>
5.회사, 다른 회원 또는 제3자의 지적재산권을 침해하는 경우<br>
6.사회의 안녕과 질서, 미풍양속을 해치는 행위를 하는 경우<br>
7.타인의 명예를 훼손, 모욕, 스톡 등 괴롭히거나 불이익을 주는 행위를 한 경우<br>
8.정보통신망에 장애를 일으킬 수 있는 행위를 하는 경우<br>
9.회사의 허락 없이 회사의 서비스를 이용해 영리행위를 하는 경우<br>
10.회사가 허락하지 않은 방법으로 회사가 운영, 관리하는 포인트를 취득한 경우<br>
11.전기통신기본법, 전기통신사업법, 정보통신 윤리강령, 정보통신 윤리위원회 심의규정,<br>
 프로그램 보호법 및 기타관련 법령과 약관이 금지하는 행위를 한 경우.
</p>
				 </div> 
				 
                        <!--  약관동의서 끝 -->
						<h1> </h1>
						<h1> </h1>
                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label">회원탈퇴동의서<strong
                              class="sr-only">필수</strong></label>
                              
                           <div class="col-sm-4">

                              <input type="checkbox" name="agree" value="동의">동의
                              &nbsp


                           </div>
                        </div>
                          <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_password">비밀번호<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="password" name="cus_pw" id="cus_pw">
                           </div>
                        
                        </div> 
                     </li>
                  </ul>
                  <div class="px-3 px-sm-0 mb-4">
                     <div class="row mx-n2">
                       <!--  <div class="col-6 order-2 px-2">
                           <button type="button" id="delete" name="delete"
                              class="btn btn-primary">회원탈퇴</button>
                        </div> -->
						<div class="col-6 order-2 px-2">
           					 <button type="button" name="delete" id="delete" class="btn btn-primary btn-lg btn-block en">회원탈퇴</button>
        				</div>


                        <div class="col-6 order-1 px-2">
                           <a href="${path}/" class="btn btn-basic btn-lg btn-block en">취소</a>
                        </div>
                     </div>
                  </div>
                  

               </form>

            </div>
         </div>
      </div>
      <!-- .nt-container -->
   </div>
   <!-- .nt-body -->
</body>
</html>
<%@ include file="../include/footer.jsp"%>