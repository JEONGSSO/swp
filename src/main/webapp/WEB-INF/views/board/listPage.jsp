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

<!-- 페이지 번호 -->	
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

<script>
   		$(function(){
   			//perPageNum select 박스 설정
   			setPerPageNumSelect();
   			//searchType select 박스 설정
   			setSearchTypeSelect();
   			
   			//등록, 삭제 후 문구 처리
   			var result = '${result}';
   			$(function(){
   				if(result === 'registerOK'){
   					$('#registerOK').removeClass('hidden');
   					$('#registerOK').fadeOut(2000);
   				}
   				if(result === 'removeOK'){
   					$('#removeOK').removeClass('hidden');
   					$('#removeOK').fadeOut(2000);
   				}
   			})
   			
   			//prev 버튼 활성화, 비활성화 처리
   			var canPrev = '${pageMaker.prev}';
   			if(canPrev !== 'true'){
   				$('#page-prev').addClass('disabled');
   			}
   			
   			//next 버튼 활성화, 비활성화 처리
   			var canNext = '${pageMaker.next}';
   			if(canNext !== 'true'){
   				$('#page-next').addClass('disabled');
   			}
   			
   			//현재 페이지 파란색으로 활성화
   			var thisPage = '${pageMaker.criteria.page}';
   			//매번 refresh 되므로 다른 페이지 removeClass 할 필요는 없음->Ajax 이용시엔 해야함
   			$('#page'+thisPage).addClass('active');
   		})
   		
   		function setPerPageNumSelect(){
		var perPageNum = '${pageMaker.criteria.perPageNum}';
		var $perPageSel = $('#perPageSel');
		var thisPage = '${pageMaker.criteria.page}';
		
		$perPageSel.val(perPageNum).prop("selected",true);
		$perPageSel.on('change',function(){
			window.location.href = "listPage?page="+thisPage+"&perPageNum="+$perPageSel.val();
		})
   		
 			function setSearchTypeSelect(){
 				var $searchTypeSel = $('#searchTypeSel');
 				var $keyword = $('#keyword');
 				
 				$searchTypeSel.val('${pageMaker.criteria.searchType}').prop("selected",true);
 				//검색 버튼이 눌리면
 				$('#searchBtn').on('click',function(){
 					var searchTypeVal = $searchTypeSel.val();
 					var keywordVal = $keyword.val();
 					//검색 조건 입력 안했으면 경고창 
 					if(!searchTypeVal){
 						alert("검색 조건을 선택하세요!");
 						$searchTypeSel.focus();
 						return;
 					//검색어 입력 안했으면 검색창
 					}else if(!keywordVal){
 						alert("검색어를 입력하세요!");
 						$('#keyword').focus();
 						return;
 					}
 					var url = "listPage?page=1"
 						+ "&perPageNum=" + "${pageMaker.criteria.perPageNum}"
 						+ "&searchType=" + searchTypeVal
 						+ "&keyword=" + encodeURIComponent(keywordVal);
 					window.location.href = url;
 				})
		}
</script>

<%@include file="../include/footer.jsp" %>