package com.pay1oad.rest.service;

import java.util.List;

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


	public List<MemberDTO> selectList() {
		// TODO Auto-generated method stub
		return template.selectList("memberSelectList");
	}


	public MemberDTO selectOne(String username) {
		// TODO Auto-generated method stub
		return template.selectOne("memberSelectOne", username);
	}


	public int update(MemberDTO dto) {
		// TODO Auto-generated method stub
		
		
		return template.update("memberUpdate",dto);
	}


	public void delete(String username) {
		// TODO Auto-generated method stub
		template.delete("memberDelete", username);
	}
	
	
	
	
	
}
