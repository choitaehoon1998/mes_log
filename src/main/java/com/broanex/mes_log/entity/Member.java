package com.broanex.mes_log.entity;

import com.broanex.mes_log.dto.MemberRequestDto;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Member {

	@Id
	private int id;
	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String refreshToken;
	private Boolean isApproved;

	public static Member dtoToEntity(MemberRequestDto dto) {
		Member member = new Member();
		member.setEmail(dto.getEmail());
		member.setPassword(dto.getPassword());
		member.setName(dto.getName());
		member.setIsApproved(dto.getIsApproved());
		return member;
	}
}
