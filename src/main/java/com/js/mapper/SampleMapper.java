package com.js.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.js.swp.domain.User;

public interface SampleMapper{	//1015
	
	@Select("select now()")
	public String getTime() ;

	@Select("select uname from User where uid = #{uid}")
	public String getUname(@Param("uid") String uid);
	
	public User getLoginInfo(@Param("uid") String uid);	// 1016 mapper 메소드명 == select id
	
	@Update("update User set loginip = #{ip}, lastlogin = now() where uid = #{uid}")
	public void updateLogin(@Param("uid") String uid, @Param("ip") String ip);
	
	//여러명 받을거라 list하고 컬럼과 문자열을 받아 검색 @SelectProvider쿼리는 얘가 준다
	@SelectProvider(type=SampleProvider.class, method = "searchUser")
	public List<User> searchUser(@Param("searchCol")String searchCol,
											@Param("searchStr") String searchStr);
	
	
}