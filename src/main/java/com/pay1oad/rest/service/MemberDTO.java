package com.pay1oad.rest.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private String username;
	private String password;
	private String name;
	private java.sql.Date postdate;
}
