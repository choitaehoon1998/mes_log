package com.broanex.mes_log.service;

import com.broanex.mes_log.dto.LoginRequestDto;
import com.broanex.mes_log.dto.MemberRequestDto;
import com.broanex.mes_log.entity.Member;
import com.broanex.mes_log.repository.MemberRepository;
import com.broanex.mes_log.util.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
		this.passwordEncoder = passwordEncoder;
		this.memberRepository = memberRepository;
	}

	public void createMember(MemberRequestDto memberRequestDto) {

		memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));

		Member member = Member.dtoToEntity(memberRequestDto);
		Member lastMember = memberRepository.findFirst1ByOrderByIdDesc();
		if (lastMember == null) {
			member.setId(1);
		} else {
			member.setId(lastMember.getId() + 1);
		}

		memberRepository.save(member);
	}


	public HashMap<String, String> login(LoginRequestDto loginRequestDto) {
		String email = loginRequestDto.getEmail();

		Member member = memberRepository.findTop1ByEmail(email);
		if (member == null) {
			return null;
		} else {
			if (passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()) &&
					member.isApproved()) {
				HashMap<String, String> map = new TokenUtils().generateAccessTokenAndRefreshToken(member.getId());
				String refreshToken = map.get("refreshToken");
				member.setRefreshToken(refreshToken);
				memberRepository.save(member);
				return map;
			} else {
				return null;
			}
		}


	}

	public void updateMember(Member member) {
		if (memberRepository.existsById(member.getId())) {
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			memberRepository.save(member);
		}
	}

	public ResponseEntity updateToken(HttpServletRequest request) {
		String refreshToken = request.getHeader("refreshToken");
		TokenUtils tokenUtils = new TokenUtils();
		int id = tokenUtils.getId(refreshToken);
		if (memberRepository.existsById(id)) {
			Member member = memberRepository.getOne(id);
			String dbRefreshToken = member.getRefreshToken();
			if (dbRefreshToken != null && refreshToken != null && !dbRefreshToken.isEmpty()
					&& !refreshToken.isEmpty() && refreshToken.equals(dbRefreshToken))
				return ok(new HashMap<String, String>() {{
					put("accessToken", tokenUtils.generateAccessToken(id));
				}});
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap());
	}

	public List<Member> findAllUser() {
		return memberRepository.findAll();
	}
}
