<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="include/header.jsp"%>

	<form role="form" action="/loginPost" method="post"><!-- QQQ -->
		<div class="box-body">
			<div class="form-group">
			<input type="text" id="uid" name="uid" value="user1" 
					class="form-control" placeholder="아이디" />
			</div>
			<div class="form-group">
				<input type="password" name="upw" id="upw"	value="1234"
							 class="form-control" placeholder="비밀번호"/>
			</div>
			
			<div class="form-group">
				<label for="useCookie">
					<input type="checkbox" id="useCookie" name="useCookie" /> 로그인 유지
				</label>
			</div>
		</div>
		
		<div class="box-footer">
		  <button type="submit" class="btn btn-primary">로그인</button>
		</div>
		
	</form>
	
	<div class = "text-center">
		<a href="${ naver_url }"> <img width="150"  src="../../resources/dist/img/naver.jpg"  alt="naver_login"/></a>
		<a href="${ google_url }"> <img width="222" src="../../resources/dist/img/google.jpg"  alt="google_login"/></a>
	</div>

<%@ include file="include/footer.jsp"%>
