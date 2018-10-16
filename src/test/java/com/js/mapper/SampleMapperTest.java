package com.js.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.js.swp.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class SampleMapperTest {
	
	@Inject
	SampleMapper sampleMapper;
	
	@Ignore @Test
	public void test() {
		String className = sampleMapper.getClass().getName();
		System.out.println("className = " + className);
		String now = sampleMapper.getTime();
		System.out.println("Now : " + now);
		assertTrue(StringUtils.startsWith(className, "com.sun.proxy."));
	}
	@Test
	public void testSearch() throws Exception{
		String searchCol = "uid";
		String searchStr = "user1";
		
		List<User> users = sampleMapper.searchUser(searchCol, searchStr);
		assertEquals(1, users.size());	//유저가 1이찍혀야 있는거
		assertEquals(searchStr, users.get(0).getUid());	//검색값과 0번째 uid를 비교
		System.out.println("users>>>>>>" + users);
		
		searchCol = "uname";
		searchStr = "수";
		
		users = sampleMapper.searchUser(searchCol, searchStr);	//0넘어온다
//		System.out.println("users.size>>>>>>" + users.size());
		assertTrue(users.size() > 1);	//사이즈는 1보다 커야하낟.
	}
}
