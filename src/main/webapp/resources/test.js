
function registerReply(){
    const BNO = 3;
    const REGIST_URL = "/replies";
    
    let jsonData = getValidData($('#newReplyWriter'), $('#newReplyText'));
    if(!jsonData){
        return;
    }

    jsonData.bno = BNO; //제이슨 데이터bno에 bno담음

sendAjax(REGIST_URL, (isSuccess, res) => {  //url, is
    if(isSuccess){
    alert("등록이 완료 되었습니다.");
    listAll();
        }else{
            console.debug("Error on registerReply>>",res);
        }
    } , 'POST', jsonData);
}

let workingReplyText =" ",
    $workingReply = null;
let workingRno = 0;
function editReply(){

    let editedReplytext = $('#replytext').val();
    if (editedReplytext === workingReplytext) {
        alert("내용 수정 안됨");
        return;
    }
}

    let jsonData = { replytext : editReply }
    
    sendAjax("/replies/"+workingRno, (isSuccess, res) => {  
        if(isSuccess){              //
            alert(workingRno + "수정완료");
            listAll();  //댓글 목록
            $workingReply.find('span').text(editedReplytext);
            closeMod();
        } else{
            console.debug("update error>>",res);
        }
    }, 'PATCH', jsonData);



function removeReply(){
    if(!confirm("Are u sure??")) return;
    
    sendAjax("/replies/"+workingRno, (isSuccess, res) => {  
        if(isSuccess){              //
            alert(workingRno+"번 댓글이 삭제완료되었습니다.");
            listAll();  //댓글 목록
            closeMod(); //댓글창 닫기
        } else{
            console.debug("Error on removeReply>>",res);
        }
    }, 'DELETE');
}

function closeMod(){		    //댓글 창 지우는 것
    let $modDiv = $("#modDiv"); //변수 modDiv 에 제이쿼리 modDiv를? 선언
    workingRno = 0;             // 현재 실행중 rno 삭제
    $('#replytext').text('');   //제이쿼리 아이디가 replytext중에 text들을 삭제
    $modDiv.hide('slow');           //느리게 사라짐
}

function listAll(){
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

function getValidData($replyer, $replytext){
    let errorFocus = null,
        replyer = $replyer.val(),
        replytext = $replytext.val(),
        errorMsg = "";

    if(!replyer){
        errorMsg = "작성자를 입력하세요.";
        $errorFocus = $replyer;
    } else if(!replytext){
        errorMsg = "내용을 입력하세요";
        $errorFocus = $replytext;
    }
    
    if(errorMsg){
        alert(errorMsg);
        $errorFocus.focus();
        return;
    }
    
    return {replyer: replyer, replytext: replytext};
}

function sendAjax(url, fn,  method, jsonData){
    let options = {
                        method: method,
                        url: url,
                        contentType: "application/json"
                   };
    //jsonData가 있을 때만 data : JSON.stringify(jsonData) 추가
    if(jsonData){
        options.data = JSON.stringify(jsonData);
        console.log(options);
    }
    
    $.ajax(options).always((responseText, statusText, ajaxResult) =>{
        let isSuccess = statusText === 'success'; //ajax 호출 성공 여부
        fn(isSuccess,responseText);
        if(!isSuccess){
            alert("오류가 발생하였습니다. (errorMessage:" + responseText + ")");
        }
    })
}

function showJson(){
    let result = [];
    $('#replies li').each ( (idx, li) => {
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

function movCenterModDiv(){
    $modDiv = $('#modDiv');
    $modDiv.css({'margin-left':$modDiv.width()/2*(-1)});
    $modDiv.css({'margin-top':$modDiv.height()/2*(-1)});
}


// function modClicked(btn){   //버튼을 누르면
//     let $btn = $(btn),      //btn을 
//         $reply = $btn.parent(),
//         rno = $reply.data('rno');
//         replytext = truncSpace($reply.find('span').text()); 
//         $('#replycontext').val(replytext);
//         $('#modDiv').show('slow');
//         workingRno = rno;   //수정할때 수정버튼이 수정했을때만 나오게 지우면 사라지게
// }

function modClicked(btn){
    let $btn = $(btn),
    $reply = $btn.parent(),
    rno = $reply.data('rno');
    replytext = truncSpace($reply.find('span').text());
    $('#replycontext').val(replytext);
    $('#modDiv').show('slow');
    workingRno = rno;
    workingReplyText = replytext;
    $workingReply = $reply;
}

var truncSpace = function(str){
    if(!str){
        return "";
    }
    return str.replace(/[\n\r\t]/g,'').trim();
};
