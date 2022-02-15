package com.broanex.mes_log.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
	private String email;
	private String password;
	private String name;
	private Boolean isApproved;
}
