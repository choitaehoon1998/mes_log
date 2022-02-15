package com.broanex.mes_log.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
	private String email;
	private String name;
	private Boolean isApproved;

	public MemberResponseDto(String email, String name, Boolean isApproved) {
		this.email = email;
		this.name = name;
		this.isApproved = isApproved;
	}
}
