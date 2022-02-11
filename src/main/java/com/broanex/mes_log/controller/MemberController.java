package com.broanex.mes_log.controller;

import com.broanex.mes_log.annotation.Auth;
import com.broanex.mes_log.dto.LoginRequestDto;
import com.broanex.mes_log.dto.MemberRequestDto;
import com.broanex.mes_log.entity.Member;
import com.broanex.mes_log.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@Auth
	@GetMapping("/member")
	public ResponseEntity<List<Member>> getMemberInfo() {
		return ok(memberService.findAllUser());
	}

	@Auth
	@PostMapping("/member")
	public ResponseEntity<Void> createMember(@RequestBody MemberRequestDto memberRequestDto) {
		memberService.createMember(memberRequestDto);
		return null;
	}

	@Auth
	@PutMapping("/member")
	public ResponseEntity<Void> updateMember(@RequestBody Member member) {
		memberService.updateMember(member);
		return null;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) {
		HashMap<String, String> map = memberService.login(loginRequestDto);
		return ok(map);
	}

	@PostMapping("/newAccessToken")
	public ResponseEntity<Map<String, String>> updateToken(HttpServletRequest request) {
		HashMap<String, String> map = memberService.updateToken(request);
		return ok(map);
	}
}
