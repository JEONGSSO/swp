<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<form role="form" method="post">
	<div class="box-body">
		<div class="form-group">

			<div class="form-group">
				<label for="writer1">작성자</label> <input class="form-control"
					type="text" id="writer1" name="writer" placeholder="Enter Writer" />
			</div>

			<label for="title1">제목</label> <input class="form-control"
				name="title" type="text" id="title1" placeholder="제목" />
		</div>

		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" name="content" id="content" cols="30"
				rows="3" placeholder="내용"></textarea>
		</div>

		<!-- 0928 drag & drop  -->
	<div class="form-group">
		<div class="fileDrop text-right">
			<label>여기다 떨구세요</label>
			<div id="percent">0%</div>
			<div id="status">ready</div>
		</div>
		</div>
	</div>

		<!-- 0928 첨부파일 -->
		<ul class="mailbox-attachments clearfix uploadedList">
			<%@ include file="uploadedFiles.jsp"%>
		</ul>
		
	<div class="box-footer">
	
		<button type="submit" class="btn btn-primary">작성</button>
		<a href="/board/listPage${criteria.makeQuery()}" class="btn btn-default">취소</a>
	</div>
		<!-- body box 끝  -->
</form>

<form id="form_attach" action="/uploadAjaxes" method="POST" enctype="multipart/form-data">
	<input type="hidden" name=type value="ajax" />
	<input type="file" name="files" id="ajax_file" style="display: none;" />
	<%-- <input type='submit' value = "ajax로 제출"/> --%>
</form>

<%@include file="../include/footer.jsp"%>
