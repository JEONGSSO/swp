package com.js.swp.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO
{
	@Inject
	SqlSession session;

	private static final String NS = "PointMapper";
	
	@Override
	public void updatePoint(String uid, Integer point) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", uid);	//가져와서 담는다 userid
		paramMap.put("upoint", point);
		
		session.update(NS +  ".updatePoint", paramMap);
	}
}
