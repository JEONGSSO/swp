<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
<link rel="stylesheet" href="/resources/test.css" />
</head>
<body>
    <h2 id = "h2-title" class="point">Ajax Test Page</h2>
    
    	<!-- 댓글 목록 ------------------------------------------------>
    <ul id="replies">    
    </ul>
    
    <div>
	    <form id="newreg">
	        <div>
	            작성자 : <input type="text" name="replyer" id="newReplyWriter" />
	        </div>
	        <div>
	            내용 : <textarea name="replytext" id="newReplyText" cols="30" rows="3"></textarea>
	        </div>
       </form>
        <button id="btnReplyAdd" class="btn btn-primary">등록</button>
        <button id="btnShowJson" class="btn btn-warning">댓글전체JSON</button>
    </div>
    
    <div id="modDiv">    
        <div class="modal-title">
            <span id="mod-rno"></span>
            <div>
                <textarea id="replycontext" cols="35" rows="3" placeholder="Enter"></textarea>  <!-- 텍스트 쓰는곳 -->  
            </div>
        </div>
        <div>
            <button style="display:none" onclick = "editReply()" id ="btnModReply">수정</button>	<!--@@@Todo 글 누르면 수정으로가게 끔 -->
          	 <button onclick = "removeReply()" id="btnDelReply">삭제</button>
            <button onclick = "closeMod()" id="btnCloseReply">닫기</button>
        </div>
    </div>
    <ul class="pagination">		<!-- 0907 페이지 껍데기만 보내고 나머지는 js가 만들어준다. -->  
    </ul>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/test.js"></script>
<script>
    $(function(){
        //$('#h2-title').on('click',listAll);
        replylistPage();
        $('#btnReplyAdd').click(function(){
            registerReply();
        })
    });
</script>
</body>
</html>

