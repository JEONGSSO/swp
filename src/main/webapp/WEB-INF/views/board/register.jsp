<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

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
				<div id = "percent">0%</div>
				<div id = "status">ready</div>
		</div>

		<form id = "form_attach" action="uploadAjax" method = "POST" enctype = "multipart/form-data">
			<input type="hidden" name = "type" value = "ajax"/>
			<input type="file" name="ajax-file" style="display : none;"/>
			<%-- <input type='submit' value = "ajax로 제출"/> --%>
		</form>

		<!-- 0928 drag & drop  -->
			<div class="form-group">
				<label for="">여기다 떨구세요</label>
				<div class="fileDrop"></div>
			</div>
	</div>	<!-- body box 끝  -->
</form>

	<div class="box-footer">
			<!-- 0928 첨부파일 -->
			<ul class="mailbox-attachments clearfix uploadedList">
				<script id="template" type="text/x-handlebars-template" class="well">
					{{#each upFiles as |uf|}}
					<li>
						<span class = "mailbox-attachment-icon has-img"> {{!-- 메일박스;? --}}
							<img src="{{imgsrc}}" alt="Attachement" />
						</span>
						<div>
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
	const $fileDrop = $('div.fileDrop'),
			$uploadedList = $('div.uploadedList');	//달러는 변수명도 달러로

			$fileDrop.on('dragover dragenter', (evt) =>
			{
				evt.preventDefault();
				$fileDrop.css("border", "1px dotted green");
			});

			$fileDrop.on('dragleave dragenter', (evt) =>
			{
				evt.preventDefault();
				$fileDrop.css("border", "1px dotted grey");
			});

			$fileDrop.on('drop', (evt) =>
			{
				evt.preventDefault();
				let files = evt.originalEvent.dataTransfer.files;
				console.debug("drop >> ", files);
				$fileDrop.css("border", "none");
				//$fileDrop.html(files[0].name);		//파일이름으로 바꾸기
				$("ajax_file").prop("files", evt.originalEvent.dataTransfer.files);	// prop 객체 선택?
				$('#form_attach').submit();	//제출
			});

			const $percent = $('#percent'),
					$status = $('#status');

			$('#form_attach').ajaxForm
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
					console.debug('complete>>>>>>>', xhr);

					let jsonData = getFileInfo(xhr.responseText);
					console.debug('jsonData>>>>', jsonData);

							$status.html(jsonData +  'Uploaded');

							renderHbs('template',
							{
								upFiles : [jsonData]
							}, 'div');	//(jsonData, tag)를 각각 넣어준다.
					}
		});

		function checkImageType(fileName)
		{
			let pattern = /jpg$|png$|gif$/i;		//이미지 파일 인것만 i : 대소문자 구분없이
			return fileName.match(pattern);
		}

		function getFileInfo(fullName)	//0928
		{
			let fileName, imgsrc, getLink, FileLink;

			if (checkImageType(fullName))
			{
				imgsrc = "/display?fileName=" + fullName;
				FileLink = fullName.substring(14); //원본 파일명/2018/09/28/s_
				let front = fullName.substring(0,12),
						end = fullName.substring(14);
				getLink = "displayFile?fileName=" + front + end; //원본파일 보기링크
			}

			else //이미지가 아니면
		  {
				imgsrc = "/resources/dist/img/file_icon.png"
				fileLink = fullName.substring(12);	//원본 파일명
				getLink = "/displayFile?fileName=" + fullName;
			}

			fileName = fileLink.substring(fileLink.indexOf('_') + 1);

			return
			{
				fileName : fileName,
				imgsrc : imgsrc,
				getLink : getLink,
				fullName : fullName
			};	//리턴 값 json으로 준다
		}	//---------------------------- getFileInfo 끝

</script>
<link rel="stylesheet" href="/resources/style.css"/>
<%@include file="../include/footer.jsp"%>
