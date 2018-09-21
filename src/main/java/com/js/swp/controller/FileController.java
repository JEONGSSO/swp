package com.js.swp.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.js.swp.domain.Board;
import com.js.swp.util.FileUtils;

@Controller
public class FileController {
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void uploadFormGET(Board board, Model model) throws Exception 
	{
		logger.info("upload GET >>>>>>>>>>");
	}
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)//TODO
	public void uploadFormPOST(MultipartFile file, Model model, @RequestParam String type) throws Exception
	{
		logger.info("upload POST >>> originName={}, size={}, contentType={}", 
				file.getOriginalFilename(),
				file.getSize(),
				file.getContentType());
		
		String savedFileName = FileUtils.uploadFile(file , uploadPath);
		model.addAttribute("savedFileName", savedFileName);	//savedFileName에다가 써라 무엇을? savedFileName을
		model.addAttribute("type", type);
	}
	
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadFormAjax(MultipartFile file, Model model, @RequestParam String type) throws Exception
	{
		logger.info("upload POST >>> originName={}, size={}, contentType={}", 
				file.getOriginalFilename(),
				file.getSize(),
				file.getContentType());
			logger.info("upload POST >>> Type={}", type);
		try
		{
			String savedFileName = FileUtils.uploadFile(file , uploadPath);	//업파일해서 다(?) 만들어줌
			return new ResponseEntity<>(savedFileName, HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}