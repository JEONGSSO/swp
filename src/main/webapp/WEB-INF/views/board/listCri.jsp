<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ include file="../include/header.jsp" %>
    
<script>

    var result = '${msg}';
    if (result === 'success') {
    	alert("OK");
    	}
    	else if(result === "remove-ok"){
    			alert("삭제 완료");
    		}
    
    $("#writer").on("click",function(){
				self.location.href = "/board/register"
	});
    
</script>

<c:set var="resultMsg" value =""/>
<c:choose>
	<c:when test ="${msg eq 'success'}">
		<c:set var="resultMsg" value = "등록완료."/>
	</c:when>
</c:choose>

<c:if test="${null ne msg }">
	<div class="alert alert-success alert-dismissible">
		<button type="button" class = "close" data-dismiss="alert" aria-label ="Close"><span aria-hidden></span></button>
		<strong>${resultMsg}</strong>
	</div>
</c:if>

<div class="alert alert-success" role="alert">...</div>
<div class="alert alert-info" role="alert">...</div>
<div class="alert alert-warning" role="alert">...</div>
<div class="alert alert-danger" role="alert">...</div>


<table class="table table-bordered">
    <tr>
	    <th style="width: 10px">BNO</th>
	    <th>TITLE</th>
	    <th>WRITER</th>
	    <th>REGDATE</th>
	    <th style="width: 40px">VIEWCNT</th>
    </tr>
    
  <c:forEach items="${list}"  var="board">
	  <tr>
	    	<td>${board.bno}</td>
	    	<td><a href="/board/read?bno=${board.bno}"> ${board.title}</a></td>
	    	<td>${board.writer}</td>
	    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value ="${board.regdate}" /></td>
	    	<td>${board.viewcnt }</td>
	    </tr>
    </c:forEach>
    
</table>

<a href="/board/register" class="btn btn-primary" id ="writer">글쓰기</a>

<%@include file="../include/footer.jsp" %>