<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="../include/header.jsp" %>
   	<link rel="stylesheet" href="/resources/test.css" />
   	
<form  role ="form" method="post">
	<input type ='hidden' name ='bno' value="${board.bno}">
</form>

<div class="box-body">
	<div class="form-group">
			<label for="title1">제목</label>
			<input class="form-control" name="title"  type="text"  value="${board.title}"  readonly ="readonly"/>
	</div>
	
	<div class="form-group">
		<label for="content">내용</label>
		<textarea class="form-control" name="content"  rows="3"
						 readonly ="readonly">${board.content}</textarea>							
	</div>
	
	<div class="form-group">
		<label for="writer1">작성자</label>
		<input  class="form-control" type= "text"  name ="writer"   value="${board.writer}"   readonly ="readonly"/>
	</div>
	
	<div class="form-group">
		<label for="writer1">조회수</label>
		<span>${ board.viewcnt }</span>
	</div>
	
</div>	<!-- body box 끝  -->

<script id="replies" type="text/x-handlebars-template" class="well"> 
		<ul class = "list-group">
		  {{#each list}} 
			<li class = "list-group-item">
				{{replyer}}	
			<small class="text-muted"><i class="fa fa-user"></i>{{replytext}}</small>
			<small class="text-muted"><i></i>{{fromNow regdate}}</small>
		  {{/each}}
		</ul>
		
<div class="text-center">
	<nav aria-label="pagination"> {{!-- 0911 수업 --}}
		<ul class ="pagination">
		{{#if (pageData.prevPage)}}	 {{!-- 페이지 메이커 prev가 있으면 밑에 실행해 --}}
					<li>
						<a href = "#" onclick="replylistPage({{pageData.prevPage}})" aria-label="Previous"	{{!-- 클릭하면 제이슨한테 데이터를 받아서 replylistPage 실행 --}}
							<span aria-hidden="true">&laquo;</span>	</a>	{{!--  --}}
					</li>
			{{/if}}
			
			{{#each pageData.pages as |page|}}	{{!-- as는 원소의 이름을 page로 받겠다. --}}
					<li class="{{#if (eq ../currentPage page)}}active{{/if}}">	{{!-- 만약에 if eq는 함수, ..currentPage page?? 뭐하는건지?  --}}
						<a href="javascript:" class="" onclick="replylistPage({{page}})">{{page}}</a>	{{!-- 링크를 자바스크립트로 걸고? 클릭하면 데이터 받아서 replylist실행  --}}
					</li>
			{{/each}}

			{{#if (pageData.nextPage)}}
					<li>
						<a href = "#" onclick="replylistPage({{pageData.nextPage}})" aria-label="Next"
							<span aria-hidden="true">&raquo;</span>	</a>
					</li>
		{{/if}}
	</nav>
	</ul>
</div>	
	</script>
	
	<script>
    $(document).ready(function(){
    		
    		replylistPage(1, ${board.bno});
    		$("#button-remove-read").on("click",function(){
    			if(confirm("삭제 하시겠습니까?"))	{
    					self.location.href = "/board/remove${criteria.makeQuery()}&bno=${board.bno}"
    				}
    		});
    	
    		$(".btn-danger").on("click",function(){
    			
    		});
    		
    		$(".btn-primary").on("click",function(){
    			self.location = "/board/replylistPage";
    		});
    });
    
    var result = '${msg}';	
    if (result === 'ok') {
    	alert("수정 완료");
    }
</script>

<div class="box-footer">
	<button id ="button-remove-read" class ="btn btn-denger">삭제</button>
	<a href="/board/update${criteria.makeQuery()}&bno=${board.bno}"  class="btn btn-warning">수정</a>
	<a href="/board/replylistPage${criteria.makeQuery()}" class="btn btn-primary">목록</a>
</div>

<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  Launch demo modal
</button>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">댓글 수정</h4>
      </div>
      <div class="modal-body">
	      <div>
	            작성자 : <input type="text" name="replyer" id="newReplyWriter" class = "form-content" />
	      </div>
	      
	       <div>
		            내용 : <textarea name="replytext" id="newReplyText" cols="30" rows="3"></textarea>
	       </div>
	       
           <div>
                <textarea id="replycontext" cols="35" rows="3" placeholder="Enter"></textarea>  <!-- 텍스트 쓰는곳 -->  
           </div>
      </div>
      
      <div class="modal-footer">
	        <button style="display:none" onclick = "editReply()" id ="btnModReply">수정</button>	<!--@@@Todo 글 누르면 수정으로가게 끔 -->
			<button onclick = "removeReply()" id="btnDelReply">삭제</button>
			<button onclick = "closeMod()" id="btnCloseReply">닫기</button>
      </div>
    </div>
  </div>
</div>

<script src="../resources/handlebars-v4.0.12.js"></script>
<script src="../resources/moment_min.js"></script>
<script src="../resources/hbs.js"></script>
<script src="../resources/reply.js"></script>

<%@include file="../include/footer.jsp" %>