<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fileupload</title>
</head>
<body>
	<form id = "form1" action="uploadForm" method = "POST" enctype = "multipart/form-data">
		<input type="file" name="file" ><input type='submit'>
	</form>
	savedFileName : ${ savedFileName } 	
	
	<hr />
	
	<form id = "form2" action="uploadForm" method = "POST" enctype = "multipart/form-data" target = ifr>
		<input type="hidden" name = "type" value = "ifr"/>
		<input type="file" name="file" >
		<input type='submit' value = "iframe으로 제출">
	</form>
		IFR-savedFileName : <sapn id="upfile"></sapn>
	<hr />
	<iframe frameborder="0" width = "0" height = "0" name = "ifr"></iframe>
	
	<div class = "fileDrop">드롭 여기다</div>
	<div class = "uploadedList"></div>
	
	<form id = "form3" action="uploadForm" method = "POST" enctype = "multipart/form-data" >
		<input type="hidden" name = "type" value = "ajax"/>
		<input type="file" name="file" />
		<input type='submit' value = "ajax로 제출"/>
	</form>
	<div id = "percent">0%</div>
	<div id = "status">ready</div>
	ajaxsavedFileName : <sapn id="ajax_file"></sapn>
	
	<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script src="/resources/plugins/jQuery/jQuery.form.min.js"></script>
	
	<script>
		window.setUploadedFile = (filename) =>
		{
			document.getElementById('upfile').innerHTML = filename;
			document.getElementById('form2').reset();
		};
	</script>
	
	<script>
		const $fileDrop = $('div.fileDrop'),	
				$uploadedList = $('div.uploadedList');	//달러는 변수명도 달러로
		
		$fileDrop.on('dragover dragenter', (evt) => {
			evt.preventDefault();
			$fileDrop.css("border", "1px dotted green");
		});
		
		$fileDrop.on('drop', (evt) => {
			evt.preventDefault();
			let files = evt.originalEvent.dataTransfer.files;
			console.debug("drop >> ", evt.originalEvent.dataTransfer.files);
			$fileDrop.css("border", "none");
			//$fileDrop.html(files[0].name);
			$("ajax_file").prop("files", evt.originalEvent.dataTransfer.files);	// prop 객체 선택?
			$('#form').submit();
		});
		
		$fileDrop.on('dragleave', (evt) => {
			evt.preventDefault();
			$fileDrop.css("border", "none");
		});
		
		const $percent = $('#percent'),
				$status = $('#status'),
				$uplist = $('div.uploadedList');
				
	$('#form3').ajaxForm
	({
		beforeSend : function()
		{
			$status.empty();
			$percent.html('0%');
		},
		uploadProgress: function(event, position, total, percentComplete){
			$status.html('uploading...');
			$percent.html(percentComplete + '%');
		},
		complete : function(xhr) //0927
		{
			console.debug('complete', xhr);
			
			let originalName = getOriginalName(xhr.responseText);
			//console.debug('originalName>>>>', originalName);
			
			let uf = '<a href="/displayFile?fileName=' + xhr.responseText + '">' + originalName +  '</a>';
			let ocd = "deleteFile('" + xhr.responseText + "')";	//쌍따옴표랑 외따옴표(?) 같이쓸때 헷갈리면 변수로 나누기
			uf += '<a href="javascript:;" onclick="' + ocd + '">X</a>';		//삭제 X 버튼 
			$uplist.append('<div>' + uf + '</div>');	//uf를 div로 감싼다.
	        $status.html(uf +  'Uploaded');
	    }
	});
	
	function deleteFile(FileName) {
	    sendAjax("/deleteFile?fileName=" + fileName, (isSuccess, res) => {
	        if (isSuccess) 
	        {
	            alert(fileName + " 삭제");
	            let a = $('div.uploadedList div a [href="/displayFile?fileName=' + fileName + '"]');
           		console.log("aaaa>>", a);
           		a.parent().remove();	//a태그의 부모를 지운다.
	        } 
	        else 
	        {
	            console.debug("Error on deleteFile >>", res);
	        }
	    }, 'DELETE');
	}
	
	function getOriginalName(fileName)	//0927
	{
		let ret = fileName.substring(fileName.indexOf('_') + 1);
		//console.debug("ori>>", ret) 	//찍힘
		if(checkImageType(fileName))
			{
				ret = ret.substring(ret.indexOf('_') + 1);
				console.debug('IMAGE>>>>>>>');
				return '<img src="/displayFile?fileName=' + fileName + '" alt="' + ret + '">';	//이미지라면 섬네일
			}
		else
			return ret;		//이미지가 아니라면 원본파일
	}
	
	function checkImageType(fileName) 
	{
		let pattern = /jpg$|png$|gif$/i;		//이미지 파일 인것만 i : 대소문자 구분없이
		return fileName.match(pattern);
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
	</script>
		
	<c:if test = "${type eq 'ifr'}">	<!-- 만약에 ifr 업로드면 이 구문을 타라 -->
		<script>
			console.debug(">>>>> ifr script")
			parent.setUploadedFile('${savedFileName}');
		</script>
	</c:if>
	
	
	
</body>
</html>