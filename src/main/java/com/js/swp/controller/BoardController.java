package com.js.swp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.js.mapper.SampleMapper;
import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
import com.js.swp.domain.PageMaker;
import com.js.swp.domain.User;
import com.js.swp.interceptor.SessionKey;
import com.js.swp.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Resource(name = "uploadPath")	//어플리케이션에서 필요한 자원을 자동으로 연결할 때 사용.
	private String uploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService service;
	
	@Inject
	private SampleMapper sampleMapper;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(Board board, Model model) throws Exception {
		logger.info("register get");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(Board board, RedirectAttributes rttr) throws Exception {
		logger.info("register post {}", board.toString());	//	커넥션 풀이 안되는듯 ?
		service.regist(board);
//		model.addAttribute("result", "success");
//		return "/board/success";
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/board/listPage"; 
//		<!-- 글쓰기하고 취소하면 1페이지로 간다. -->
	}
	
		@RequestMapping(value = "/read", method = RequestMethod.GET)
		public String read(@RequestParam("bno") Integer bno, 
						@ModelAttribute("criteria") Criteria criteria,
						HttpServletResponse response,
						Model model) throws Exception 
		{
			logger.info("read GET .....");
			Board board = service.read(bno);
			logger.info(">>>> board.read: {}", board);
			if (board == null) 
				response.sendError(404);
			
			model.addAttribute(board);
			return "/board/read";
		}
		
		@ResponseBody	//ajax라서 사용 rest방식이라 @PathVariable 사용 
		@RequestMapping(value = "/getAttach/{bno}", method = RequestMethod.GET)
		public List<String> read(@PathVariable ("bno") Integer bno) throws Exception 
		{	//bno잘 찍힘
			logger.info("getAttach ..... bno={}", bno);
			return service.getAttach(bno);
		}
		
		@RequestMapping(value = "/update", method = RequestMethod.GET)	 
		public void updateGet(@RequestParam("bno")int bno,
				@ModelAttribute("criteria")Criteria criteria, Model model) throws Exception {
			logger.info("UpdateGet.......");
			Board board = service.read(bno);
			model.addAttribute(board);
	}
		@RequestMapping(value = "/update", method = RequestMethod.POST)
		public String updatePOST(Board board, Criteria criteria, Model model, RedirectAttributes rttr) throws Exception {
			logger.info("update POST ..... {}", board.getBno());
			service.modify(board);		//DB에 수정하는 구문
			rttr.addFlashAttribute("msg", "ok");	//msg에 ok를 심음
			rttr.addAttribute("page", criteria.getPage());
			rttr.addAttribute("perPageNum", criteria.getPerPageNum());
			rttr.addAttribute("searchType", criteria.getSearchType());
			rttr.addAttribute("keyword", criteria.getKeyword());
//			rttr.addAttribute("bno", board.getBno()); //1003
			return "redirect:/board/read?bno="+ board.getBno();	//지금 수정한 bno로 이동한다. 메시지 var "msg" 는 read.jsp에다가 써야한다
		}
		
		@RequestMapping(value = "/remove", method = RequestMethod.GET)
		public String remove(@RequestParam("bno") Integer  bno, Criteria criteria, RedirectAttributes rttr) throws Exception {
			service.remove(bno);		//@RequestParam을 사용해 bno를 받아온다
			rttr.addFlashAttribute("msg", "remove-ok");	//msg에 remove-ok를 심음	
			rttr.addAttribute("page", criteria.getPage());
			rttr.addAttribute("perPageNum", criteria.getPerPageNum());
			rttr.addAttribute("searchType", criteria.getSearchType());
			rttr.addAttribute("keyword", criteria.getKeyword());
			return "redirect:/board/listPage";	//삭제 후 보드 리스트로 이동
		}

		@RequestMapping(value = "/listPage", method = RequestMethod.GET)
		public void listPage(Criteria criteria, Model model, HttpSession session) throws Exception {
			logger.info("bctrl listPage>>>>>>>>>>>>={}",criteria.toString());
			
			model.addAttribute("list", service.listCriteria(criteria));
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(criteria);
			pageMaker.setTotalCount(131);
			
			pageMaker.setTotalCount(service.listCountCriteria(criteria));
			
			model.addAttribute("pageMaker" , pageMaker);
			
			String now = sampleMapper.getTime();	 // 샘플매퍼에서 시간을 가져온다.
			model.addAttribute("NOW", now);		//NOW라는 이름에 now를 넣는다.
			
// User는 세션에 있고, User객체에 LOGIN을 세션에 넣어줘 담는다. 오브젝트 타입이라 형변환
			User loginUser = (User)session.getAttribute(SessionKey.LOGIN);
			if (null != loginUser) {
				String uname = service.getUname(loginUser.getUid());
				model.addAttribute("UNAME", uname);	//listPage에서 부를 수 있다.
				
				User loginInfoUser = service.getLoginInfo(loginUser.getUid());
				model.addAttribute("loginIp", loginInfoUser.getLoginip());
				model.addAttribute("loginTime", loginInfoUser.getLastLogin());
			}
		}
}	
		/*@ResponseBody	//뷰 출력되지 않고 http body에 직접쓰여짐	 POST하는 이유는 큰 파일 옮기기에 적합하다.
		@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8") //멀티파트로 올라오는 파일의 내용을 텍스트로 받는거
		public ResponseEntity<String> uploadFormAjax(MultipartFile file, String type) throws Exception
		{
			logger.info("upload AJAX >>> originName={}, size={}, contentType={}",
					file.getOriginalFilename(),
					file.getSize(),
					file.getContentType());
			try
			{
				String savedFileName = FileUtils.uploadFile(file, uploadPath); 
//				logger.info("ajax savedFileName >>> " + savedFileName);	
				// savedFileName이  xhr.responseText로 전달된다.
				return new ResponseEntity<>(savedFileName, HttpStatus.CREATED);
			} 
			
			catch (Exception e)
			{
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		
		*/
		
//		@RequestMapping(value = "/listAll", method = RequestMethod.GET)
//		public void listAll(Model model) throws Exception {
//			logger.info("list get");
//			model.addAttribute("list", service.listAll());
//		}
		
		
//		@RequestMapping(value = "/listCri", method = RequestMethod.GET)
//		public void listAll(Criteria criteria, Model model) throws Exception {
//			logger.info("list Cri");
//			model.addAttribute("list", service.listCriteria(criteria));
//		}
//		
		//10-01
		/*
		*/
