<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
	
<%@include file="../include/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fileupload</title>
</head>
<body>

<form role="form" method="post">
	<div class="box-body">
		<div class="form-group">
			<label for="title1">제목</label> <input class="form-control"
				name="title" type="text" id="title1" placeholder="제목" />
		</div>

		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" name="content" id="content" cols="30"
				rows="3" placeholder="내용"></textarea>
		</div>

		<div class="form-group">
			<label for="writer1">작성자</label> <input class="form-control"
				type="text" id="writer1" name="writer" placeholder="Enter Writer" />
		</div>

		<!-- 0928 drag & drop  -->
			<div class="form-group">
				<label for="">여기다 떨구세요</label>
				<div class="fileDrop text-right">
					<div id = "percent">0%</div>
					<div id = "status">ready</div>
				</div>
			</div>
	</div>	<!-- body box 끝  -->
</form>


<form id = "form_attach" action="uploadAjax" method = "POST" enctype = "multipart/form-data">
	<input type="hidden" name = "type" value = "ajax"/>
	<input type="file" name="file" id="ajax_file" style="display:none;"/>
	<%-- <input type='submit' value = "ajax로 제출"/> --%>
</form>

	<div class="box-footer">
			<!-- 0928 첨부파일 -->
		<ul class="mailbox-attachments clearfix uploadedList">
			<script id="template" type="text/x-handlebars-template">
					{{#each upFiles}}
					<li>
						<span class = "mailbox-attachment-icon has-img">
							<img src="{{imgsrc}}" alt="Attachement" />
						</span>
						<div class = "mailbox-attachment-info">
							<a href="{{getlink}}" class="mailbox-attachment-name">{{filename}}</a>
							<a href="{{fullName}}" class="btn btn-default btn-xs pull-right delbtn">
								<i class="fa fa-fw fa-remove"></i>
							</a>
						</div>
					</li>
					{{else}}
						<li>첨부파일이 없습니다.</li>
					{{/each}}
			</script>
		</ul>
		<button type="submit" class="btn btn-primary">작성</button>
		<a href="/board/listPage${criteria.makeQuery()}"
			class="btn btn-default">취소</a>
	</div>

<script>
	const $fileDrop = $('div.fileDrop');

	$fileDrop.on('dragover dragenter', (evt) => 	//드래그오버, 드래그엔터 : 드래그가 진입했을때
	{
		evt.preventDefault();
		$fileDrop.css("border", "1px dotted red");
	});

	$fileDrop.on('dragleave', (evt) => {
		evt.preventDefault();
		$fileDrop.css("border", "1px dotted grey");
	});

	$fileDrop.on('drop', (evt) => 
	{
		evt.preventDefault();	//원래의 브라우저의 이벤트 기능 막는다.
		let files = evt.originalEvent.dataTransfer.files;
		console.log("drop >> ", evt.originalEvent.dataTransfer.files);
		$fileDrop.css("border", "none");
		//$fileDrop.html(files[0].name);		//파일이름으로 바꾸기
		$("#ajax_file").prop("files", evt.originalEvent.dataTransfer.files);	// prop 드롭된 값을 주는것 파일이라는 값으로 뒤에evt를 줌
		$('#form_attach').submit();
	});
	
	const $percent = $('#percent'),
		   $status = $('#status');

	let upFiles = [];
	$('#form_attach').ajaxForm
	({
		beforeSend : function()
		{
			let f =$('#ajax_file').val();
			console.info("beforeSend>>>", f);
			if(!f) return false;	//f에 값이없으면 바로종료
			$status.empty();
			$percent.html('0%');
		},
		uploadProgress: function(event, position, total, percentComplete){
			$status.html('uploading...');
			$percent.html(percentComplete + '%');
		},
		complete : function(xhr) //0927 xhr 서버에서 보내주는 값 이상한게들어온다. TODO
		{
			let jsonData = getFileInfo(xhr.responseText);
			
			console.info('jsonData>>>>', jsonData);
			upFiles.push(jsonData);
	
			$status.html(jsonData.fileName +  'Uploaded');
	
			renderHbs('template', {upFiles : [jsonData]});
			//(jsonData, tag)를 각각 넣어준다.
		}
	});

	function checkImageType(fileName)
	{
		let pattern = /jpg$|png$|gif$/i; // ^시작 , $끝, i는 대소문자 구분 없
		return fileName.match(pattern);
	}

	function getFileInfo(fullName)	//0928이 풀네임에서 정보뽑음
	{
		let fileName, imgsrc, getLink, fileLink;
		if (checkImageType(fullName))
		{
			console.info("IMAGE>>>");
			imgsrc = "/displayFile?fileName=" + fullName;
			fileLink = fullName.substring(14); // /2018/09/28/s_ 유니크 아이디랑 원본파일이름 붙음
			let front = fullName.substring(0,12), // /2018/09/28/ 년월일까지
					end = fullName.substring(14);
			getLink = "/displayFile?fileName=" + front + end; //원본파일 보기링크
		}

		else //이미지가 아니면 이 자리에 아이콘을 보여주겠다.
	  	{
			imgsrc = "/resources/dist/img/file_icon.png";
			fileLink = fullName.substring(12);	//원본 파일명
			getLink = "/displayFile?fileName=" + fullName;	//원본파일 경로
		}	
		//실제 파일명
		fileName = fileLink.substring(fileLink.indexOf('_') + 1);

		return{		// 띄어쓰면 오류난다 ㅇㅓ이;
			fileName: fileName,
			imgsrc: imgsrc,
			getLink: getLink,
			fullName: fullName
		};
	}
	///---------------------------- getFileInfo 끝 //리턴 값 json으로 준다 모두 핸들바에서 쓴다.
</script>
</body>
</html>

<%@include file="../include/footer.jsp"%>
