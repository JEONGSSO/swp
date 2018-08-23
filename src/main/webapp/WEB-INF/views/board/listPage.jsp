<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ page session="false" %>
       <%@ include file="../include/header.jsp" %>
    
    <c:set var="resultMsg" value =""/>
<c:choose>
	<c:when test ="${msg eq 'success'}">
		<c:set var="resultMsg" value = "등록완료."/>
		<div class="alert alert-success" role="alert">...</div>
	</c:when>
	
	<c:when test ="${msg eq 'remove-ok'}">
		<c:set var="resultMsg" value = "삭제완료."/>
		<div class="alert alert-info" role="alert">...</div>
	</c:when>
	
</c:choose>

	<c:if test="${null ne msg }">
	<div class="alert alert-success alert-dismissible">
		<button type="button" class = "close" data-dismiss="alert" aria-label ="Close"><span aria-hidden></span></button>
		<strong>${resultMsg}</strong>
	</div>
</c:if>

<c:if test="${null ne msg }">
	<div class="alert alert-info alert-dismissible">
		<button type="button" class = "close" data-dismiss="alert" aria-label ="Close"><span aria-hidden></span></button>
		<strong>${resultMsg}</strong>
	</div>
</c:if>

    
    <table class="table table-bordered">
    <tr>
	    <th style="width: 10px">번호</th>
	    <th>제목</th>
	    <th>작성자</th>
	    <th>작성 날짜</th>
	    <th>조회수</th>
    </tr>
    
  <c:forEach items="${list}"  var="board">
	  <tr>
	    	<td>${ board.bno }</td>
	    	<td><a href="/board/read${pageMaker.makeQuery(pageMaker.criteria.page)}&bno=${board.bno}"> ${board.title}</a></td>
	    	<td>${board.writer}</td>
	    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value ="${board.regdate}" /></td>
	    	<td><span class ="badge bg-red">${ board.viewcnt }</span></td>
	    </tr>
    </c:forEach>
    
</table>
<div class ="text-center">
	<ul class = "pagination">
	
		<c:if test="${pageMaker.prev }">
			<li><a href="listPage${pageMaker.makeQuery(pageMaker.startPage - 1) }">&laquo;</a></li>
		</c:if>
		
		<c:forEach begin="${ pageMaker.startPage }"
			end="${ pageMaker.endPage }" var="idx">
			
			<li
				<c:out value="${pageMaker.criteria.page == idx ? 'class =active' : ''}"/>>
				<a href="listPage${pageMaker.makeQuery(idx)}">${idx }</a>
			</li>
		</c:forEach>
			
			<c:if test="${ pageMaker.next && pageMaker.endPage > 0}" >
				<li><a href="listPage${pageMaker.makeQuery(pageMaker.endPage +1)}">&raquo;</a></li>
			</c:if>
	</ul>
</div>    
<a href="/board/register" class="btn btn-primary" id ="writer">글쓰기</a>
<%@include file="../include/footer.jsp" %>