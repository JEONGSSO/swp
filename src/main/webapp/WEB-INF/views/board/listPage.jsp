<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ page session="false" %>
       <%@ include file="../include/header.jsp" %>
    
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
<%@include file="../include/footer.jsp" %>