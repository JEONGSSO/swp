<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
   
<%@ include file="../include/header.jsp" %>

<form role ="form" method="post">
	<input type ='hidden' name ='bno' value="${board.bno}">
</form>

<!-- 섹션 시작 ----------------------------------------------- -->
<section class="content">
	<div class="box-body">
		<div class="form-group">
				<label for="title1">제목</label>
				<input class="form-control" name="title"  type="text"  value="${board.title}"  readonly ="readonly"/>
		</div>
		
		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" name="content"  rows="3" id="content"
							 readonly ="readonly">${board.content}</textarea>							
		</div>
		
		<div class="form-group">
			<label for="writer1">작성자</label>
			<input  class="form-control" type= "text"  name ="writer" id="writer"   value="${board.writer}" readonly ="readonly"/>
		</div>
							<!--
							<div class="form-group">
								<label for="writer1">조회수</label>
								<span>${ board.viewcnt }</span>
							</div>
							-->
	</div>	
</section><!-- 섹션 끝  -------------------------------------------->

<!-- 글 수정 삭제 버튼-----------------------------------------------------------  -->
<div class="box-footer">
	<button id ="button-remove-read" class ="btn btn-denger">삭제</button>
	<a href="/board/update${criteria.makeQuery()}&bno=${board.bno}"  class="btn btn-warning">수정</a>
	<a href="/board/listPage${criteria.makeQuery()}" class="btn btn-primary">목록</a>
</div>

<!-- 댓글 목록--------------------------------------------------------------------------09 12-->
<script id="replies" class="well" type="text/x-handlebars-template"> 
	<ul class = "list-group">
		{{#each list}} {{!--리스트만큼 반복.--}}
			<a href = "#" class = "list-group-item" onclick="editReply({{rno}}, '{{replyer}}', '{{replytext}}')">
				{{replytext}}	
				<small class="text-muted"><i class="fa fa-user">{{replyer}}</i></small>
				<small class="text-muted pull right">{{fromNow regdate}}</small>	{{!--moment fromNow 현재시간--}}
			</a>
		{{/each}}
	</ul>
		
<div class="text-center">
	<nav aria-label="pagination"> {{!-- 0911 수업 --}}
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
<script type="text/x-handlebars-template"  class="modal fade" id="myModal" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">댓글 {{#if gIsEdit}}수정{{else}}등록{{/if}}</h4>
      </div>
      <div class="modal-body">
	      	<div>
	      	      작성자 : <input type="text" value="{{replyer}}" name="replyer" {{#if gIsEdit}}readonly{{/if}} id="replyer" class = "form-control" />
	     	 </div>
	       <div>
		            내용 : <textarea name="replytext" id="replytext" cols="30" rows="3" class="form-control">{{replytext}}</textarea>
	       </div>
      </div>
      
      <div class="modal-footer">
    	    <button id="btnReplyAdd" class="btn btn-primary" onclick="save()">{{#if gIsEdit}}수정{{else}}등록{{/if}}</button>	
		 	{{#if gIsEdit}}
	 	 		<button onclick = "removeReply()" id="btnDelReply">삭제</button>
	  		{{/if}}      
	  </div>
    </div>
  </div>
</script>

 <button onclick = "editReply()" id ="btnModReply" class = "btn btn-primary">등록</button>

	<script>
    $(document).ready(function(){
    		replylistPage(1, ${board.bno});
    		$("#button-remove-read").on("click",function(){
    			if(confirm("삭제 하시겠습니까?"))	{
    					self.location.href = "/board/remove${criteria.makeQuery()}&bno=${board.bno}"
    				}
    		});
    });
    
    var result = '${msg}';	
    if (result === 'ok') {
    	alert("수정 완료");
    }
    
</script>

<link rel="stylesheet" href="/resources/test.css" />
<script src="../resources/handlebars-v4.0.12.js"></script>
<script src="../resources/moment_min.js"></script>
<script src="../resources/hbs.js"></script>
<script src="../resources/reply.js"></script>

<%@include file="../include/footer.jsp" %>