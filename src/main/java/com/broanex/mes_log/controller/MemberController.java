package com.broanex.mes_log.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 Member을 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 : member
 * */

import com.broanex.mes_log.annotation.Auth;
import com.broanex.mes_log.dto.LoginRequestDto;
import com.broanex.mes_log.dto.MemberRequestDto;
import com.broanex.mes_log.dto.MemberResponseDto;
import com.broanex.mes_log.entity.Member;
import com.broanex.mes_log.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/*
 * 동작방식
 * 1. /member            [GET]    : 전체 멤버의 정보를 조회한다.
 * 2. /member            [POST]   : 신규 멤버를 생성후, 저장한다.
 * 3. /member            [PUT]    : 기존 멤버를 업데이트한다.
 * 3. /login             [POST]   : EMAIL 과 PASSWORD 를 통하여, 로그인한다 accessToken 과 refreshToken 을 리턴받는다.
 * 3. /newAccessToken    [GET]    : refreshToken 을 통하여, accessToken 을 받는다.
 */
@Controller
//@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS}
//		, originPatterns = "*", allowedHeaders = {"Content-Type", "X-PINGOTHER"}, maxAge = 86400, allowCredentials = "true")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@Auth
	@GetMapping("/member")
	public ResponseEntity<List<Member>> getMemberInfo(@RequestParam(required = false) String email,
	                                                  @RequestParam(required = false) String isApproved,
	                                                  @RequestParam(required = false) String name) {
		return ok(memberService.findAllUser(new HashMap<String, String>() {{
			put("email", email);
			put("isApproved", isApproved);
			put("name", name);
		}}));
	}

	@Auth
	@PostMapping("/member")
	public ResponseEntity<Void> createMember(@RequestBody MemberRequestDto memberRequestDto) {
		memberService.createMember(memberRequestDto);
		return ok().build();
	}

	@Auth
	@PutMapping("/member")
	public ResponseEntity<Void> updateMember(@RequestBody Member member) {
		memberService.updateMember(member);
		return ok().build();
	}
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) {
		HashMap<String, String> map = memberService.login(loginRequestDto);
		if(map== null){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ok(map);
	}

	@GetMapping("/newAccessToken")
	public ResponseEntity<HashMap<String, String>> updateToken(HttpServletRequest request) {
		return memberService.updateToken(request);
	}

	@GetMapping("/member2")
	public ResponseEntity<List<MemberResponseDto>> getByParam(@RequestParam(required = false) String email,
	                                                          @RequestParam(required = false) String isApproved,
	                                                          @RequestParam(required = false) String name){
		return ok(memberService.findByParam(new HashMap<String, String>() {{
			put("email", email);
			put("isApproved", isApproved);
			put("name", name);
		}}));
	}
}
