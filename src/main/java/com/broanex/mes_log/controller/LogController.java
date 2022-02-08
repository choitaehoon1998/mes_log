package com.broanex.mes_log.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 Log을 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 : logs
 * */

import com.broanex.mes_log.document.Log;
import com.broanex.mes_log.service.LogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

import static org.springframework.http.ResponseEntity.ok;

/*
 * 동작방식
 * 1. /log      [POST]  : log의 정보를 전달받아 logs 에 저장한다
 * 2. /log      [GET]   : log의 인자들(userName,companyCode,exceptionClass,exceptionMessage,method,requestUri,remoteHost)을 받아 logs 에서 조회후, JSON형태로 리턴해준다.
 * 3. /log/{id} [GET]   : log의 id를 받아, 있는 ID 일경우 ID에 해당하는 LOG 를 리턴하고, 그렇지 않을경우 NULL 을 리턴한다.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogController {

	private final LogService logService;

	public LogController(LogService logService) {
		this.logService = logService;
	}


	@PostMapping(value = "log")
	public ResponseEntity<Log> logPost(@RequestBody HashMap<String, String> body) {
		Log log = logService.createLog(body);
		return ok(log);
	}

	@GetMapping(value = "log", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Log>> getLog(@RequestParam(required = false) String userName,
	                                        @RequestParam(required = false) String companyCode,
	                                        @RequestParam(required = false) String exceptionClass,
	                                        @RequestParam(required = false) String exceptionMessage,
	                                        @RequestParam(required = false) String method,
	                                        @RequestParam(required = false) String requestUri,
	                                        @RequestParam(required = false) String remoteHost,
	                                        @RequestParam(required = false)
	                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
	                                        @RequestParam(required = false)
	                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
	                                        Pageable pageable) {
		Page<Log> logList = logService.findByParam(new HashMap<String, Object>() {{
			put("userName", userName);
			put("companyCode", companyCode);
			put("exceptionClass", exceptionClass);
			put("exceptionMessage", exceptionMessage);
			put("method", method);
			put("requestUri", requestUri);
			put("remoteHost", remoteHost);
			put("startDate", startDate);
			put("endDate", endDate);
		}}, pageable);

		return ok(logList);
	}

	@GetMapping(value = "log/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Log> getLogDetailInfo(@PathVariable(value = "id") String id) {
		return ok(logService.findLogById(id));
	}
}
