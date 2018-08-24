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
		<div class="alert alert-success" role="alert"></div>
	</c:when>
	
	<c:when test ="${msg eq 'remove-ok'}">
		<c:set var="resultMsg" value = "삭제완료."/>
		<div class="alert alert-success" role="alert">...</div>
	</c:when>
	
</c:choose>
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
		
<div class="row text-center">
	<div class ="form-inline">
			<select id="searchType">
				<option value="t">제목</option>
				<option value="c">내용</option>
				<option value="w">작성자</option>
				<option value="tc">제목+내용</option>
				<option value="a">전체 검색</option>
			</select>
			<input type="text" id="keyword"  name ="keyword" value="${ pageMaker.criteria.keyword}"
						placeholder ="검색어 입력" class ="form-control"/>
			<button id ="searchBtn" class ="btn btn-primary">검색</button>
	</div>
</div>

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
			
				<a href="/board/register" class="btn btn-primary"  id ="writer">글쓰기</a>
	</ul>
</div>  

<script>
   		$(document).ready(function() {
   			var canPrev = "${pageMaker.prev}";
   			console.log("canPrev >>", canPrev)
   			
   			if(canPrev !=='true'){
   				$('#page-prev').addClass('disabled')
   				}
   		
    		var canNext = "${pageMaker.next}";
    		
			if(canNext !=='true'){
				$('#page-next').addClass('disabled')
			}
   		
 			var thisPage = '${pageMaker.criteria.page}';
 			$('#page' + thisPage).addClass('active');
    	
 			$('#searchBtn').on('click', function() {
 				var $keyword = $('#keyword');
 				var $searchType =$('#searchType');
 				
 				var searchType = $searchType.val();
 				var keywordStr = $keyword.val();
 				
 				if(!keywordStr){
 					alert("검색어를 입력하세요.");
 					$keyword.focus();
 					return;
 				}
 				
 				var url = "listPage${pageMaker.makeQuery(1, false)}";
						url += "&searchType" + searchType;
						url += "&keyword" + encodeURIComponent(keywordStr);
						window.location.href = url;
        });
});	   			
</script>

<%@include file="../include/footer.jsp" %>