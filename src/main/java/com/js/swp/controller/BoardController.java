package com.js.swp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.js.swp.domain.Board;
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
		return "redirect:/board/listAll";
	}
		@RequestMapping(value = "/listAll", method = RequestMethod.GET)
		public void listAll(Model model) throws Exception {
			logger.info("list get");
			model.addAttribute("list", service.listAll());
		}
}
