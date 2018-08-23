package com.js.swp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
import com.js.swp.domain.PageMaker;
import com.js.swp.service.BoardService;

@Controller
@RequestMapping("/board/*")

public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService service;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(Board board, Model model) throws Exception {
		logger.info("register get");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(Board board, RedirectAttributes rttr) throws Exception {
		logger.info("register post");
		logger.info(board.toString());
		service.regist(board);
//		model.addAttribute("result", "success");
//		return "/board/success";
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/board/listPage"; 
//		<!-- 글쓰기하고 취소하면 1페이지로 간다. -->
	}
	
		@RequestMapping(value = "/listAll", method = RequestMethod.GET)
		public void listAll(Model model) throws Exception {
			logger.info("list get");
			model.addAttribute("list", service.listAll());
		}
		
		@RequestMapping(value = "/read", method = RequestMethod.GET)
		public void read(@RequestParam("bno")int bno, 
						@ModelAttribute("criteria") Criteria criteria,
						Model model) throws Exception {
			model.addAttribute(service.read(bno));
		}
		
		@RequestMapping(value = "/update", method = RequestMethod.GET)
		public void updateGet(@RequestParam("bno")int bno,
				@ModelAttribute("criteria")Criteria criteria, Model model) throws Exception {
			logger.info("UpdateGet");
			Board board = service.read(bno);
			model.addAttribute(board);
	}
		@RequestMapping(value = "/update", method = RequestMethod.POST)
		public String updatePOST(Board board, Criteria criteria, Model model, RedirectAttributes rttr) throws Exception {
			service.modify(board);		//DB에 수정하는 구문
			rttr.addFlashAttribute("msg", "ok");	//msg에 ok를 심음
			rttr.addAttribute("page", criteria.getPage());
			rttr.addAttribute("perPageNum", criteria.getPerPageNum());
			return "redirect:/board/read?bno="+ board.getBno();	//지금 수정한 bno로 이동한다. 메시지 var "msg" 는 read.jsp에다가 써야한다
		}
		
		@RequestMapping(value = "/remove", method = RequestMethod.GET)
		public String remove(@RequestParam("bno")int bno, Criteria criteria, RedirectAttributes rttr) throws Exception {
			service.remove(bno);		//@RequestParam을 사용해 bno를 받아온다
			rttr.addFlashAttribute("msg", "remove-ok");	//msg에 remove-ok를 심음	
			rttr.addAttribute("page", criteria.getPage());
			rttr.addAttribute("perPageNum", criteria.getPerPageNum());
			return "redirect:/board/listPage";	//삭제 후 보드 리스트로 이동
		}
		
		@RequestMapping(value = "/listCri", method = RequestMethod.GET)
		public void listAll(Criteria criteria, Model model) throws Exception {
			logger.info("list Cri");
			model.addAttribute("list", service.listCriteria(criteria));
		}
		
		@RequestMapping(value = "/listPage", method = RequestMethod.GET)
		public void listPage(@ModelAttribute("Criteria")Criteria criteria, Model model) throws Exception {
			logger.info(criteria.toString());
			
			model.addAttribute("list", service.listCriteria(criteria));
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(criteria);
			pageMaker.setTotalCount(131);
			
			pageMaker.setTotalCount(service.listCountCriteria(criteria));
			
			model.addAttribute("pageMaker" , pageMaker);
		}
		
}

