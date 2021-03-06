package com.js.swp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.js.swp.domain.Criteria;
import com.js.swp.domain.PageMaker;
import com.js.swp.domain.ReplyVO;
import com.js.swp.domain.User;
import com.js.swp.interceptor.SessionKey;
import com.js.swp.service.ReplyService;

@RestController
@RequestMapping("/replies") // /* 안써도 된다 replies라고 쓰는것들 다 프론트컨트롤러가 나한테 주는거
public class ReplyController
{
	@Inject
	private ReplyService service;	//서비스의 객체화 구현체를 만들어준다 

	@PostMapping(value = "") // POST 등록 1005 bno 0 넘어온다 일단 외래키 빼놨음
	public ResponseEntity<String> register(@RequestBody ReplyVO reply, HttpSession session, HttpServletRequest request)	// 스트링을 리턴할거야	json하려면 obj가와야한다.
	{ 																								
//		User loginUser = (User)session.getAttribute(SessionKey.LOGIN);
//		System.out.println("ReplyRegister>>{}"+ reply);	//디버그
		try
		{
//			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");	//1007
//			if(loginUser == null && loginCookie ==null)	//어뷰저 막기
//				return new ResponseEntity<>("Not LoggedIn", HttpStatus.UNAUTHORIZED);
//			else if(loginCookie != null)
//			{
//				loginUser = new User();
//				loginUser.setUserid("user6");
//			}
			service.register(reply);	//위에서 담은 reply를 서비스에 레지스터 실행
			return new ResponseEntity<>("ReplyRegisterOK", HttpStatus.OK);		//ok == 200;
//			return new ResponseEntity<String>(T body, status)		// ResponseEntity에 T는 어떤타입이든 와도 된다. object임	
																					//
		} catch (Exception e)
		{
//			e.printStackTrace();	//예외를 출력해주고
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);	//BAD_REQUEST == 400을 반환한다.
		}
	}

	@RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH}) //PUT, PATCH 수정
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno,		//@PathVariable 변수는 RNO 타입은 INTEGER;
			@RequestBody ReplyVO reply)	//@RequestBody 잭슨이 JSON값을 REPLY에 담는다?
	{
//		logger.debug("ReplyUpdate>>{}", rno, reply);
		try
		{
			reply.setRno(rno);	//replyvo에 setrno안에 클라이언트에게서 받은 rno를 쓴다.
			service.modify(reply);	//서비스 -> DAO -> SQLSESSION(매퍼 실행)  사실상 실행문
			return new ResponseEntity<String>("ReplyUpdateOK", HttpStatus.OK);
		} catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno)
	{
		try
		{
			service.remove(rno);		//DB 삭제 실행문
			return new ResponseEntity<>("ReplyDeleteOK", HttpStatus.OK);
		} catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}				
	}

	@RequestMapping(value = "/all/{bno}/{page}", method = RequestMethod.GET)		//리스트는 읽기 GET
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") Integer bno,	//@PathVariable uri에있는 값을 가져온다?
			@PathVariable("page") Integer page, HttpSession session)	//@PathVariable은 @RequestMapping에 bno, page를 담는다
	{
		Map<String, Object> map = new HashMap<>();	//해쉬맵 하나 선언 맵의 키 list pageMaker	list: [리스트vo] 
		try
		{
			Criteria cri = new Criteria();		//크리테리아는 페이징하려고
			cri.setPage(page);					
			List<ReplyVO> list = service.listReplyPage(bno, cri);	// 배열로 [ bno = 6 , rno = 10 , replyer :홍길동 ]
			map.put("list", list);	

			PageMaker pagemaker = new PageMaker(cri);	//페이지메이커는 쪽수 표현하려고
			int replyCount = service.getTotalCount(bno);// 서비스에 getTotalCount 실행해 리턴해온 값을 replyCount에 담는다.
			pagemaker.setTotalCount(replyCount);	//전체 갯수
			map.put("pageMaker", pagemaker);
			
			User loginUser = (User)session.getAttribute(SessionKey.LOGIN);	//오브젝트 타입을 유저 타입으로 넣어준다.
			if(loginUser != null)	//로그인 되었을때만 
			map.put("loginUid", loginUser.getUid()); //클라이언트로 내려간다 ResponseEntity에 담겨서

			return new ResponseEntity<>(map, HttpStatus.OK);
		} 
			catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	//일단 댓글 번호를 읽으려면 rno를 받아와야한다. 컨트롤러 -> 서비스(댓글은 생략가능) -> dao >>>>
	@RequestMapping(value = "/{rno}", method = { RequestMethod.GET})	//PUT, PATCH 수정 QQQ
	public ResponseEntity<ReplyVO> readRno(@PathVariable("rno") Integer rno)		//@PathVariable 변수는 RNO 타입은 INTEGEr	// @RequestBody 잭슨이 JSON값을 REPLY에 담는다?
	{
//		logger.debug("ReplyUpdate>>{}", rno);
		try
		{	
			// ReplyVO리턴 타입 vo에 rno 받음
			ReplyVO Replyvo = service.readRno(rno);	// 서비스 -> DAO -> SQLSESSION(매퍼 실행)  사실상 실행문 vo반환
			System.out.println("replyctrl>>>>>>>>>>>>>>> "  + service.readRno(rno).toString());
			return new ResponseEntity<>(Replyvo, HttpStatus.OK);	// ResponseEntity 생성해 vo(body)를 보낸다.
//			return new ResponseEntity<ReplyVO>(body, status)
		} 
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
