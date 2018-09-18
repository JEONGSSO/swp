package com.js.swp.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.js.swp.domain.MessageVo;
import com.js.swp.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController
{
	@Inject
	private MessageService service;
	
	@RequestMapping(value = "/", method = RequestMethod.POST) // POST 등록
	public ResponseEntity<String> addMessage(@RequestBody MessageVo message)	// 스트링을 리턴할거야	json하려면 obj가와야한다.
	{ 		ResponseEntity<String> entity = null;									//json은 받아와서 vo를 만들거야, 잭슨이 받아온다(?)
																									//@RequestBody는 json의 바디에  클라이언트에서 준 값을 message에 담는다.
																//ResponseEntity 주고받는 봉투를 구현한것,  데이터가 예외적 상황에 상태 표현해주는 것 값은 스트링
		try
		{
			service.addMessage(message);	//위에서 담은 reply를 서비스에 레지스터 실행
			entity = new ResponseEntity<>("ADD MSG OK", HttpStatus.OK); 		//ok == 200;
//			return new ResponseEntity<String>(T body, status)		// ResponseEntity에 T는 어떤타입이든 와도 된다. object임	
			
		} catch (Exception e)
		{
			e.printStackTrace();	//예외를 출력해주고
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);	//BAD_REQUEST == 400을 반환한다.
		}
		return entity;
	}
}
