package com.pay1oad.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	
	
	
	@PostMapping(value="/members/json",produces = "text/plain;charset=UTF-8")
	public String insertJsonValue(@RequestBody MemberDTO dto) {
		int affected = dao.insert(dto);
		
		return affected+"행이 추가 되었습니다![JSON]";
	}
	
	
	@GetMapping("/members")
	public List<MemberDTO> selectList(){
		
		return dao.selectList();
	}
	
	@GetMapping("/members/{username}")
	public MemberDTO selectOne(@PathVariable String username){
		
		return dao.selectOne(username);
	}
	
	
	/*
	 * 		put과 delete 도 데이터는 요청 body에 있으므로 json으로 받아야함!!
	 * 		URI 파라미터 (pathvariable)
	 */
	
	@PutMapping("/members/{username}")
	public MemberDTO update(@PathVariable String username, @RequestBody MemberDTO dto) {
		dto.setUsername(username);
		dao.update(dto);
		
		return dao.selectOne(dto.getUsername());
	}
	
	@DeleteMapping("/members/{username}")
	public MemberDTO delete(@PathVariable String username) {
		MemberDTO dto = dao.selectOne(username);
		dao.delete(username);
		
		return dto;
	}
	
	//파라미터 명과 맞춰주자
	@PostMapping(value ="/images",produces="text/plain; charset=UTF-8")
	public String upload(@RequestPart MultipartFile attach,HttpServletRequest req) throws IllegalStateException, IOException {
		
		
		String path = req.getServletContext().getRealPath("/resources");
		File myfiles = new File(path+File.separator+attach.getOriginalFilename());
		
		
		attach.transferTo(myfiles);
		System.out.println("기타 파리미터 받기 : " + req.getParameter("title"));
		
		return "File upload Success";
	}
	
	
	/*
	  	RestTemplate
	 	- Spring 3.0부터 지원하는 내장 클래스
		- Rest방식으로 HTTP 통신을 동기 방식으로 쉽게 할수 있는  템플릿
		- 기본적으로 Connection pool을 사용하지 않아서
		  많은 요청을 하면 TIME_WAIT로 인해 자원이 점점 부족해져
		  서비스에 어려움이 있다
		- 내부적으로 java.net.HttpURLConnection 사용
		-요청을 보낼때는 HttpEntity< Map혹은 DTO,HttpHeaders>타입에 요청헤더와 요청바디(데이타) 설정
		-응답을 받을때는 ResponseEntity<Map혹은 DTO>
	
	 	
	 */
	//https://jsonplaceholder.typicode.com/서버 (Rest API 서버)로 HTTP로 요청하기
	//https://www.jsonschema2pojo.org/
	@Autowired
	private RestConfig config;
	
	
	@GetMapping(value ="/photos",produces="application/json; charset=UTF-8")
	public Photo[] getPhotos(){
		//요청헤더 설정 [해당 데이터를 보내기 위해] 
		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json; charset=UTF-8");
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		headers.add("Authorization","key=발급받은 키값"); //요청헤더에 발급 받은 키값을 전송해야하는 경우 이렇게 추가함
		//2. 요청 헤더정보등을 담은 Entity
		// DTO 혹은 Map 에는 요청시 서버에 보낼 데이터를 담음!
		// HttpEntity<DTO or Entity> entity = new HttpEntity(DTO or Map, headers);
		
		
		
		HttpEntity entity = new HttpEntity(headers);
		//3. RestTamplate 요청보내기
		// String urL = "요청URL";
		//요청 URI에 한글 포함시키는 UriComponents로 객체 생성후 toString()메소드를 통해 사용함
		//UriComponents uri = UriComponentsBuilder.fromHttpUrl(urL).build();
		
		String uri = "https://jsonplaceholder.typicode.com/photos";
/*		
  		//이때는 반환타입 List<Photo>로
		//동기방식이기 때문에 받아야 다음코드가 진행됨
		//비동기의 의미는 결과를 받든 안받는 진행됨을 의미함
		ResponseEntity<List<Photo>> response =  config.restTemplate().exchange(
				uri, 
				HttpMethod.GET, 
				entity,		//요청해더 
				new ParameterizedTypeReference<List<Photo>>() {	
				}	//그냥 Parameterized 안쓰고 List.class 로받아도됨(제네릭도 List로만 맞춰주면 다받음)
				);	//응답을 받을 데이터 타입. Map으로 받을 때는 Map.class 
//		
//		System.out.println("Response Code : "  + response.getStatusCode());
//		System.out.println("Response Headers : "  + response.getHeaders());
		for(Photo atom : response.getBody()) {
			System.out.println("Response body(Real Data) : "  + atom.toString());
			
		}
		//System.out.println("This Object -> " + response);
*/		
		ResponseEntity<Photo[]> response =  config.restTemplate().exchange(
				uri, 
				HttpMethod.GET, 
				entity,		//요청해더 
				Photo[].class
				);	//응답을 받을 데이터 타입. Map으로 받을 때는 Map.class 

		System.out.println("Response Code : "  + response.getStatusCode());
		System.out.println("Response Headers : "  + response.getHeaders());
		for(Photo atom : response.getBody()) {
			System.out.println("Response body(Real Data) : "  + atom);
			
		}
		System.out.println("This Object -> " + response);
		return response.getBody();
	}
	
	
	
}
