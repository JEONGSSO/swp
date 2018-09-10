<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="../include/header.jsp" %>
    	
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
			<small class="text-muted"><i></i>{{regdate}}</small>
		  {{/each}}
		</ul>

		<nav aria-label="Page navigation" class = "text-center">
				<ul class="pagination">
				  <li>
					<a href="#" aria-label="Previous">
					  <span aria-hidden="true">&laquo;</span>
					</a>
				  </li>
				  <li><a href="#">1</a></li>
				  <li><a href="#">2</a></li>
				  <li><a href="#">3</a></li>
				  <li><a href="#">4</a></li>
				  <li><a href="#">5</a></li>
				  <li>
					<a href="#" aria-label="Next">
					  <span aria-hidden="true">&raquo;</span>
					</a>
				  </li>
				</ul>
			  </nav>
	</script>
	
	<script>
    $(document).ready(function(){
    		
    		$("#button-remove-read").on("click",function(){
    			if(confirm("삭제 하시겠습니까?"))	{
    					self.location.href = "/board/remove${criteria.makeQuery()}&bno=${board.bno}"
    				}
    		});
    	
    		$(".btn-danger").on("click",function(){
    			
    		});
    		
    		$(".btn-primary").on("click",function(){
    			self.location = "/board/listPage";
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
	<a href="/board/listPage${criteria.makeQuery()}" class="btn btn-primary">목록</a>
</div>

<script src="../resources/handlebars-v4.0.12.js"></script>
<script src="../resources/hbs.js"></script>
<script src="../resources/reply.js"></script>

<%@include file="../include/footer.jsp" %>