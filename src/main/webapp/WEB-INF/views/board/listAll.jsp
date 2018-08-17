<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ page session="false" %>
    <%@ include file="../include/header.jsp" %>
    
<script>
    
    var result = '${msg}';
    if (result === 'success') {
    	alert("OK");
    }
</script>

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
	    	<td><a href="/board/read?bno=${board.bno}'">${board.title}</a></td>
	    	<td>${board.writer}</td>
	    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value ="${board.regdate}" /></td>
	    	<td>${board.viewcnt }</td>
	    </tr>
    </c:forEach>
    
</table>

<button id = "writerr" type="submit" class="btn btn-primary">글쓰기</button>

<%@include file="../include/footer.jsp" %>