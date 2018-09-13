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
	assert.deepEqual(resReal, resExpect, "makePageData 통과");
});


 const assignentExpected = read(75); // 이거 db로가져온다 컨ㅌ크롤러 부터 쿼리까지 다만들어야 db예측값 todototodo

 QUnit.test("Test listPage() 첫 페이지", (assert) => { // 리스트페이지 그대로넣어
 let page = 1;
 let listUrl = "/replies/all/" + 3 + "/" + 1; // 3은 글번호 1은 페이지 번호
 console.debug("url >>> ", listUrl);
	
 const done = assert.async(); // 다 끝날때까지 기다려

	 sendAjax(listUrl, (isSuccess, res) => { // res = responsetext 페이지메이커랑
												// LIST 맵 (댓글목록 10개).
	 console.debug("listPage : res >>", res);
	 assert.ok(isSuccess, "Ajax 성공!");
	 if(isSuccess) {
		 res.currentPage = page;
		 res.pageData = makePageData(res.pageMaker);
		 assert.equal(res.list.length, 10, "페이지 10개!");
		 assert.deepEqual(read(res.list), assignmentExpected,"성공");
		 assert.deepEqual(res.list[0],assignmentExpected, "listPage 통과");
	 	}
	 	done(); // 끝났음을 알려준다.
	 	});
	 });
//
// //과제 20페이지??
// //Todo 과제 텍스트엔터누르면 수정 되게끔 작성자에서 엔터누르면 댓글내용으로 가게끔
// //Todo 예상된 rno랑 실제 rno를 비교한다(db에서 직접가져오기 ) -> 컨트롤러 -> 서비스 -> ~~~~~ 
// //DELIM 메이크 페이지 수정하고싶을때 테스트를먼저하고 에러가뜨겠지, 그걸 고쳐가면서 개발한다.
