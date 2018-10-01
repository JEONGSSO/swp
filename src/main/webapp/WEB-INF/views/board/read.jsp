<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="isTest" scope="page" value="${false}" />
<!--QQQ :  Reply Unit Test -->
<%@ include file="../include/header.jsp"%>

<script>
	var result = '${msg}';	
	if (result === 'ok') 
	{
		alert("수정 완료");
	}
</script>

<form role="form" method="post">
	<input type='hidden' name='bno' value="${board.bno}">
</form>

<c:if test="${ true eq isTest }">
	<%@ include file="../qunit.jsp"%>
	<script src="../resources/test/replytest.js"></script>
</c:if>

<!-- 섹션 시작 ----------------------------------------------- -->
<section class="content">
	<div class="box-body">

		<div class="form-group">
			<label for="writer1">작성자</label> <span style="float: right"><label
				for="writer1">조회수</label>${ board.viewcnt }</span> <input
				class="form-control" type="text" name="writer" id="writer"
				value="${board.writer}" readonly="readonly" />
		</div>

		<div class="form-group">
			<label for="title1">제목</label> <input class="form-control"
				name="title" type="text" value="${board.title}" readonly="readonly" />
		</div>

		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" name="content" rows="3" id="content"
				readonly="readonly">${board.content}</textarea>
		</div>
	</div>

	<!-- 1001 파일업로드  -------------------------------------------->
	<ul class="mailbox-attachments clearfix uploadedList">
		<%@include file="uploadedFiles.jsp"%>
	</ul>

	<!-- 글 수정 삭제 버튼-----------------------------------------------------------  -->
	<div class="box-footer">
		<button id="button-remove-read" class="btn btn-danger">삭제</button>
		<a href="/board/update${criteria.makeQuery()}&bno=${board.bno}"
			class="btn btn-warning">수정</a> <a
			href="/board/listPage${criteria.makeQuery()}" class="btn btn-primary">목록</a>
		<button onclick="editReply()" id="btnModReply" class="btn btn-Info">댓글
			등록</button>
	</div>
</section><!-- 섹션 끝  -------------------------------------------->
	<!-- 댓글 목록--------------------------------------------------------------------------09 12-->
	<script id="replies" class="well" type="text/x-handlebars-template"> 
	<ul class = "list-group">
		{{#each list}} {{!--리스트만큼 반복.--}}
			<a href = "#" class = "list-group-item" onclick="editReply({{rno}}, '{{replyer}}', '{{replytext}}')">	{{!--댓글 클릭 하면 수정으로 todo 요거 몇몇댓글--}}
				{{replytext}}	
				<small class="text-muted"><i class="fa fa-user">{{replyer}}</i></small>
				<small class="text-muted pull right">{{fromNow regdate}}</small>	{{!--moment fromNow 현재시간--}}
			</a>
		{{/each}}
	</ul>
		
<div>
	<nav aria-label="pagination" class="text-center"> {{!-- 0911 수업 --}}
		<ul class ="pagination">
		{{#if pageData.prevPage}}	 {{!-- 페이지 메이커 prev가 있으면 밑에 실행해 --}}
					<li>
						<a href = "#" onclick="replylistPage({{pageData.prevPage}})">	{{!-- 클릭하면 제이슨한테 데이터를 받아서 replylistPage 실행 --}}
							<span>&lt;&lt;</span>
						</a>
					</li>
			{{/if}}
			{{pageData.currentPage}}
			{{#each pageData.pages as |page|}}	{{!-- as는 원소의 이름을 page로 받겠다. --}}
					<li class="{{#if (eq ../currentPage page)}}active{{/if}}">	{{!-- 만약에 if eq는 함수, ..currentPage page?? 뭐하는건지?  --}}
						<a href="javascript:;" onclick="replylistPage({{page}})"	{{!-- 링크를 자바스크립트로 걸고? 클릭하면 데이터 받아서 replylist실행  --}}
							class=data-page="{{page}}">
							{{page}}
						</a>
					</li>
			{{/each}}

			{{#if pageData.nextPage}}
					<li>
						<a href = "#" onclick="replylistPage({{pageData.nextPage}})">
							<span>&gt;&gt;</span>
						</a>
					</li>
			{{/if}}
		</ul>
	</nav>
</div>	
</script>

<!-- 핸들러~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<script type="text/x-handlebars-template" class="modal fade" id="myModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">댓글 {{#if gIsEdit}}수정{{else}}등록{{/if}}</h4>
      </div>
      <div class="modal-body">
	      	<div>
	      	      작성자 : <input type="text" onkeyup="toggleEditBtn()" value="{{replyer}}" name="replyer" {{#if gIsEdit}}readonly{{/if}} id="replyer" class = "form-control" />
	     	 </div>
	       <div>
		            내용 : <textarea name="replytext" id="replytext" onkeyup="toggleEditBtn()" cols="30" rows="3" class="form-control">{{replytext}}</textarea>
	       </div>
      </div>
      
      <div class="modal-footer">	{{!-- 수정 모달 안에 버튼들 --}}
    	    <button id="btnReplyAdd" class="btn btn-primary"  onclick="save()">{{#if gIsEdit}}수정{{else}}등록{{/if}}</button>	
		 	{{#if gIsEdit}}
	 	 		<button onclick = "removeReply()" id="btnDelReply">삭제</button>
	  		{{/if}}      
	  </div>
    </div>
  </div>
</script>

<script src="/resources/reply.js"></script>
<script src="/resources/upload.js"></script>

<script>
$(document).ready(	function()
   {
   		replylistPage(1, ${board.bno});
   		$("#button-remove-read").on('click', function()
   		{
   			if(confirm("삭제 하시겠습니까?"))	
   					self.location.href = "/board/remove${criteria.makeQuery()}&bno=${board.bno}";
   		});
   		
   		listPage(1, '${board.bno}'); // QQQ
   		
   		sendAjax("/board/getAttach/${board.bno}", (isSuccess, res) => 
   		{
   	        if (isSuccess) 
   	        {
   	        	let upfiles =[];	// array of jsonData
   	        	res.forEach( rj =>	
	            {
	            	let jsonData = getFileInfo(rj);
	            	upFiles.push(jsonData);
	            });
   	        	renderHbs('template', { upfiles : upfiles }, 'div');
   	    } 	else 
   	    	{
   	            console.debug("Error on registerReply>>", res);
   	        }
   	});
  });
</script>

<%@include file="../include/footer.jsp"%>