package com.broanex.mes_log.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs")
@Getter
public class Log {
	@Id
	private String _id;

	private String userName;
	private String companyCode;
	private String exceptionClass;
	private String exceptionMessage;
	private String method;
	private String requestUri;
	private String remoteHost;
	private String params;
	private LocalDateTime sendDateTime;

	@Builder
	public Log(String userName, String exceptionClass, String exceptionMessage, String method, String requestUri,
	           String remoteHost, String params, String companyCode) {
		this.userName = userName;
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
		this.method = method;
		this.requestUri = requestUri;
		this.remoteHost = remoteHost;
		this.params = params;
		this.sendDateTime = LocalDateTime.now();
		this.companyCode = companyCode;
	}
}
