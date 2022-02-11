package com.broanex.mes_log.repository;

import com.broanex.mes_log.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findTop1ByEmail(String email);
	Member findFirst1ByOrderByIdDesc();
}
