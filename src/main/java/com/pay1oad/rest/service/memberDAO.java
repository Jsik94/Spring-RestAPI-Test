package com.pay1oad.rest.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class memberDAO {
	
	@Autowired
	private SqlSessionTemplate template;
	
	
	public int insert(MemberDTO member) {
		return template.insert("memberInsert",member);
	}
	
	
	
	
	
}
