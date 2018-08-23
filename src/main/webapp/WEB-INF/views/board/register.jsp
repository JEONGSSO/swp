<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>

			<form role="form" method="post">
					<div class="box-body">
						<div class="form-group">
								<label for="title1">제목</label>
								<input class="form-control" name="title"  type="text"  id = "title1"  placeholder="제목" />
						</div>
						
						<div class="form-group">
							<label for="content">내용</label>
							<textarea class="form-control" name="content"  id="content" cols="30" rows="3"
											placeholder="내용"></textarea>							
						</div>
						
						<div class="form-group">
							<label for="writer1">작성자</label>
							<input  class="form-control" type= "text"  id="writer1"  name ="writer"  placeholder="Enter Writer"/>
						</div>
					</div>	<!-- body box 끝  -->
					
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">작성</button>
						<a  href="/board/listPage${criteria.makeQuery()}" class="btn btn-default">취소</a>
					</div>
					
			</form>
			
<%@include file="../include/footer.jsp" %>