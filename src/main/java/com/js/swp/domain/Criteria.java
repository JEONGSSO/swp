package com.js.swp.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Criteria {
	private int page;
	private int perpageNum;
	
	public Criteria() {
		this.page =1 ;
		this.perpageNum =10 ;
	}
	public void setPage(int page) {
		if(page <=0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perpageNum = 10;
			return;
		}
		
		this.perpageNum = perPageNum;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getPageStart() {
		return (this.page-1) * perpageNum;
	}
	
	public int getPerPageNum() {
		
		return this.perpageNum;
	}
	@Override
	public String toString() {
		return "Criteria [page=" + page +"," + "perPageNum=" + perpageNum+"]";
	}
	
	public String makeQuery() {	
		UriComponents uriComponents = 
					UriComponentsBuilder.newInstance()
					.queryParam("page", this.page)
					.queryParam("perPageNum", this.getPerPageNum())
					.build();
		
		return uriComponents.toUriString();
	}
}