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
	ajaxsavedFileName : <sapn id="ajax_upfile"></sapn>
	
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
				$uploadedList = $('div.uploadedList');
		
		$fileDrop.on('dragover dragenter', (evt) => {
			evt.preventDefault();
			$fileDrop.css("border", "1px dotted green");
		});
		
		$fileDrop.on('drop', (evt) => {
			evt.preventDefault();
			console.debug("drop >> ", evt.originalEvent.dataTransfer.files);
		});
		
		$fileDrop.on('dropleave', (evt) => {
			evt.preventDefault();
			$fileDrop.css("border", "none");
		});
		
		const $percent = $('#percent'),
				$status = $('#status');
				
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
		complete: function(xhr) {
	        status.html(xhr.responseText);
	    }
	});
	</script>
		
	<c:if test = "${type eq 'ifr'}">	<!-- 만약에 ifr 업로드면 이 구문을 타라 -->
		<script>
			console.debug(">>>>> ifr script")
			parent.setUploadedFile('${savedFileName}');
		</script>
	</c:if>
	
</body>
</html>