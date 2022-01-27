package com.broanex.mes_log.service;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : Log를 관리하는 Service 역활을 한다.
 * 관련 DB 테이블 :  Logs
 * */

import com.broanex.mes_log.document.Log;
import com.broanex.mes_log.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. createLog R:[Log] P:[HashMap<String,String>]         : Log를 신규 저장한다.
 * 2. findByParam R:[List<Log>] P:[HashMap<String,String>] : 파라미터로 받은 HashMap에 부합하는 Log만 조회한다.
 */


@Service
public class LogService {

	private final LogRepository logRepository;

	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}


	public Log createLog(HashMap<String, String> body) {
		return logRepository.save(
				Log.builder()
						.userName(body.get("userName"))
						.companyCode(body.get("companyCode"))
						.exceptionClass(body.get("exceptionClass"))
						.exceptionMessage(body.get("exceptionMessage"))
						.method(body.get("method"))
						.requestUri(body.get("requestUri"))
						.remoteHost(body.get("remoteHost"))
						.build()
		);
	}

	public List<Log> findByParam(HashMap<String, String> hashMap) {
		return logRepository.findByParam(hashMap);
	}
}
