package com.pay1oad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pay1oad.rest.service.MemberDTO;
import com.pay1oad.rest.service.memberDAO;

@RestController
public class RestAPIController {
	
	@Autowired
	private memberDAO dao;
	
	// post - htpp://localhost:CURPORT/members/key
	// key-value로 온다 현재 dto로 받을꺼니까
	// json 작동 x 
	@PostMapping(value="/members/key",produces = "text/plain;charset=UTF-8")
	public String insertKeyValue(MemberDTO dto) {
		int affected = dao.insert(dto);
		
		return affected+"행이 추가 되었습니다!";
	}
	
	
	
	
	
}
