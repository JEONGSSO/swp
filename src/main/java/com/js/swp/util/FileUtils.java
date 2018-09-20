package com.js.swp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils	//0920//TODO
{
	private static Map<String, MediaType> mediaMap;	//맵을 만들어서 
	static	// FileUtils 생성될 때 이짓거리를 해라 static은 한번만 실행된다 //싱글톤(?)은 뉴 안하고 하나만 만든다. 
	{
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String ext)
	{
		return mediaMap.get(ext.toUpperCase());
	}
	
	public static String uploadFile(MultipartFile file, String uploadPath) throws IOException	//DB입출력 관련된건 다 IOException
	{
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();	//랜덤 아이디 출력 asdasdsa_이름.jpg 등
		String dirname = getCurrentUploadPath(uploadPath);	//dirname 에는 현재 업로드 경로 함수가 담긴다 (년 월 일) 생성
		File target = new File(dirname, filename);	//새로운 객체를 만들어 매개변수 전닳
		FileCopyUtils.copy(file.getBytes(), target);		//getBytes은 파일이름을 부른(?)다.
		
		String ext = getFileExtenstion(filename);	//만들어야해
		
		String uploadFilename = null; 
		if(getMediaType(ext) != null)
			uploadFilename = makeThumbnail(uploadPath, dirname, filename);
		else
			uploadFilename = makeIcon(uploadPath, dirname, filename);
		
		return filename;
	}
	
	private static String makeIcon(String uploadPath, String dirname, String filename)
	{
		String iconName = uploadPath + File.separator + filename;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	private static String makeThumbnail(String uploadRootPath, String dirname, String filename) throws IOException 
	{
		BufferedImage srcImg = ImageIO.read(new File(dirname, filename));	//버퍼는 콩이랑 쌀 걸러낼때 바구니 2개를 만드는거
		BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);	//scrImg를 무조건 100으로 맞춰라
		
		String thumbnailName = dirname + File.separator + "s_" + filename; // 디렉토리명;; separator는 스트링
		String ext = filename.substring(filename.lastIndexOf(".") + 1);		//
		File newFile = new File(thumbnailName);// 디렉토리;
		ImageIO.write(destImg, ext.toUpperCase(), newFile); //디스크에 쓸 파일 준다.
		
		return thumbnailName.substring(uploadRootPath.length()).replace(File.separatorChar, '/');
	}
	
	public static String getCurrentUploadPath(String uploadRootPath) 
	{
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DATE);
		
		return makeDir(uploadRootPath, "" + y, StringUtils.len2(m), StringUtils.len2(d));
	}

	private static String makeDir(String uploadRootPath, String... paths)
	{
		System.out.println(">>" + Arrays.toString(paths));
		for (String path : paths)
		{
			uploadRootPath += File.separator + path;
			System.out.println(uploadRootPath);
			File tmpFile = new File(path) ;
			if(tmpFile.exists())
				continue;
			else
				tmpFile.mkdir();
		}
		return uploadRootPath;
	}
	
	public static void main(String args[])
	{
		getCurrentUploadPath("aaa");
	}
	
}
