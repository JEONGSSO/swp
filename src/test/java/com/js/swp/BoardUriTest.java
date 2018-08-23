package com.js.swp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class BoardUriTest {

	private static Logger logger = LoggerFactory.getLogger(BoardUriTest.class);
		@Test
		public void testURI2() throws Exception {
			int bno = 207;
			int perpagenum = 20;
			UriComponents uriComponents = null;
			for (int i = 0; i < 200222; i++) {
				uriComponents =	UriComponentsBuilder.newInstance()
					.path("/{module}/{page}")
					.queryParam("bno", bno)
					.queryParam("perPageNum", perpagenum)
					.queryParam("keyword", "강원도도돋ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ!@$%345?")
					.build()
					.expand("board", "read")
					.encode();
			}
			logger.info("/board/read?bno=12&perPageNum=20");
			logger.info(uriComponents.toString());
		}
}
