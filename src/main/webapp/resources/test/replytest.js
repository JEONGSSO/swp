//Todo
const pageMaker = {
		  "startPage": 1,
		  "endPage": 10,
		  "prev": false,
		  "next": true,
		  "criteria": {
		    "page": 1,
		    "perpageNum": 10,
		    "searchType": null,
		    "keyword": null,
		    "perPageNum": 10,
		    "pageStart": 0
		  }
};

QUnit.test("Test pageMaker", (assert) => {
	assert.deepEqual(1, 1, "패스");
	let resReal = makePageData(pageMaker),
		resExpect = {
			"prevPage" : 0,
		 	"pages" : [
		 		1,
				2,
				3,
				4,
				5,
				6,
				7,
				8,
				9,
				10
			],
		 	"nextPage" : 11
		};
	assert.deepEqual(resReal, resExpect, "makePageData 통과");		//0913 통
});

const gResExpect = //예측 값
{	
		"nextPage" : 11,
		"pages" : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
		"prevPage" : 0
} 

 QUnit.test("Test listPage() 첫 페이지", (assert) => 	// 리스트페이지 그대로넣어
 { 
	 let page = 1;
	 let listUrl = "/replies/all/" + 3 + "/" + 1; // 3은 글번호 1은 페이지 번호
	 console.debug("url >>> ", listUrl);
		
	 const done = assert.async(); // 다 끝날때까지 기다려
	
		 sendAjax(listUrl, (isSuccess, res) =>	 // res = responsetext 페이지메이커랑
			{
				 console.debug("listPage : res >>", res);
				 assert.ok(isSuccess, "Ajax 성공!");
				 
				 let isDone = false;
				 try 
				 {
					 if(isSuccess) 
					 {
						 res.currentPage = page;
						 res.pageData = makePageData(res.pageMaker);
//						 console.debug("rerererers" + res.list.length);  10개 잘넘어 옴
						 assert.equal(res.list.length, 10, "페이지 10개!");
						 assert.deepEqual(res.pageData, gResExpect ,"성공");
						 
						 let firstReply = res.list[0];
						 
						 readReply(firstReply.rno).then	// 0914 여기서 에러
						 (
								 success =>	// 함수명
								 {
									 assert.deepEqual(firstReply, success, "리플 데이터 통과");
									 done();
									 isDone = true;
								 },
								 
								 error =>
								 {
									 console.error("에러 온 리드", error);
									 done();
									 isDone = true;
								 }
						);	//then end
						 
				 }			// if end	 
					 else
						 	throw new Error ("list ajax fail!!")
				 }	// try end
				 catch(Error)
				 {	
					 if (!isDone)
						done(); // 끝났음을 알려준다. 쓰루하고 캐치안하면 언카우치
				 }
	 	});	//sand ajax end
 });	//Qunit end
 
 
// //과제 20페이지??
// //Todo 과제 텍스트엔터누르면 수정 되게끔 작성자에서 엔터누르면 댓글내용으로 가게끔
// //Todo 예상된 rno랑 실제 rno를 비교한다(db에서 직접가져오기 ) -> 컨트롤러 -> 서비스 -> ~~~~~ 
// //DELIM 메이크 페이지 수정하고싶을때 테스트를먼저하고 에러가뜨겠지, 그걸 고쳐가면서 개발한다.
