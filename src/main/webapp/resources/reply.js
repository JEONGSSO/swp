let gbno = 0;

let workingReplyText = "", //현재 리플의 텍스트.
    $workingReply = null, //현재 리플은 비어있다
    workingRno = 0; //현재 떠있는 리플 번호.
	
function replylistPage(page, bno) {		//0911 수업
	gbno = bno || gbno;
    page = page || 1;
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

function registerReply() {
    const REGIST_URL = "/replies";
    let jsonData = getValidData($('#newReplyWriter'), $('#newReplyText'));
    
    if (!jsonData) {	//등록하면 안에 데이터 삭제 0909 
        return;
    }

    jsonData.bno = gbno; //제이슨 데이터bno에 gbno담음 0911
    
    sendAjax(REGIST_URL, (isSuccess, res) => { //url, is
        if (isSuccess) {
            alert("등록이 완료 되었습니다.");
            replylistPage(1); //등록완료되면 페이지 1로
            document.getElementById("newreg").reset(); //0909 new부분 폼으로 감싸서 완료. 입력창 비워주기
        } else {
            console.debug("Error on registerReply>>", res);
        }
    }, 'POST', jsonData);
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

function editReply() { //jsp에서 onclick 이름과 같다.	오류 k.type.toUpperCase is not a function 오타오류였음

    if (!confirm("수정?")) return;	//수정여부 물어보고 수정이 yes가 아니면 (no면) 종료 

    let jsonData = {
        replytext: editedReplytext = $('#replycontext').val()	//전역변수로 하니까 널값이 되어서 0909에 수정
    } //제이슨 데이타안에 replytext는 editedReplytext를 담는다.

    sendAjax("/replies/" + workingRno, (isSuccess, res) => {    //함수 sendAjax 실행
        if (isSuccess) {
            alert(workingRno + "수정완료");
            //replylistPage(); //댓글 목록 -> 페이지 0906 수정	0909 수정  1페이지 간거 수정
            $workingReply.find('span').text(editedReplytext);   //$workingReply의 span에서 텍스트를 가져온다 그 텍스트는 editedReplytext
            closeMod();	//댓글 닫기
        } else {
            console.debug("update error>>", res);후
        }
    }, 'PUT', jsonData);	//순서 바뀌면 OBJ오류 출력됐었다. 하지만 그게 정상 sendAjax 0907 14:00
}

let workingPage = 0;	//현재있는 페이지 

function removeReply() {
    if (!confirm("댓글을 삭제하시겠습니까?")) return;

//    workingPage = ${replylistPage}
    sendAjax("/replies/" + workingRno, (isSuccess, res) => {
        if (isSuccess) { 
            alert(workingRno + " 번 댓글이 삭제완료되었습니다.");
            workingPage = ($('.active').data().page);	//액티브에 데이타함수에 페이지 값
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

    if (!replyer) {
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

function modClicked(btn) { //댓글 옆 수정 버튼을 클릭하면  
    let $btn = $(btn),
        $reply = $btn.parent(), //$reply에 btn의 부모를 가르킴 이돟ㅇ
        rno = $reply.data('rno'); //rno는 
    replytext = truncSpace($reply.find('span').text()); //replytext는 truncSpace안에 span을 찾아 text를 가져온다
    $('#replycontext').val(replytext);  //id replycontext 안에 val에서 텍스트를 가져와 담음.
//    $('#modalBtn').show('slow');
    workingRno = rno;
    workingReplyText = replytext;
    $workingReply = $reply;
    toggleEditBtn();		
}

var truncSpace = function (str) {
    if (!str) {
        return "";
    }
    return str.replace(/[\n\r\t]/g, '').trim(); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈	트림은 공백제거
};

function toggleEditBtn() {
	let editedReplytext = $('#replycontext').val();	//수정된 텍스트는 제이쿼리 id	replycontext 에서 val로 가져옴
    // $('#btnModReply').hide()    //수정버튼이 사라져있어야 하는데 아직은 안된다	//디스플레이 none으로 대체
    //   $('#replycontext').change(function() { alert("content changed"); });	//체인지 함수.
	//    console.log($('#replycontext').val() == editedReplytext); //true 값이랑 비교하면 트루
    $('#replycontext').keyup(function() {//텍스트를 입력하면 실행되는 keyup함수
    	$('#btnModReply').show();
      if ($('#replycontext').val() == editedReplytext)		//만약에 변경된게 수정된 텍스트랑 다르면 수정버튼 보여준다.
    	  $('#btnModReply').hide();	
    });
}

//function movCenterModDiv() {
//$modDiv = $('#modDiv');
//$modDiv.css({
//  'margin-left': $modDiv.width() / 2 * (-1)
//});
//$modDiv.css({
//  'margin-top': $modDiv.height() / 2 * (-1)
//});
//}
//
//function closeMod() { //댓글 창 지우는 것
//let $modDiv = $("#modDiv"); //변수 modDiv 에 제이쿼리 modDiv를? 선언
//workingRno = 0; // 현재 실행중 rno 삭제 깔끔하게 0으로 만들어주는게 좋ㄷㅏ.
//$('#replytext').text(''); //제이쿼리 아이디가 replytext중에 text들을 삭제
//$modDiv.hide('slow'); //느리게 사라짐
//}
//Archive
//수정할때 수정버튼이 수정했을때만 나오게 지우면 사라지게 0908 완료

//function showJson() {
//let result = [];
//$('#replies li').each((idx, li) => {
//  let $li = $(li),
//      rno = $li.data('rno')
//  replyer = $li.data('replyer')
//  replytext = truncSpace($li.text());
//  result.push({
//      rno: rno,
//      replyer: replyer,
//      replytext: replytext
//  })
//})
//result = JSON.stringify(result, null, '  ');
//console.log(result);
//}

//Todo 댓글 목록 화살표 해보기
