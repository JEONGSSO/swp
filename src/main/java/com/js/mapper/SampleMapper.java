package com.js.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
	
	
}