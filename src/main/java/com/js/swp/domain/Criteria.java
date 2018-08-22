package com.js.swp.domain;

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
}