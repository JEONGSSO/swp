const BNO = 3;	//bno 전역변수로 잡음 const는 상수

function registerReply() {
    const REGIST_URL = "/replies";
    let jsonData = getValidData($('#newReplyWriter'), $('#newReplyText'));
    if (!jsonData) {
        return;
    }

    jsonData.bno = BNO; //제이슨 데이터bno에 bno담음

    sendAjax(REGIST_URL, (isSuccess, res) => { //url, is
        if (isSuccess) {
            alert("등록이 완료 되었습니다.");
            listPage();
        } else {
            console.debug("Error on registerReply>>", res);
        }
    }, 'POST', jsonData);
}

let workingReplyText = "", //현재 리플의 텍스트.
    $workingReply = null, //현재 리플은 비어있다
    workingRno = 0; //현재 떠있는 리플 번호.

function editReply() { //jsp에서 onclick 이름과 같다.	오류 k.type.toUpperCase is not a function 오타오류였음

    if (!confirm("수정?")) return;	//수정여부 물어보고 수정이 yes가 아니면 (no면) 종료 

    let jsonData = {
        replytext: editedReplytext
    } //제이슨 데이타안에 replytext는 editedReplytext를 담는다.

    sendAjax("/replies/" + workingRno, (isSuccess, res) => {    //함수 sendAjax 실행
        if (isSuccess) {
            alert(workingRno + "수정완료");
            listPage(); //댓글 목록 -> 페이지 0906 수정
            $workingReply.find('span').text(editedReplytext);   //$workingReply의 span에서 텍스트를 가져온다 그 텍스트는 editedReplytext
            closeMod();	//댓글 닫기
        } else {
            console.debug("update error>>", res);
        }
    }, 'PUT', jsonData);	//순서 바뀌면 OBJ오류 출력됐었다. 하지만 그게 정상 sendAjax 0907 14:00
}

function removeReply() {
    if (!confirm("삭제하시겠습니까?")) return;

    sendAjax("/replies/" + workingRno, (isSuccess, res) => {
        if (isSuccess) { //
            alert(workingRno + "번 댓글이 삭제완료되었습니다.");
            listPage(); //댓글 목록
            closeMod(); //댓글창 닫기
        } else {
            console.debug("Error on removeReply>>", res);
        }
    }, 'DELETE');
}

function closeMod() { //댓글 창 지우는 것
    let $modDiv = $("#modDiv"); //변수 modDiv 에 제이쿼리 modDiv를? 선언
    workingRno = 0; // 현재 실행중 rno 삭제 깔끔하게 0으로 만들어주는게 좋ㄷㅏ.
    $('#replytext').text(''); //제이쿼리 아이디가 replytext중에 text들을 삭제
    $modDiv.hide('slow'); //느리게 사라짐
}

function listPage(page) {
    page = page || 1;
    listUrl = "/replies/all/" + BNO + "/" + page;

    sendAjax(listUrl, function (isSuccess, res) {	//res
        if (isSuccess) {
            let data = res.list,
                pageMaker = res.pageMaker;
            let str = ""; //바뀔 수 있음
            //$(data).each((a,b) => {console.log(a,b)});
            data.forEach(
                (d) => {
                    //str += '<li data-rno="' + d.rno + '" class="replyLi">' + d.replytext + '<button>수정</button>' + '</li>';
                    str += `<li data-rno= "${d.rno}" class= "replyLi">
                            <span>${d.replytext}</span>
                            <button onclick=modClicked(this) class="point">수정</button>
                            </li>`;
                }
            );
            $('#replies').html(str);
            printPage(pageMaker);
        }

    }//fn여기까지
    );
}

function getValidData($replyer, $replytext) {
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

    $.ajax(options).always((responseText, statusText, ajaxResult) => {		 // 다른곳 res = responseTextmap.put("list", list);		map.put("pageMaker", pagemaker); 담아온다
        let isSuccess = statusText === 'success'; //ajax 호출 성공 여부
        fn(isSuccess, responseText);
        if (!isSuccess) {
            alert("오류가 발생하였습니다. (errorMessage:" + responseText + ")");
        }
    })
}

function showJson() {
    let result = [];
    $('#replies li').each((idx, li) => {
        let $li = $(li),
            rno = $li.data('rno')
        replyer = $li.data('replyer')
        replytext = truncSpace($li.text());
        result.push({
            rno: rno,
            replyer: replyer,
            replytext: replytext
        })
    })
    result = JSON.stringify(result, null, '  ');
    console.log(result);
}

function movCenterModDiv() {
    $modDiv = $('#modDiv');
    $modDiv.css({
        'margin-left': $modDiv.width() / 2 * (-1)
    });
    $modDiv.css({
        'margin-top': $modDiv.height() / 2 * (-1)
    });
}

function modClicked(btn) { //댓글 옆 수정 버튼을 클릭하면  
    let $btn = $(btn),
        $reply = $btn.parent(), //$reply에 btn의 부모를 가르킴 이돟ㅇ
        rno = $reply.data('rno'); //rno는 
    replytext = truncSpace($reply.find('span').text()); //replytext는 truncSpace안에 span을 찾아 text를 가져온다
    $('#replycontext').val(replytext);  //id replycontext 안에 val에서 텍스트를 가져와 담음.
    $('#modDiv').show('slow');
    workingRno = rno;
    workingReplyText = replytext;
    $workingReply = $reply;
    hideBtn();		
}

function hideBtn() {
    let editedReplytext = $('#replycontext').val();	//수정된 텍스트는 제이쿼리 id	replycontext 에서 val로 가져옴	
    // $('#btnModReply').hide()    //수정버튼이 사라져있어야 하는데 아직은 안된다	//디스플레이 none으로 대체
    //   $('#replycontext').change(function() { alert("content changed"); });	//체인지 함수.
//    console.log($('#replycontext').val() == editedReplytext); //true 값이랑 비교하면 트루
    $('#replycontext').keyup(function() {//키를 누르면 실행되는 함수 인것 같다.
    	$('#btnModReply').show();
      if ($('#replycontext').val() == editedReplytext)		//만약에 변경된게 수정된 텍스트랑 다르면 수정버튼 보여준다.
    	  $('#btnModReply').hide();	
    });
}

function printPage(pm) {		//0907 오후 수업	pm은 페이지 메이커에서 받아받아온거 매개변수

    console.log(pm);
    let str = "",
        tmpPage = 0;	//임시 변수
    let currentPage = pm.criteria.page;

    if (pm.prev) {
        tmpPage = pm.startPage - 1;
        str = `<li><a href="#" onclick = "listPage(tmpPage)" data-page = "${tmpPage}">&lt;&lt;</a></li>`;
    }

    for (let i = pm.startPage; i <= pm.endPage; i++)
        str += `<li><a href="#" onclick = "listPage(${i})" class="${currentPage === i ? "active" : ""}" data-page = "${i}">${i}</a></li>`;
    //class="${currentPage === i ? "active" : ""}" currentPage가 i 랑 같으면 클래스가 "active" 아니면 "null" "?"삼항 연산자
    if (pm.next) {
        tmpPage = pm.startPage + 1;
        str += `<li><a href="#" onclick = "listPage(tmpPage) data-page = "${tmpPage}">&gt;&gt;</a></li>`;
    }
    $('ul.pagination').html(str);	//ul안에 pagination찾음
}

var truncSpace = function (str) {
    if (!str) {
        return "";
    }
    return str.replace(/[\n\r\t]/g, '').trim(); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈	트림은 공백제거
};

//Archive
//수정할때 수정버튼이 수정했을때만 나오게 지우면 사라지게 0908 완료