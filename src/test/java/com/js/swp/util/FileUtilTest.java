package com.js.swp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class FileUtilTest	//0921
{
	@Test
	public void makeThumbnailTest() throws Exception{
		String uploadRootPath = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads";
		assertTrue(existsDir(uploadRootPath));
		String dirName = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads\\2018\\09\\21";
		String fileName = "test.jpg";//수정
		String thumbnailName = dirName + File.separator + "s_" + fileName;
		
		File old =new File(thumbnailName);
		if(old.exists())
			old.delete();
		
		String makeThumbnail = FileUtils.makeThumbnail(uploadRootPath, dirName, fileName);
		assertTrue(new File(thumbnailName).exists());
		assertEquals(makeThumbnail,"/2018/09/21/s_test.jpg");
	}
	
	@Ignore
	public void test()
	{
		String uploadRootPath = "/Users/Administrator/Documents/workspace-sts-3.9.5.RELEASE/upload";
		
		String CurrentPath = FileUtils.makeDir(uploadRootPath);//QQQ
		System.out.println(">>>>>>>Current = " + CurrentPath);
		
		String path = FileUtils.getCurrentUploadPath(uploadRootPath);
		
		assertTrue(existsDir(path));
	}
	
	@Ignore
	public void getCurrentUploadPathTest() throws Exception{
		String uploadRootPath = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads";
		assertTrue(existsDir(uploadRootPath));
		
		String path = FileUtils.getCurrentUploadPath(uploadRootPath);
		assertTrue(existsDir(path));
	}

	private boolean existsDir(String path)
	{
		return new File(path).exists();
	}
	
}
