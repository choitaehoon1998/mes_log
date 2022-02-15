package com.broanex.mes_log.repository;

import com.broanex.mes_log.dto.MemberResponseDto;
import com.broanex.mes_log.entity.Member;

import java.util.HashMap;
import java.util.List;

public interface CustomMemberRepository {
	List<Member> findByParam(HashMap<String, String> hashMap);

	List<MemberResponseDto> findByParam2(HashMap<String, String> stringStringHashMap);
}
