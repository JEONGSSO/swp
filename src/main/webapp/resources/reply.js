let gbno = 0,	
	gIsEdit = false;
	gRno = 0,
	gPage = null;
	gReplytext = null;		//0913 수정버튼 삭제할때 만듦 
	
const	REGIST_URL = "/replies/";

function replylistPage(page, bno) {		//0911 수업
	gbno = bno || gbno;	//bno 없으면 gbno
    gPage = page || gPage || 1;
    listUrl = "/replies/all/" + gbno + "/" + gPage;

    sendAjax(listUrl, (isSuccess, res) => {	//res = responsetext 페이지메이커랑 뭐랑있음.
        if (isSuccess) {
             res.pageData = makePageData(res.pageMaker);
        	 res.currentPage = gPage;	//pageData 삭제하고 액티브 나오게 했다.
//        	 console.debug("디버그",res.pageData);
        	 console.debug(">>>", res)
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
    	//페이지 메이커에 prev 있으면
    if (pageMaker.prev) {	
    	pageData.prevPage = pageMaker.startPage - 1;
    	//페이지 데이터 안에 prev는 page메이커 스타트 페이지 -1
    }

    for (let i = pageMaker.startPage ; i <= pageMaker.endPage ; i++) {
        pageData.pages.push(i); //pageData.pages는 위에 pages [] 푸쉬는 넣는거  
    }
    if(pageMaker.next){
    	pageData.nextPage = pageMaker.endPage + 1;
    }
//    pagaData.delim = "|"; 
    
    return pageData;
}	//0911 수업

function editReply(rno, replyer, replytext){	//수정인지 등록인지 계산하는 함수	
//	console.debug("editReply " + rno, replyer, replytext)
	event.preventDefault();	//# 눌렀을 때 작동 안되게
	gIsEdit = !!rno; //rno가 없거나 공백이거나 스페이스바거나 두루뭉술 gisEdit 만들
	gRno = rno;
	gReplytext = replytext;
	
	renderHbs('myModal', {
		gIsEdit : gIsEdit,
		replyer : replyer,
		replytext : replytext
	}, 'div');	//(tmpid, jsonData, tag)를 각각 넣어준다.
	
	$('#myModal').modal();	//모달 쇼 디폴트 쇼
	 $('#btnReplyAdd').hide();
}

function closeMod() { //댓글 창 지우는 것
	gRno = 0;
	gReplytext = null;	
//	$('#replyer').val('');	//작성자 지워주기
//	$('#replytext').val('');	//텍스트 지워주기 
	$('#myModal').modal('hide');	//모달 숨김
	}

function save() {	//0912
    let jsonData = getValidData($('#replyer'), $('#replytext'));	//수정 등록 경계를 허물어서 바꿈
    let url = gIsEdit ? REGIST_URL + gRno : REGIST_URL,		//등록인지 수정인지 gIsEdit이 true면  앞 : false면 뒤
    	method = gIsEdit ? 'PATCH' : 'POST';
//    gReplytext = $(replytext).val();
//    console.log("지리플텍스트 >>>" + gReplytext);
    if(!gIsEdit){ jsonData.bno = gbno;}
    
    sendAjax(url, (isSuccess, res) => { //url, is
        if (isSuccess) {
        	let resultMsg = gIsEdit ? gRno + "번 댓글이 수정되었습니다." 
        			: "댓글이 등록되었습니다.";
            alert(resultMsg);
            toggleEditBtn();
           let result = gIsEdit ? gPage : 1;	//결과는 gIsEdit 수정 이면 ~~ 아니면 1
            replylistPage(result);//0912 수정 후 페이지 유지.
            closeMod();										
    } 	else {
            console.debug("Error on registerReply>>", res);
        }
    }, method, jsonData);
}

function toggleEditBtn() {	//0913 수정
	let $replyer= $('#replyer'),
		$replytext = $('#replytext'),
		replyer = $replyer,
		replytext = $replytext.val();
	
	 if(event.keyCode === 13)							
			console.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
      if (replyer && replytext && replytext != gReplytext)	
    	  $('#btnReplyAdd').show();
      else
    	  $('#btnReplyAdd').hide();
    };
//  $('#replytext').keyup(function() {//텍스트를 입력하면 실행되는 keyup함수

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

    sendAjax("/replies/" + gRno, (isSuccess, res) => {
        if (isSuccess) { 
            alert(gRno + " 번 댓글이 삭제완료되었습니다.");
            console.log();
            replylistPage(gPage); //댓글 페이지 유지
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

const readReply = rno => new Promise( (resolves, rejects) => 	//0914 수업 아예 타지 않음.	
{
	sendAjax("/replies/" + rno, (isSuccess, res) =>  //예측 값
	{
		if(isSuccess)
			{
//				console.debug("isSuccess" + res); 
				resolves(res);
			}
			
		else
			rejects(Error(res));
	});
});

function readRno(){	//TODO
	
}

//    return str.replace(/[\n\r\t]/g, '').trim(); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈	트림은 공백제거

//Archive
//수정할때 수정버튼이 수정했을때만 나오게 지우면 사라지게 0908 완료

//Todo
// 몇몇 댓글들 안들어가진다.