	const $fileDrop = $('div.fileDrop');

	let  gUri = window.location.pathname,
		 gIsRegister = gUri.indexOf('/register') !== -1, //없으면 -1인가보다.
		 gIsUpdate = gUri.indexOf('/update') !== -1,
		 gIsEditing = gIsRegister || gIsUpdate,	// 쓰기, 수정에서만 삭제버튼이 보인다.
		 gIsDirect = false;
	
	$fileDrop.on('dragover dragenter', (evt) => 	//드래그오버, 드래그엔터 : 드래그가 진입했을때
	{
		evt.preventDefault();
		$fileDrop.css("border", "1px dotted grey");
	});

	$fileDrop.on('dragleave', (evt) => {
		evt.preventDefault();
		$fileDrop.css("border", "1px solid black");
	});

	$fileDrop.on('drop', (evt) => 
	{
		evt.preventDefault();	//원래의 브라우저의 이벤트 기능 막는다.
			let files = evt.originalEvent.dataTransfer.files;
			console.log("upload.js drop >>>> ", evt.originalEvent.dataTransfer.files);
			$fileDrop.css("border", "none");
			//$fileDrop.html(files[0].name);		//첫번째 파일이름으로 바꾸기
			$("#ajax_file").prop("files", evt.originalEvent.dataTransfer.files);	// prop 드롭된 값을 주는것 파일이라는 값으로 뒤에evt를 줌
			$('#form_attach').submit();
	});
	
	const $percent = $('#percent'),
		   $status = $('#status');

	let gUpFiles = [];	//배열로 담아서 기존파일 유지
	$('#form_attach').ajaxForm
	({
		beforeSend : function()
		{
			let f =$('#ajax_file').val();
			console.info("upload.js ajaxForm beforeSend>>>", f);
			if(!f) return false;
			$status.empty();
			$percent.html('0%');
		},
		uploadProgress: function(event, position, total, percentComplete){
			$status.html('uploading...');
			$percent.html(percentComplete + '%');
		},
		complete : function(xhr)  //0927 xhr 서버에서 보내주는 값 이상 해결: ajax를 컨트롤러에 넣어줌.
		{	//10-01 멀티파일
			console.info("upload js xhr>>>>>>", xhr);	//xhr널찍힘
			let resJson = xhr.responseJSON;		//array
			if( xhr.status !== 201 )	//업로드가 실패했으면 컨트롤러 CREATE는 200번 아니라 201번
				{
					alert("업로드 에러,  (" +  resJson[0] + ")");	 //1002뜹니다
					return;
				}
			
			console.info('Complete>>>>', resJson);
			resJson.forEach ( rj =>
			{
				gUpFiles.push(jsonData);	//배열에 파일 하나씩 푸쉬
			});
			$status.html(resJson.length +  ' Uploaded');
			renderHbs('template', { upFiles : gUpFiles }, 'div');
			//tmpid, gUpFiles, div(안넣어도 기본값) 넣어준다.
		}
	});
	//uploadedFiles에서 호출
	function deleteFile(fullName, bno)	//1003 
	{
		let fileInfo = getFileInfo(fullName),	//파일 정보 가져오기
			 url = "/deleteFile?fileName=" + fullName;	//url만들기
		console.info("bno>>>>>>>>> ", bno)
		
		if(!confirm("삭제된 파일은 복구되지 않습니다."))
			return;
			
		if(bno)	//bno가 있으면 bno를 달고간다.
			url += "&bno=" + bno;	//파라메터가 여러개있을때 &(and? n? percent)?
		
		sendAjax(url, (isSuccess, res) => 
		{
	        if (isSuccess)
	        {
	            alert(fileInfo.fileName + " 삭제");
	            
	            $('li#' + fileInfo.fileId).remove();	 // dom(display이미지) remove	
	           
	            let tmpIdx = -1;		// 0을주면 나중에 0번째 처리할 수 있기때문에 0을 주면 안된다. arr.indexOf에 없는 값을 넣으면 -1을 리턴해준다.
	            
	            //루프, forEach : 메모리 영역부분 하나씩 처리해라 ex)서랍의 번호 uf, idx 내용물 꺼내 각각 uf, idx 넣어줌
	            gUpFiles.forEach( (uf, idx) =>	
	            {
	            	if(uf.fullName === fullName)	// uf(제이슨)의 풀네임과 지우고자하는 풀네임을 비교한다.
	            		tmpIdx = idx;	//임시 인덱스에 서랍장 번호를 담아 0, 1, 2....
	            });
	            gUpFiles.splice(tmpIdx, 1);	//서랍장번호로부터 하나만 지워 = 자기만 지운다.
	        }
	        else
	        {
	            console.debug("Error on deleteFile >>", res);
	        }
	    }, 'DELETE');
	}

	function checkImageType(fileName)	
	{	//이미지인지 판단
		let pattern = /jpg$|png$|gif$/i; // ^시작 , $끝,  i 대소문자 구분 X
		return fileName.match(pattern);
	}

	function getFileInfo(fullName)	//0928이 풀네임에서 정보뽑음
	{
		let fileName, imgsrc, getLink, fileLink
		let jsonData = getFileInfo(rj),
		//form id가 form_attach인놈 밑에 input아이디가 isdircet인놈 $isdirect $붙으면 DOM DOM은 배열
				$isdirect = $("#form_attach input#isdirect")	,
		// $isdirect가 있고 길이(셀렉트된 자료)가 있으면 $isdirect의 val(값)을 넣어주고 값이있으면 true
				isdirect = $isdirect && $isdirect.length && $isdirect.val() == "true";
		
		isdirect = isdirect ? true : gIsDirect;
		
		const uphost = window.location.protocol + "//" + window.location.hostname;
		
		if (checkImageType(fullName))
		{
			console.info("uplode.js getFileInfo IMAGE>>>");
			if(isdirect)
				imgsrc = uphost + "/uploads" + fullName;
			else
				imgsrc = "/displayFile?fileName=" + fullName;
			
			fileLink = fullName.substr(14); // /2018/09/28/s_ 유니크 아이디랑 원본파일이름 붙음
			let front = fullName.substr(0,12), // /2018/09/28/ 년월일까지
					end = fullName.substr(14);
			getLink = "/displayFile?fileName=" + front + end; //원본파일 보기링크
		}

		else //이미지가 아니면 이 자리에 아이콘을 보여주겠다.
	  	{
			imgsrc = "/resources/dist/img/file_icon3.jpg";	//아이콘 주소
			fileLink = fullName.substr(12);	//원본 파일명
			getLink = "/displayFile?fileName=" + fullName;	//원본파일 경로
		}
		
		if (isdirect)
			getLink += "&isdirect=true";
		
		//실제 파일명
		fileName = fileLink.substr(fileLink.indexOf('_') + 1);
		let fileId = fileLink.substr(0, fileLink.indexOf('_'));	//1001 
		//console.info("register fileID >>", fileId);
		
		return{		// 띄어쓰면 오류난다 ㅇㅓ이 핸들바에 들어갈 야들
			fileName: fileName,	
			imgsrc: imgsrc,
			getLink: getLink,
			fullName: fullName,
			fileId : fileId,
			isEditing : gIsEditing
		};
	}	///---------------------------- getFileInfo 끝