package com.js.swp.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class Criteria {
	private int page;
	private int perpageNum;
	private String searchType;
	private String keyword;
	
	public Criteria() {
		this.page =1 ;
		this.perpageNum =10 ;
		this.searchType = null;
		this.keyword = null;
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
	
	public String makeQuery() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perpageNum", this.perpageNum);
				
		if (searchType!=null) {
			uriComponentsBuilder
					.queryParam("searchType", this.searchType)
					.queryParam("keyword", this.keyword);
		}
		return uriComponentsBuilder.build().encode().toString();
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
	
	public int getPerpageNum() {
		return perpageNum;
	}
	public void setPerpageNum(int perpageNum) {
		this.perpageNum = perpageNum;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "Criteria [page=" + page +"," + "perPageNum=" + perpageNum +
				"searchType =" + searchType + "keyword=" + keyword + "]";
	}

}