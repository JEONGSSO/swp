<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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
    <ul id="replies">    
    </ul>
    
    <div>
        <div>
            작성자 : <input type="text" name="replyer" id="newReplyWriter" />
        </div>
        <div>
            내용 : <textarea name="replytext" id="newReplyText" cols="30" rows="3"></textarea>
        </div>
        <button id="btnReplyAdd" class="btn btn-primary">등록</button>
        <button id="btnShowJson" class="btn btn-warning">댓글전체JSON</button>
    </div>
    
    <div id="modDiv">
        <div class="modal-title">
            <span id="mod-rno"></span>
            <div>
                <textarea id="replycontext" rows="3" placeholder="Enter"></textarea>  <!-- 글의 텍스트 -->  
            </div>
        </div>
        <div>
            <button style="display:none" onclick = "editReply()" id ="btnModReply">수정</button>
            <button onclick = "removeReply()" id="btnDelReply">삭제</button>
            <button onclick = "closeMod()" id="btnCloseReply">닫기</button>
        </div>
    </div>

    <ul class="pagination">
        <li><a href="#" data-page = "000">&lt;&lt;</a></li> <!-- lt는 prev -->
        <li><a href="#" data-page = "1">&lt;&lt;</a></li> <!-- lt는 prev -->
        <li><a href="#" data-page = "333">&gt;&gt;</a></li>    <!-- gt는 넥스트 --> 요거 3개 js로 빼기 껍데기만 넘기는게 에이작스
        
    </ul>
    
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/test.js"></script>
<script>
    $(function(){
        //$('#h2-title').on('click',listAll);
        listPage();
        $('#btnReplyAdd').click(function(){
            registerReply();
        })
        
        $('#btnShowJson').click(function(){
            showJson();
        })
        
        movCenterModDiv();
    });
</script>
</body>
</html>

