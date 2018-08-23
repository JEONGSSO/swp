package com.js.swp.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int displayPageNum = 10;
	
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private Criteria criteria;
	
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

public String makeQuery(int page) {	//286
	UriComponents uriComponents = 
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", criteria.getPerPageNum())
				.queryParam("searchType", criteria.getSearchType())
				.queryParam("keyword", criteria.getKeyword())
				.build();
	
	return uriComponents.toUriString();
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