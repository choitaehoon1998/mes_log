package com.broanex.mes_log.controller;

import com.broanex.mes_log.document.Log;
import com.broanex.mes_log.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class LogController {

	private final LogService logService;

	public LogController(LogService logService) {
		this.logService = logService;
	}


	@PostMapping(value = "log")
	public ResponseEntity<Void> logPost(@RequestBody HashMap<String, String> body) {
		logService.createLog(body);
		return null;
	}

	@GetMapping(value = "log")
	public ResponseEntity<List<Log>> getLog(@RequestParam(required = false) String userName,
	                                        @RequestParam(required = false) String companyCode,
	                                        @RequestParam(required = false) String exceptionMessage) {

		List<Log> logList = logService.findByParam(new HashMap<String, String>() {{
			put("userName", userName);
			put("companyCode", companyCode);
			put("exceptionMessage", exceptionMessage);
		}});

		return ok(logList);
	}
}
