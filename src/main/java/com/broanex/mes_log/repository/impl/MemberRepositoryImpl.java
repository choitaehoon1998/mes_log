package com.broanex.mes_log.repository.impl;

import com.broanex.mes_log.dto.MemberResponseDto;
import com.broanex.mes_log.entity.Member;
import com.broanex.mes_log.repository.CustomMemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

import static com.broanex.mes_log.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements CustomMemberRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Member> findByParam(HashMap<String, String> hashMap) {
		return queryFactory.selectFrom(member).where(
				likeName(hashMap.get("name")),
				likeEmail(hashMap.get("email")),
				isApproved(hashMap.get("isApproved"))
		).fetch();
	}

	@Override
	public List<MemberResponseDto> findByParam2(HashMap<String, String> hashMap) {
		return queryFactory.select(Projections.constructor(MemberResponseDto.class,
				member.name,
				member.email,
				member.isApproved)).where(
				likeName(hashMap.get("name")),
				likeEmail(hashMap.get("email")),
				isApproved(hashMap.get("isApproved"))
		).fetch();
	}


	private BooleanExpression likeName(String name) {
		if (StringUtils.hasText(name)) {
			return member.name.contains(name);
		}
		return null;
	}

	private BooleanExpression likeEmail(String email) {
		if (StringUtils.hasText(email)) {
			return member.email.contains(email);
		}
		return null;
	}

	private BooleanExpression isApproved(String isApproved) {
		if (StringUtils.hasText(isApproved)) {
			if (isApproved.equals("Y")) {
				return member.isApproved.eq(true);
			} else if (isApproved.equals("N")) {
				return member.isApproved.eq(false);
			}
			return null;
		}
		return null;
	}
}
