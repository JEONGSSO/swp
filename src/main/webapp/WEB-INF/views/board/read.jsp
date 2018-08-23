<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="../include/header.jsp" %>
    
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

<div class="box-footer">
	<button id ="button-remove-read" class ="btn btn-denger">삭제</button>
	<a href="/board/update?bno=${board.bno}"  class="btn btn-warning">수정</a>
	<a href="/board/listPage${criteria.makeQuery()}" class="btn btn-primary">목록</a>
</div>
	
<%@include file="../include/footer.jsp" %>