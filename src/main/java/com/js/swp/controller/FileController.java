package com.js.swp.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.js.swp.domain.Board;

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
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public void uploadFormPOST(MultipartFile file, Model model, @RequestParam String type) throws Exception
	{
		logger.info("upload POST >>> originName={}, size={}, contentType={}", 
				file.getOriginalFilename(),
				file.getSize(),
				file.getContentType());
		
		String savedFileName = uploadFile(file);
		model.addAttribute("savedFileName", savedFileName);	//savedFileName에다가 써라 무엇을? savedFileName을
		model.addAttribute("type", type);
	}

	private String uploadFile(MultipartFile file) throws IOException	//DB입출력 관련된건 다 IOException
	{
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		File target = new File(uploadPath, filename);
		FileCopyUtils.copy(file.getBytes(), target);		//getBytes파일 이름
		return filename;
	}
	
}