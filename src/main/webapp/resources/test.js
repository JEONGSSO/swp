function registerReply() {
    const BNO = 3;
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

let workingReplyText = " "; //현재 리플의 텍스트.
let $workingReply; //현재 리플은 비어있다
let workingRno = 0; //현재 떠있는 리플 번호.

function editReply() { //jsp에서 onclick 이름과 같다.	오류 k.type.toUpperCase is not a function
	
	let editedReplytext = $('#replytext').val();	//수정된 텍스트는 제이쿼리 id	replytext 에서 val로 가져옴		
	if (editedReplytext === workingReplyText) {	//0907 미완성
														//수정된 텍스트랑 현재 텍스트랑 같으면 수정이 없는것이라 판단.
	alert("내용 수정 안됨");
	return;
	}
	
	if(!confirm("수정?")) return;	//수정여부 물어보고 수정이 yes가 아니면 (no면) 종료 
													    							
    let jsonData = {
        replytext: editedReplytext
    } //제이슨 데이타 replytext는 editedReplytext를 담는다.

    sendAjax("/replies/" + workingRno, (isSuccess, res) => {
        if (isSuccess) {
            alert(workingRno + "수정완료");
            listPage(); //댓글 목록
            $workingReply.find('span').text(editedReplytext);   //$workingReply의 span에서 텍스트를 가져온다 그 텍스트는 editedReplytext
            closeMod();	//댓글 닫기
        } else {
            console.debug("update error>>", res);
        }
    },jsonData , 'PUT');	//순서 바뀌면 OBJ오류 출력됐었다.
}

function removeReply() {
    if (!confirm("Are u sure??")) return;

    sendAjax("/replies/" + workingRno, (isSuccess, res) => {
        if (isSuccess) { //
            alert(workingRno + "s번 댓글이 삭제완료되었습니다.");
            listPage(); //댓글 목록
            closeMod(); //댓글창 닫기
        } else {
            console.debug("Error on removeReply>>", res);
        }
    }, 'DELETE');
}

function closeMod() { //댓글 창 지우는 것
    let $modDiv = $("#modDiv"); //변수 modDiv 에 제이쿼리 modDiv를? 선언
    workingRno = 0; // 현재 실행중 rno 삭제
    $('#replytext').text(''); //제이쿼리 아이디가 replytext중에 text들을 삭제
    $modDiv.hide('slow'); //느리게 사라짐
}

function listPage() {
    const bno = 3;
    const page = 1;
    listUrl = "/replies/all/" + bno + "/" + page;
    $.getJSON(listUrl, (data, b, c) => {
        console.log(">> data=", data, ", b=", b, ", c=", c);
        let str = ""; //바뀔 수 있음
        //$(data).each((a,b) => {console.log(a,b)});
        data.list.forEach(
            (d) => {
                //str += '<li data-rno="' + d.rno + '" class="replyLi">' + d.replytext + '<button>수정</button>' + '</li>';
                str += `<li data-rno= "${d.rno}" class= "replyLi">
                        <span>${d.replytext}</span>
                        <button onclick=modClicked(this) class="point">수정</button>
                        </li>`;
            }
        );
        $('#replies').html(str);
    });
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
        method: method, //메소드 get post put 등
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

function showJson() {
    let result = [];
    $('#replies li').each((idx, li) => {
        let $li = $(li),
            rno = $li.data('rno')
        replyer = $li.data('replyer')
        replytext = truncSpace($li.text()); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈
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

function modClicked(btn) { //버튼을 클릭하면 
    let $btn = $(btn),
        $reply = $btn.parent(), //$reply에 btn의 부모 참조? 사용?
        rno = $reply.data('rno'); //rno는 
    replytext = truncSpace($reply.find('span').text());
    $('#replycontext').val(replytext);
    $('#modDiv').show('slow');
    workingRno = rno;               
    workingReplyText = replytext;
    $workingReply = $reply; //수정할때 수정버튼이 수정했을때만 나오게 지우면 사라지게
}

var truncSpace = function (str) {
    if (!str) {
        return "";
    }
    return str.replace(/[\n\r\t]/g, '').trim();
};