let gbno = 0,
	gIsEdit = false;
	gRno = 0,
	gPage = null;
	
const	REGIST_URL = "/replies/";
	//todo 

function replylistPage(page, bno) {		//0911 수업
	gbno = bno || gbno;
    page = page || gPage || 1;
    listUrl = "/replies/all/" + gbno + "/" + page;

    sendAjax(listUrl, (isSuccess, res) => {
        if (isSuccess) {
             res.pageData = makePageData(res.pageMaker);
//             console.debug("디버그",res.pageData);
        	 res.currentPage = page;	//pageData 삭제하고 액티브 나오게 했다.
//        	 console.debug(">>>", res)
             renderHbs("replies", res, 'div'); //호출 replies , res 제이슨 데이타, 태그는 div
    	 }
	});
}

function makePageData(pageMaker){	//0911 수업
//    console.debug(">saddasd>>",pageMaker);
    let pageData = {	//제이슨 pagaData안에 prev, next, pages담김
        prevPage : 0,
        nextPage : 0,
        pages : []
        
    };

    if (pageMaker.prev) {	//페이지 메이커에 prev 있으면
    	pageData.prevPage = pageMaker.startPage - 1;
    	//페이지 데이터 안에 prev는 page메이커 스타트 페이지 -1
    }

    for (let i = pageMaker.startPage ; i <= pageMaker.endPage ; i++) {
        pageData.pages.push(i); //pageData.pages는 위에 pages [] 푸쉬는 넣는거  
    }
    if(pageMaker.next){
    	pageData.nextPage = pageMaker.endPage + 1;
    }
    return pageData;
}	//0911 수업

function editReply(rno, replyer, replytext){	//수정인지 등록인지 계산하는 함수	
	console.debug("editReply " + rno, replyer, replytext)
	event.preventDefault();	//# 눌렀을 때 작동 안되게
	gIsEdit = !!rno; //rno가 없거나 공백이거나 스페이스바거나 두루뭉술 gisEdit 만들
	gRno = rno;
	renderHbs('myModal', {
		gIsEdit : gIsEdit,
		replyer : replyer,
		replytext : replytext
	}, 'div');	//(tmpid, jsonData, tag)를 각각 넣어준다.
	$('#myModal').modal();	//모달 쇼 디폴트 쇼
}

function closeMod() { //댓글 창 지우는 것
	gRno = 0;
//	$('#replyer').val('');	//작성자 지워주기
//	$('#replytext').val('');	//텍스트 지워주기 
	$('#mymodal').modal('hide');	//모달 숨김
	}

function save() {	//0912
    let jsonData = getValidData($('#replyer'), $('#replytext'));	//수정 등록 경계를 허물어서 바꿈
    let url = gIsEdit ? REGIST_URL + gRno : REGIST_URL,	
    	method = gIsEdit ? 'PATCH' : 'POST';
    if(!gIsEdit){
    		jsonData.bno = gbno;
    	}
    sendAjax(url, (isSuccess, res) => { //url, is
        if (isSuccess) {
        	let resultMsg = gIsEdit ? gRno + "번 댓글이 수정되었습니다." 
        			: "댓글이 등록되었습니다.";
            alert(resultMsg);
            replylistPage(gIsEdit || 1); //등록완료되면 페이지 1로
//            document.getElementById("newreg").reset(); //0909 new부분 폼으로 감싸서 완료. 입력창 비워주기
        } else {
            console.debug("Error on registerReply>>", res);
        }
    }, method, jsonData);
}

function sendAjax(url, fn, method, jsonData) {
    let options = {
        method: method || 'GET', //메소드 get post put 등
        url: url,
        contentType: "application/json" //타입은 제이슨으로 받겠다.
    };
    //jsonData가 있을 때만 data : JSON.stringify(jsonData) 추가
    if (jsonData) {
        options.data = JSON.stringify(jsonData);
        console.log(options);
    }

    $.ajax(options).always((responseText, statusText, ajaxResult) => {
        let isSuccess = statusText === 'success'; //ajax 호출 성공 여부
        fn(isSuccess, responseText);
        if (!isSuccess) {
            alert("오류가 발생하였습니다. (errorMessage:" + responseText + ")");
        }
    })
}

function removeReply() {
    if (!confirm("댓글을 삭제하시겠습니까?")) return;

//    workingPage = ${replylistPage}
    sendAjax("/replies/" + gRno, (isSuccess, res) => {
        if (isSuccess) { 
            alert(gRno + " 번 댓글이 삭제완료되었습니다.");
            gPage = ($('.active').data().page);	//액티브에 데이타함수에 페이지 값
            replylistPage(workingPage); //댓글 페이지 유지
            closeMod(); //댓글창 닫기
        } else {
            console.debug("Error on removeReply>>", res);
        }
    }, 'DELETE');
}

function getValidData($replyer, $replytext) {	//Register때만 실행
    let errorFocus = null,
        replyer = $replyer.val(),
        replytext = $replytext.val(),
        errorMsg = "";

    if (!gIsEdit && !replyer) {
        errorMsg = "작성자를 입력하세요.";
        $errorFocus = $replyer;
    } else if (!replytext) {
        errorMsg = "내용을 입력하세요";
        $errorFocus = $replytext;
    }

    if (errorMsg) {
        alert(errorMsg);
        $errorFocus.focus();
        return;
    }

    return {
        replyer: replyer,
        replytext: replytext
    };
}

//    return str.replace(/[\n\r\t]/g, '').trim(); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈	트림은 공백제거

//function toggleEditBtn() {
//	let editedReplytext = $('#replycontext').val();	//수정된 텍스트는 제이쿼리 id	replycontext 에서 val로 가져옴
//    // $('#btnModReply').hide()    //수정버튼이 사라져있어야 하는데 아직은 안된다	//디스플레이 none으로 대체
//    //   $('#replycontext').change(function() { alert("content changed"); });	//체인지 함수.
//	//    console.log($('#replycontext').val() == editedReplytext); //true 값이랑 비교하면 트루
//    $('#replycontext').keyup(function() {//텍스트를 입력하면 실행되는 keyup함수
//    	$('#btnModReply').show();
//      if ($('#replycontext').val() == editedReplytext)		//만약에 변경된게 수정된 텍스트랑 다르면 수정버튼 보여준다.
//    	  $('#btnModReply').hide();	
//    });
//Archive
//수정할때 수정버튼이 수정했을때만 나오게 지우면 사라지게 0908 완료

//Todo 수정 에러