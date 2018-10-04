package com.js.swp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils
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
	{	//맵에서 확장자가 이미지인지 판단
		return mediaMap.get(ext.toUpperCase());
	}
	
	//컨트롤러에서 실행 핵심 메소드
	public static String uploadFile(MultipartFile file, String uploadPath) throws IOException	//DB입출력 관련된건 다 IOException
	{
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();	//랜덤 아이디 출력 asdasdsa_이름.jpg 등
		String dirname = getCurrentUploadPath(uploadPath);	//dirname 에는 현재 업로드 경로 함수가 담긴다 (년 월 일) 생성
		File target = new File(dirname, filename);	//새로운 target 객체를 만들어 매개변수 전닳 
		FileCopyUtils.copy(file.getBytes(), target);		//getBytes은 파일이름을 부른(?)다. 원본파일을 저장함.
		
		String ext = getFileExtension(filename);	//확장자 뽑음
		
//		System.out.println("getcurrent>>>" + dirname); 	//C:/Users/Administrator/Documents/workspace-sts-3.9.5.RELEASE/upload\2018\10\01 잘 찍힘
		
		String uploadFilename = null;	//초기화
		
		if(getMediaType(ext) != null)	//getMediaType 이미지인지 판단하는 함수 확장자가 비어있지 않으면 썸네일 생성.
			uploadFilename = makeThumbnail(uploadPath, dirname, filename);
		else									//getMediaType 이미지가 아니라 비어있으면 아이콘 생성
			uploadFilename = makeIcon(uploadPath, dirname, filename);
		
		return uploadFilename;	
	}
	
	public static String getFileExtension(String filename) 	//확장자 뽑는 함수
	{	// lastIndexOf 점 뒤에 전부를 가져와 substr로 끊어먹는다.
		return filename.substring(filename.lastIndexOf(".") + 1); 
	}

	private static String makeIcon(String uploadPath, String dirname, String filename)
	{	//이미지가 아닐때 아이콘 생성
		String iconName = dirname + File.separator + filename;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	static String makeThumbnail(String uploadRootPath, String dirname, String filename) throws IOException 
	{
		BufferedImage srcImg = ImageIO.read(new File(dirname, filename));	//버퍼는 콩이랑 쌀 걸러낼때 바구니 2개를 만드는거 temp생각하면 된다.
		BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);	//scrImg를 HEIGHT 100으로 맞춰라 나머지는 알아서 맞춰줌
		
		String thumbnailName = dirname + File.separator + "s_" + filename; // 디렉토리명;; separator는 스트링
		String ext = getFileExtension(filename);		// 확장자 뽑기
		File newFile = new File(thumbnailName); // 디렉토리
		ImageIO.write(destImg, ext.toUpperCase(), newFile); //디스크에 쓸 파일 준다.
		// 섬네일 경로생성후 fileupload 보냄
		return thumbnailName.substring(uploadRootPath.length()).replace(File.separatorChar, '/');
	}
	
	//crop
	
	public static String getCurrentUploadPath(String uploadRootPath) 
	{	//년월일 뽑는 메소드
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DATE);
		
//		경로에 폴더 생성하게 한다.
		return makeDir(uploadRootPath, "" + y, FileUtils.len2(m), FileUtils.len2(d));
	}

	public static String makeDir(String uploadRootPath, String... paths)
	{	//폴더 생성 메소드	string... paths요거는 배열?이었나
		for (String path : paths)
		{
			uploadRootPath += File.separator + path;	
//			System.out.println(uploadRootPath);
			File tmpFile = new File(uploadRootPath) ;	//임시 파일에 uploadRootPath 줘야 폴더를 생성해간다.
			if(tmpFile.exists())	 //파일이 존재하면  계속진행
				continue;
			else
				tmpFile.mkdir();
		}
		return uploadRootPath;
	}

	public static String len2(int n)
	{
		return new DecimalFormat("00").format(n).toString();
	}
}
