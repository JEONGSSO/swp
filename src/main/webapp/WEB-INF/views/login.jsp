<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="include/header.jsp"%>

	<form role="form" action="/loginPost" method="post">
		<div class="box-body">
			<div class="form-group">
			<input type="text" id="uid" name="uid"
					class="form-control" placeholder="아이디" />
			</div>
			<div class="form-group">
				<input type="password" name="upw" id="upw"
							 class="form-control" placeholder="비밀번호"/>
			</div>
			
			<div class="form-group">
				<label for="useCookie">
					<input type="checkbox" id="usecookie" name="usecookie" /> 로그인 기억
				</label>
			</div>
		</div>
		
		<div class="box-footer">
		  <button type="submit" class="btn btn-primary">로그인</button>
		</div>
		
	</form>

<%@ include file="include/footer.jsp"%>