
package com.js.swp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.js.swp.util.FileUtils;

@Controller
public class UploadController
{
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void uploadFormGET() throws Exception
	{
		logger.info("upload GET >>>>>>>>>>");
	}
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST) // TODO
	public void uploadFormPOST(MultipartFile file, Model model, @RequestParam String type) throws Exception
	{
		logger.info("upload POST >>> originName={}, size={}, contentType={}",
				file.getOriginalFilename(),
				file.getSize(),
				file.getContentType());
		
		String savedFileName = FileUtils.uploadFile(file, uploadPath);
		model.addAttribute("savedFileName", savedFileName); // savedFileName에다가 써라 무엇을? savedFileName을
		model.addAttribute("type", type);
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadFormAjax(MultipartFile file, Model model, @RequestParam String type) throws Exception
	{
		logger.info("upload AJAX >>> originName={}, size={}, contentType={}",
				file.getOriginalFilename(),
				file.getSize(),
				file.getContentType());
		try
		{
			String savedFileName = FileUtils.uploadFile(file, uploadPath); // 업파일해서 다(?) 만들어줌
			return new ResponseEntity<>(savedFileName, HttpStatus.CREATED);
		} 
		
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)	//0927
	public ResponseEntity<String> deleteFile(String fileName) throws Exception
	{
		logger.info("deleteFile >>> name={}",fileName);
		try
		{
			boolean isImage = FileUtils.getMediaType(FileUtils.getFileExtenstion(fileName)) != null;	 //이미지라면 섬네일이 올랑땜ㄴ에
			File file = new File(uploadPath + fileName);
			file.delete();
			
			//image면 원본 이미지도 삭제
			if (isImage)
			{
				int lastSlash = fileName.lastIndexOf("/") + 1;
				String realName = fileName.substring(0, lastSlash) + fileName.substring(lastSlash + 2);
				File real = new File(uploadPath + realName);
				real.delete();
			}
			
			return new ResponseEntity<>("deleted" , HttpStatus.OK);
		} 
		
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception
	{
		logger.info("display File filename ={}", fileName);
		
		InputStream in = null;	//?
		
		try
		{
			String formatName = FileUtils.getFileExtenstion(fileName);
			MediaType mType = FileUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			
			File file = new File(uploadPath + fileName);
			logger.info("exists={}", file.exists());
			if (!file.exists())		//없으면 낫파운드
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			in = new FileInputStream(file);
			
			if (mType != null)	//이미지가 아닐때에는
				headers.setContentType(mType);//콘텐츠를 mType
			else
			{
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); //8byte 파이프 크기 
				String dsp = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");	//utf-8 쪼개서 ISO-8859-1으로 합쳐진다.
				logger.info("dsp={}", dsp);
				headers.add("content-Disposition", "attachment; filename=\"" + dsp + "\"");	//첨부파일 파일명 dsp
			}
			return new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.OK);	//파이프로 내보낸다
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		finally //무조건 탄다
		{
			if(in != null)
			in.close();
		}
	}
	
}