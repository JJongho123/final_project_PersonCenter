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
<title>회원 가입</title>
<meta http-equiv="content-language" content="kr">

</head>
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

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

</style>

<%--아이디 중복확인--%>

<script> 
$(function(){
   
   $('#cus_id').keyup(function(){
      let cus_id = $('#cus_id').val();
         
      $.ajax({
         type:"post",
         url:"${pageContext.request.contextPath}/customer/joinIdCheck",
         data : {"cus_id":cus_id},
         dataType : "json",
            
         success : function(result){
            if(result == 1){
               $("#idcheck").html('이미 사용중인 아이디입니다.');
               $("#idcheck").attr('color','#dc3545');
            } else{
               $("#idcheck").html('사용할 수 있는 아이디입니다.');
               $("#idcheck").attr('color','#2fb380');
            } 
         },
            error: function( request, status, error ){

                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
               alert("요청실패");
               }
      });
   });
});
</script>

<!-- 비밀번호 중복확인 -->
<script>
$(function(){

      //비밀번호 확인
         $('#cus_pwC').blur(function(){
            if($('#cus_pw').val() != $('#cus_pwC').val()){
                if($('#cus_pwC').val()!=''){
                alert("비밀번호가 일치하지 않습니다.");
                    $('#cus_pwC').val('');
                   
                }
             }
         });        
      });
</script>

   <div id="nt_sticky_wrap"></div>

   <div id="nt_header">
      <div id="nt_body" class="nt-body">
         <div class="nt-container my-3 my-sm-4 px-0 px-sm-4 px-xl-0">
            <div class="register m-auto f-de">

               <form action="join" method="post" enctype="multipart/form-data" onsubmit="return checkForm();">
                  <ul class="list-group mb-4">
                     <li></li>
                     <li></li>
                     <li></li>
                     <li><h5></h5></li>
                     <li><h5>이용정보 입력</h5></li>
                     <li class="list-group-item pt-3 pt-sm-4">
                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_id">아이디<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="text" name="cus_id" value="" id="cus_id" 
                                 class="form-control required" minlength="3" maxlength="20">
                              
                              <font id="idcheck" size="2"></font>
                           </div>
                           <div class="col-sm-6">
                              <p class="form-control-plaintext f-de text-muted pb-0">
                                 영문자, 숫자, _ 만 입력 가능. 최소 3자이상 입력 가능
                              <div id="msg_mb_id"></div>
                              </p>
                           </div>
                        </div>

                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_password">비밀번호<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="password" name="cus_pw" id="cus_pw" required
                                 class="form-control required" class=".pw" minlength="3" maxlength="20">
                           </div>
                           <label class="col-sm-2 col-form-label" for="reg_mb_password_re">비밀번호
                              확인<strong class="sr-only">필수</strong>
                           </label>
                           <div class="col-sm-4">
                              <input type="password" name="cus_pwC" id="cus_pwC" required
                                 class="form-control required" class=".pw" minlength="3" maxlength="20">
                           </div>
                        </div>

                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_nick">이름<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="text" name="cus_nickname" id="cus_nickname"
                                 required class="form-control nospace required" maxlength="20">
                           </div>
                           <div class="col-sm-6">
                              <p class="form-control-plaintext f-de text-muted pb-0">
                                 공백없이 한글,영문,숫자만 가능 (한글2자,영문4자 이상 가능)</p>
                           </div>
                           <div class="col-sm-10 offset-sm-2">
                              <div id="msg_mb_nick"></div>
                              <p class="form-control-plaintext f-de pb-0">
                                 닉네임을 변경하면 앞으로 <b>60</b>일 이내에는 재변경을 할 수 없습니다.
                              </p>
                           </div>

                        </div>
                        
                        
                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label" >프로필사진</label>
                           <div class="col-sm-4">
                              <input type="file" name="upload" class="form-control required" >
                           </div>
                           
                        </div>
                        
                        
                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label">성별<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="radio" name="cus_gender" value="M" checked>남
                              &nbsp; <input type="radio" name="cus_gender" value="F">여

                           </div>
                        </div>

                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label">질문<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <select id="cus_question" name="cus_question">
                                 <option value="1">가장 좋아하는 장소는?</option>
                                 <option value="2">가장 좋아하는 동물은?</option>
                                 <option value="3">가장 좋아하는 음식은?</option>
                              </select>
                           </div>
                           <div class="col-sm-4">
                              <p class="form-control-plaintext f-de text-muted pb-0">
                                 비밀번호를 찾을때 사용됩니다</p>
                           </div>
                        </div>

                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label">답변<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="text" class="form-control" placeholder="질문에 대한 답"
                                 id="cus_answer" name="cus_answer" required>
                           </div>
                        </div>
                  
                        <div class="form-group row">
                           <label class="col-sm-2 col-form-label">계좌<strong
                              class="sr-only">필수</strong></label>
                             
                              &nbsp;&nbsp;&nbsp;<select name="cus_bank" id="cus_bank">
                               <option value="">선택</option>
                               <option value="농협">농협</option>
                               <option value="국민">국민</option>
                               <option value="기업">기업</option>
                               <option value="수협">수협</option>
                               <option value="신한">신한</option>
                         </select>

                         <div class="col-sm-4">
                              <input type="number" name="cus_account" id="cus_account"
                                 required class="form-control nospace required" style="width:272; height:40 ;" maxlength="20" placeholder="- 빼고 숫자로만 작성해주세요.">
                            </div>
                           </div>

                           <div class="form-group row">
                                 <label class="col-sm-2 col-form-label" for="reg_mb_password">우편번호<strong
                                    class="sr-only">필수</strong></label>
                              <div class="col-sm-4">
                                <input placeholder="우편번호" name="cus_zip_code" id="cus_zip_code" class="form-control" style="width:150; height:40 ;" readonly="readonly">
                              </div>
                              <div class="col-sm-4">
                                <input type="button"  style="float: left;" class="btn btn-primary"  onclick="execPostCode();" value="우편번호"/>
                              </div>
                           </div>
                           
                           <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_password_re">주소
                              <strong class="sr-only">필수</strong>
                           </label>
                           <div class="col-sm-4">
                              <input placeholder="주소" name="cus_addr" id="cus_addr" class="form-control" style="width:343; height:40 ;" readonly="readonly">
                           </div>
                           </div>
                           
                    
                 
                         <!-- 주소 API 스크립트 -->
                         <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
                         <script src="/resources/js/addressapi.js"></script>
                           
                         <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_nick">전화번호<strong
                              class="sr-only">필수</strong></label>
                           <div class="col-sm-4">
                              <input type="number" name="cus_tel" id="cus_tel"
                                 required class="form-control nospace required" maxlength="11" placeholder="- 빼고 숫자로만 작성해주세요.">
                           </div>
                         </div>

							 <input type="hidden" name="cus_grade" id="cus_grade" value="👶씨앗회원">
						
                     </li>
                  </ul>
                  <div class="px-3 px-sm-0 mb-4">
                     <div class="row mx-n2">
                        <div class="col-6 order-2 px-2">
                           <button type="submit" value="Join Us" id="btn_submit"
                              class="btn btn-primary btn-lg btn-block en">회원가입</button>
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
   <script>
   function execPostCode() {
       new daum.Postcode({
           oncomplete: function(data) {
              // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

              // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
              // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
              var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
              var extraRoadAddr = ''; // 도로명 조합형 주소 변수

              // 법정동명이 있을 경우 추가한다. (법정리는 제외)
              // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
              if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                  extraRoadAddr += data.bname;
              }
              // 건물명이 있고, 공동주택일 경우 추가한다.
              if(data.buildingName !== '' && data.apartment === 'Y'){
                 extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
              }
              // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
              if(extraRoadAddr !== ''){
                  extraRoadAddr = ' (' + extraRoadAddr + ')';
              }
              // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
              if(fullRoadAddr !== ''){
                  fullRoadAddr += extraRoadAddr;
              }

              // 우편번호와 주소 정보를 해당 필드에 넣는다.
              console.log(data.zonecode);
              console.log(fullRoadAddr);
              
              
              $("[name=cus_zip_code]").val(data.zonecode);
              $("[name=cus_addr]").val(fullRoadAddr);
              
              /* document.getElementById('signUpUserPostNo').value = data.zonecode; //5자리 새우편번호 사용
              document.getElementById('signUpUserCompanyAddress').value = fullRoadAddr;
              document.getElementById('signUpUserCompanyAddressDetail').value = data.jibunAddress; */
          }
       }).open();
   }

   </script>
</body>
</html>
<%@ include file="../include/footer.jsp"%>