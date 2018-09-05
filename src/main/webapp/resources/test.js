function registerReply(){
    const BNO = 3;
    const REGIST_URL = "/replies";
    let $replyer = $('#newReplyWriter'),
        $replytext = $('#newReplyText'),
        $errorFocus = null;
    
    let replyer = $replyer.val(),
        replytext = $replytext.val(),
        errorMsg = "";
    
    if(!replyer){
        errorMsg = "작성자를 입력하세요.";
        $errorFocus = $replyer;
    }else if(!replytext){
        errorMsg = "내용을 입력하세요";
        $errorFocus = $replytext;
    }
    
    if(errorMsg){
        alert(errorMsg);
        $errorFocus.focus();
        return;
    }
    
    let jsonData = {
            bno      : BNO,
            replyer  : replyer,
            replytext: replytext
    };
}

/*
 * function listAll(){ var bno = 6, listUrl = "/replies/all/" + bno;
 * $.getJSON(listUrl, function(data, b, c){ console.log(">> data=", data, ",
 * b=", b, ", c=", c); c.always(function(){ console.log(c.status); }) }); }
 */

function listAll(){
    const bno = 3;
    const page = 1;
    listUrl = "/replies/all/" + bno + "/" + page;
    $.getJSON(listUrl, (data, b, c) => {
        console.log(">> data=", data, ", b=", b, ", c=", c);
        // $(data).each((a,b) => {console.log(a,b)});
        
        let str = ""; // 바뀔 수 있음
        data.list.forEach(
            (d) => {
// str += '<li data-rno="' + d.rno + '" class="replyLi">' + d.replytext +
// '</li>';
                str += `<li data-rno="${d.rno}" class = "replyLi"> ${d.replytext}
                			<button oneclick=modClicked(this) class = "point">수정</button>
                			</li>`;
            }
        );
        $('#replies').html(str);
        
    });
}

function sendAjax(url, fn, method, jsonData)	// url, 함수, 메소드, jsondata
{
	let options = {
						method : 'GET' || method,	// 메소드 get, post, put 등이 있다.
						url : url,
						contentType: 'application/json'		//내가보내는 데이터가 json이라고 알려준다.
					   };
	if(jsonData)	
			options.data = json.stringify(jsonData);	// 만약에 jsonData가 있으면 fn에서 스트링으로 받아올거다. 
	}
		//	$.ajax(options).always(function(responseText, statusText, ajaxRes){})  간략화 =>
	 $.ajax(options).always((responseText, statusText, ajaxRes) =>
	 {		// ajax 옵션.언제나 실행 (리스폰, 상태, ajax 결과
		 let isSuccess = statusText === 'success';
		 fn(isSuccess, responseText);
		 
		 if(!isSuccess)	// 실패
			 alert("오류가 발생하였습니다.");
		 else
			 alert("등록 되었습니다.");
});


function makeCenterModDiv(){
	let $mod = $('#ModDiv')		// let는 변할 수 있는 값
	$mod.css('margin-top', $mod.height() / 2 * 1)
}

function modClicked(btn) {
	console.log("aaaaaaaaaaaaaaaaaaaa>>", btn);
	let $btn = $(btn),
		$reply = $btn.parent(),
		rno = $reply.data('rno');
	console.log("QQQQQQQQQQ>>", $btn, $reply, rno)
}

