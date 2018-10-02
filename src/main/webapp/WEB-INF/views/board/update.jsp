<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>

<div class="box-body">
	<form role="form" action="/board/update${criteria.makeQuery()}" method="post">
		<input type="hidden" name ="bno" value = "${board.bno}"/>
		<div class="form-group">
				<label for="title1">제목</label>
				<input class="form-control" name="title" value="${board.title}"  type="text"  id = "title1"   placeholder="제목" />
		</div>
		
		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" name="content"  id="content" cols="30" rows="3"
							placeholder="내용">${board.content}</textarea>							
		</div>
		
	<div class="form-group">
		<div class="fileDrop text-right">
			<label>여기다 떨구세요</label>
			<div id="percent">0%</div>
			<div id="status">ready</div>
		</div>
	</div>
		
		<ul class="mailbox-attachments clearfix uploadedList">
			<%@include file="uploadedFiles.jsp"%>
		</ul>
		
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">수정</button>	<!--sumbit은 form의 action을 탄다-->
			<a  href="/board/read${criteria.makeQuery()}&bno=${board.bno}" class="btn btn-default">취소</a>
		</div>
	</form>
	
	<form id="form_attach" action="/uploadAjaxes" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="type" value="ajax" />
		<input type="file" name="files" id="ajax_file" style="display: none;" />
		<%-- <input type='submit' value = "ajax로 제출"/> --%>
	</form>

</div>

<script>	// 1002 파일목록
	showAttaches(${board.bno});
</script>

					
<%@include file="../include/footer.jsp" %>