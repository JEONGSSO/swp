<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="isTest" scope="page" value="${false}" />
<!--QQQ :  Reply Unit Test -->
<%@ include file="../include/header.jsp"%>

<script>
	let result = '${msg}';	
	if (result === 'ok') 
		alert("수정 완료");
</script>

<c:if test="${ true eq isTest }">
	<%@ include file="../qunit.jsp"%>
	<script src="../resources/test/replytest.js"></script>
</c:if>

<!-- 섹션 시작 ----------------------------------------------- -->
<section class="content">
	<div class="box-body">
		<div class="form-group">
			<label for="writer">작성자</label> <span style="float: right">
			<label for="writer">조회수</label>${ board.viewcnt }</span>
			<input
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
		
				<!-- 1001 파일업로드  -------------------------------------------->
			<ul class="mailbox-attachments clearfix uploadedList">
				<%@include file="uploadedFiles.jsp"%>
			</ul>
	</div>

	<!-- 글 수정 삭제 버튼-----------------------------------------------------------  -->
	<div class="box-footer">
	<c:if test="${ loginUser.uid == board.writer }">	<!-- TODO 작성자랑 아이디랑 같으면 나오게 -->
		<button id="button-remove-read" class="btn btn-danger">삭제</button>
		<a href="/board/update${criteria.makeQuery()}&bno=${board.bno}"
			class="btn btn-warning">수정</a>
	</c:if>
		<a href="/board/listPage${criteria.makeQuery()}" class="btn btn-primary">목록</a>
		<c:if test="${ loginUser.uid != null}"> <!-- QQQ 아이디 없으면 댓글등록창없게 -->
			<button onclick="editReply('${loginUser.uid}')" id="btnModReply" class="btn btn-Info">댓글등록</button>
		</c:if>
	</div>
</section><!-- 섹션 끝  -------------------------------------------->

	<!-- 댓글 목록--------------------------------------------------------------------------09 12-->
	<script id="replies" class="well" type="text/x-handlebars-template"> 
	<ul class = "list-group">
		{{#each list}} {{!--리스트만큼 반복.--}}
			<a href = "#" class = "list-group-item" onclick="editReply('{{../loginUid}}', {{rno}}, '{{replyer}}', '{{replytext}}')">
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
						<a href="javascript:;" onclick="replylistPage({{page}},{{bno}})"	{{!-- 링크를 자바스크립트로 걸고? 클릭하면 데이터 받아서 replylist실행  --}}
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
	      	     <input type="text" onkeyup="toggleEditBtn()" value="{{replyer}}"
									name="replyer" {{#if gIsEdit}}readonly{{/if}} id="replyer" class = "hidden" readonly />
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
<script>
$(document).ready(	function()
   {
   		$("#button-remove-read").on('click', function()
   		{
   			if(confirm("삭제하시겠습니까?"))	
   					self.location.href = "/board/remove${criteria.makeQuery()}&bno=${board.bno}";
   		});
   		replylistPage(1, '${board.bno}');	//QQQ ''치는 이유 안쳤는데 없으면 에러나서
   		gIsDirect = true;	//1010 
   });
</script>


<script>	// 1002 파일목록
	showAttaches(${board.bno});
</script>

<%@include file="../include/footer.jsp"%>