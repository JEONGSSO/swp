package com.js.swp.domain;

import java.util.Date;

import lombok.Data;

@Data	// 0928 lombok사용 outline 꼭 확인.
public class Board {

		private Integer bno;
		private String title;
		private String content;
		private String writer;
		private Date regdate;
		private int viewcnt;
		private int replycnt;
		private String[] files;		// 0928
}
