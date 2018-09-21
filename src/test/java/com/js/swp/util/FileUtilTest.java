package com.js.swp.util;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class FileUtilTest	//0921
{
	
	@Test
	public void test()
	{
		String uploadRootPath = "/Users/Administrator/Documents/workspace-sts-3.9.5.RELEASE/upload";
		
		String CurrentPath = FileUtils.makeDir(uploadRootPath,);//QQQ
		System.out.println(">>>>>>>Current = " + CurrentPath);
		
		String path = FileUtils.getCurrentUploadPath(uploadRootPath);
		
		assertTrue(existsDir(path));
	}

	private boolean existsDir(String path)
	{
		return new File(path).exists();
	}
	
}
