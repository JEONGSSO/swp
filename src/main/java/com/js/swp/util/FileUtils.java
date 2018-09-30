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
	private static Map<String, MediaType> mediaMap;	//맵을 만들어서 키밸류 쌍으로
	static	// FileUtils 생성될 때 이짓거리를 해라 static은 한번만 실행된다 //싱글톤(?)은 뉴 안하고 하나만 만든다. 
	{
		mediaMap = new HashMap<>();		// 맵을 담았다
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);	//일단 이미지형태만 잡아놓음 
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String ext)	//확성자를 주면 대문자로 찾아 내려보냄
	{
		return mediaMap.get(ext.toUpperCase());
	}
	
	public static String uploadFile(MultipartFile file, String uploadPath) throws IOException	//DB입출력 관련된건 다 IOException
	{
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();	//랜덤 아이디 출력 asdasdsa_이름.jpg 등
		String dirname = getCurrentUploadPath(uploadPath);	//dirname 에는 현재 업로드 경로 함수가 담긴다 (년 월 일) 생성
		File target = new File(dirname, filename);	//새로운 객체를 만들어 매개변수 전닳 
		FileCopyUtils.copy(file.getBytes(), target);		//getBytes은 파일이름을 부른(?)다.
		
		String ext = getFileExtension(filename);	
		
		String uploadFilename = null;
		
		if(getMediaType(ext) != null)	//이미지라면 썸네일 생성.
			uploadFilename = makeThumbnail(uploadPath, dirname, filename);
		else
			uploadFilename = makeIcon(uploadPath, dirname, filename);
		
		return uploadFilename;
	}
	
	public static String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1); //확장자는 맨 마지막에 점
	}

	private static String makeIcon(String uploadPath, String dirname, String filename)
	{
		String iconName = dirname + File.separator + filename;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	static String makeThumbnail(String uploadRootPath, String dirname, String filename) throws IOException 
	{
		BufferedImage srcImg = ImageIO.read(new File(dirname, filename));	//버퍼는 콩이랑 쌀 걸러낼때 바구니 2개를 만드는거
		BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);	//scrImg를 HEIGHT 100으로 맞춰라 나머지는 알아서 맞춰줌
		
		String thumbnailName = dirname + File.separator + "s_" + filename; // 디렉토리명;; separator는 스트링
		String ext = getFileExtension(filename);		//확장자
		File newFile = new File(thumbnailName);// 디렉토리;
		ImageIO.write(destImg, ext.toUpperCase(), newFile); //디스크에 쓸 파일 준다.
		
		return thumbnailName.substring(uploadRootPath.length()).replace(File.separatorChar, '/');
	}
	
	public static String getCurrentUploadPath(String uploadRootPath) 	//년월일 뽑는 메소드
	{
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DATE);
		
		return makeDir(uploadRootPath, "" + y, StringUtils.len2(m), StringUtils.len2(d));
	}

	public static String makeDir(String uploadRootPath, String... paths)
	{
//		System.out.println("makeDir >>" + Arrays.toString(paths));//경로 나옴 생성이 안됨
		for (String path : paths)
		{
			uploadRootPath += File.separator + path;
//			System.out.println(uploadRootPath);
			File tmpFile = new File(path) ;
			if(tmpFile.exists())
				continue;
			else
				tmpFile.mkdir();
		}
		return uploadRootPath;
	}
}
