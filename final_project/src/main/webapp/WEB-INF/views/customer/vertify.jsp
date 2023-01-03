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
<title>회원정보수정</title>
<meta http-equiv="content-language" content="kr">

<style>

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
</style>

</head>
<body class="responsive is-round">

	<script>
		$(function() {

			//비밀번호 확인
			$('#cus_pwC').blur(function() {
				if ($('#cus_pw').val() != $('#cus_pwC').val()) {
					if ($('#cus_pwC').val() != '') {
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

					<form action="vertifyUpdate" method="post"
						onsubmit="return checkForm();" enctype="multipart/form-data">
						<ul class="list-group mb-4">
							<li></li>
							<li></li>
							<li>ㅤ</li>
							<li>ㅤ</li>
							<li><h5> </h5></li>
							<li class="list-group-item pt-3 pt-sm-4">

								<div class="form-group row">
									<label class="col-sm-2 col-form-label" for="reg_mb_id">프로필사진</label>

									<img src="${pageContext.request.contextPath}/customer/download"
										class="rounded-circle" style="width: 50px; height: 50px; margin-left:20px;">
									
									<div class="col-sm-4" style="margin-left:50px;">
										<label class="col-sm-2 col-form-label" for="input-file"> <img src="https://cdn-icons-png.flaticon.com/128/8498/8498539.png"
										 style="cursor:hand; width: 30px; height: 30px;"> </label> <input type="file"
											name="upload" id="input-file" style="display: none; "><small><b>사진수정</b></small>
											
									</div>
									<div class="col-sm-4">
									
									</div>
								</div>



								<div class="form-group row">
									<label class="col-sm-2 col-form-label" for="reg_mb_id">아이디<strong
										class="sr-only">필수</strong></label>
									<div class="col-sm-4">
										<input type="text" name="cus_id" value="${customer.cus_id}"
											id="cus_id" required class="form-control required"
											readonly="readonly">
									</div>
									
								</div>

								<div class="form-group row">
									<label class="col-sm-2 col-form-label" for="reg_mb_password">비밀번호<strong
										class="sr-only">필수</strong></label>
									<div class="col-sm-4">
										<input type="password" name="cus_pw" id="cus_pw" required
											class="form-control required" minlength="3" maxlength="20">
									</div>
									<label class="col-sm-2 col-form-label" for="reg_mb_password_re">비밀번호
										확인<strong class="sr-only">필수</strong>
									</label>
									<div class="col-sm-4">
										<input type="password" name="cus_pwC" id="cus_pwC" required
											class="form-control required" minlength="3" maxlength="20">
									</div>
								</div>

								<div class="form-group row">
									<label class="col-sm-2 col-form-label" for="reg_mb_nick">이름<strong
										class="sr-only">필수</strong></label>
									<div class="col-sm-4">
										<input type="text" name="cus_nickname"
											value="${customer.cus_nickname}" id="cus_nickname" required
											class="form-control nospace required" maxlength="20">
									</div>
									<div class="col-sm-6">
										<p class="form-control-plaintext f-de text-muted pb-0">
											공백없이 한글,영문,숫자만 가능 (한글2자,영문4자 이상 가능)</p>
									</div>
									<div class="col-sm-10 offset-sm-2">
										<div id="msg_mb_nick"></div>
									</div>

								</div>
								<div class="form-group row">
									<label class="col-sm-2 col-form-label">성별<strong
										class="sr-only">필수</strong></label>
									<div class="col-sm-4">
										<input type="radio" name="cus_gender" value="M" checked>남
										&nbsp <input type="radio" name="cus_gender" value="F">여
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
                              <div class="col-sm-2">
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
                           
                         <div class="form-group row">
                           <label class="col-sm-2 col-form-label" for="reg_mb_password_re">
                         <strong class="sr-only">필수</strong>
                             </label>
                           <div class="col-sm-4">
                             <input placeholder="상세주소" name="cus_addr" id="cus_addr" class="form-control" style="width:343; height:40 ;" readonly="readonly">
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
							</li>
						</ul>
						<div class="px-3 px-sm-0 mb-4">
							<div class="row mx-n2">
								<div class="col-6 order-2 px-2">
									<button type="submit" value="Join Us" id="btn_submit"
										class="btn btn-primary btn-lg btn-block en">회원정보수정</button>
								</div>
								<div class="col-6 order-1 px-2">
									<a href="${pageContext.request.contextPath}/customer/withdrawalView" 
									class="btn btn-basic btn-lg btn-block en">회원탈퇴</a>
								</div>
							</div>
						</div>

						<input type="hidden" name="cus_answer"
							value="${customer.cus_answer}" id="cus_answer" required
							class="form-control required"> <input type="hidden"
							name="cus_question" value="${customer.cus_question}" id="cus_id"
							required class="form-control required">


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