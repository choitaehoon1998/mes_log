package com.broanex.mes_log.service;

import com.broanex.mes_log.document.Log;
import com.broanex.mes_log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class LogService {

	private final LogRepository logRepository;

	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}


	public void createLog(HashMap<String, String> body) {
		List<Log> logList = new ArrayList<>();

		for (int i = 0; i < 100000; i++) {
			logList.add(
					Log.builder()
							.userName(body.get("userName") + i)
							.companyCode(body.get("companyCode") + i)
							.exceptionClass(body.get("exceptionClass") + i)
							.exceptionMessage(body.get("exceptionMessage") + i)
							.method(body.get("method") + i)
							.requestUri(body.get("requestUri") + i)
							.remoteHost(body.get("remoteHost") + i)
							.build()

			);
		}
		logRepository.saveAll(logList);


//		logRepository.save(
//				Log.builder()
//						.userName(body.get("userName"))
//						.companyCode(body.get("companyCode"))
//						.exceptionClass(body.get("exceptionClass"))
//						.exceptionMessage(body.get("exceptionMessage"))
//						.method(body.get("method"))
//						.requestUri(body.get("requestUri"))
//						.remoteHost(body.get("remoteHost"))
//						.build()
//		);

	}

	public List<Log> findByParam(HashMap<String, String> stringStringHashMap) {

		return null;
	}
}
