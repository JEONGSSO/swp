<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<script src="/resources/upload.js"></script>	

<script id="template" type="text/x-handlebars-template">	//deleteFile bno 강제로 주기 showOriginal 원본파일 링크
				{{#each upFiles}}
					<li id="{{fileId}}">
					  <input type="hidden" name="files" value="{{fullName}}" />
						<span class = "mailbox-attachment-icon has-img">
							<img src="{{imgsrc}}" alt="Attachement" />
						</span>
						<div class = "mailbox-attachment-info">
							<a href="javascript:;" onclick="showOriginal('{{getLink}}')" class="mailbox-attachment-name">{{fileName}}</a>
						   {{#if isEditing}}
							<a href="javascript:;" onclick ="deleteFile('{{fullName}}', '${board.bno}')" class="btn btn-default btn-xs pull-right delbtn"> {{!-- javascript a태그 무력화  띄어쑤기 조심--}}
								<i class="fa fa-fw fa-remove"></i>
							</a>
							{{/if}}
						</div>
					</li>
					{{else}}
						<li>첨부파일이 없습니다.</li>
				{{/each}}
	</script>
	
<div id="original-image-modal" class="modal fade" tabindex="-1" role="dialog">	
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <img src="" alt="" />
    </div>
  </div>
</div>       
	
<script>	
	function showOriginal(link)	//1003
	{
		let $originalImageModal = $('#original-image-modal'); //위에 곧 만들 div id적는다.
		
		if(checkImageType(link))		//이미지 일때만
			{
				$originalImageModal.find('img').attr('src', link);	//위에 img에 값으로 link를 박는다.
				$originalImageModal.modal('show');	//모달 쇼
			}
		else
			window.location.href = link;		//아니면 다운로드 링크
	}

	function showAttaches(bno)	//1002
	{
		sendAjax("/board/getAttach/" + bno, (isSuccess, res) => 
			{
		        if (isSuccess) 
		        	{
		        		res.forEach( rj =>	
	            {
	            	let jsonData = getFileInfo(rj);
	            	gUpFiles.push(jsonData); //기존파일 + 새로운파일
          		});												//div는 기본값 안줘도됨
		        	renderHbs('template', { upFiles : gUpFiles }, 'div');	
		    } 	else 	//upfiles 대소문자 문제가능성 있음
		    	{
		            console.debug("Error on registerReply>>", res);
		        }
		});
	}
	</script>