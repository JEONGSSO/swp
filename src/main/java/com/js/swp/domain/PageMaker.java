package com.js.swp.domain;

import org.springframework.web.util.UriComponentsBuilder;
public class PageMaker {
	private int displayPageNum = 10;
	
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private Criteria criteria;
	public PageMaker() {
		
	}
	public PageMaker(Criteria criteria) {
		this.criteria  = criteria;
	}
	public  void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	
	public Criteria getCriteria() {
		return criteria;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	
	calcData();
}

private void calcData() {
	endPage = (int) (Math.ceil(criteria.getPage() / (double) displayPageNum) * displayPageNum);
	
	startPage = (endPage - displayPageNum) +1;
	
	int tempEndPage = (int) (Math.ceil(totalCount / (double) criteria.getPerPageNum()));
	
	if(endPage > tempEndPage) {
		endPage = tempEndPage;
	}
	
	prev = startPage == 1 ? false : true;
	
	next = endPage * criteria.getPerPageNum() >=totalCount ? false : true;
	
	System.out.println("::>" + startPage + "," + endPage);
	}

	public String makeQuery(int page) {		//메소드 오버로딩 변수 하나면 일로옴
		
		return makeQuery(page, true);
	}
	
	public String makeQuery(int page, boolean needsearch) {	//286
		UriComponentsBuilder uriComponentsBuild = 
					UriComponentsBuilder.newInstance()
					  .queryParam("page", page)
					  .queryParam("perPageNum", this.criteria.getPerPageNum());
					
					if(!needsearch) {
						uriComponentsBuild
						  .queryParam("searchType", this.criteria.getSearchType())
						  .queryParam("keyword", this.criteria.getKeyword());
					}
					return uriComponentsBuild.build().encode().toString();
	}

	public boolean isPrev() {
		return prev;
	}
	
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	
	public boolean isNext() {
		return next;
	}
	
	public void setNext(boolean next) {
		this.next = next;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}