<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<script id="template" type="text/x-handlebars-template">
				{{#each upFiles}}
					<li id="{{fileId}}">
						<input type="hidden" name="files" value="{{fullName}}"/>
						<span class = "mailbox-attachment-icon has-img">
							<img src="{{imgsrc}}" alt="Attachement" />
						</span>
						<div class = "mailbox-attachment-info">
							<a href="{{getlink}}" class="mailbox-attachment-name">{{fileName}}</a>
						   {{#if isEditing}}
							<a href="javascript:;" onclick ="deleteFile('{{fullName}}')" class="btn btn-default btn-xs pull-right delbtn"> {{!-- javascript a태그 무력화  띄어쑤기 조심--}}
								<i class="fa fa-fw fa-remove"></i>
							</a>
							{{/if}}
						</div>
					</li>
					{{else}}
						<li>첨부파일이 없습니다.</li>
				{{/each}}
	</script>