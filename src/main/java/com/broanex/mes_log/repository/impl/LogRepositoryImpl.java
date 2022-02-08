package com.broanex.mes_log.repository.impl;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 Log을 관리하는 Repository 역활을 한다.
 * 관련 DB 테이블 : logs
 * */

import com.broanex.mes_log.document.Log;
import com.broanex.mes_log.repository.CustomLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. findByParam R:[List<Log>] P:[HashMap<String,String>]  : logs 에서 hashMap 에 담긴 정보들에 부합하는 정보들만 조회한다.
 * 2. likeUserName R:[x] P:[String ,Query]                  : UserName 이 포함된것만 조회하도록 한다.
 * 3. likeCompanyCode R:[x] P:[String ,Query]               : companyCode 이 포함된것만 조회하도록 한다.
 * 4. eqExceptionClass R:[x] P:[String ,Query]              : exceptionClass 가 같은것만 조회하도록 한다.
 * 5. eqExceptionMessage R:[x] P:[String ,Query]            : exceptionMessage 가 같은것만 조회하도록 한다.
 * 6. eqMethod R:[x] P:[String ,Query]                      : Method 가 같은것만 조회하도록 한다.
 * 7. eqRequestUri R:[x] P:[String ,Query]                  : RequestUri 가 같은것만 조회하도록 한다.
 * 8. eqRemoteHost R:[x] P:[String ,Query]                  : RemoteHost 가 같은것만 조회하도록 한다.
 * 9. betweenDate R:[x] P:[LocalDate , LocalDate , Query ]  : Date 가 사이에 있는것만 조회하도록 한다.
 */

public class LogRepositoryImpl implements CustomLogRepository {

	private final MongoTemplate mongoTemplate;

	public LogRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Page<Log> findByParam(HashMap<String, Object> hashmap, Pageable pageable) {
		Query dynamicQuery = new Query();

		likeUserName((String) hashmap.get("userName"), dynamicQuery);
		likeCompanyCode((String) hashmap.get("companyCode"), dynamicQuery);
		eqExceptionClass((String) hashmap.get("exceptionClass"), dynamicQuery);
		eqExceptionMessage((String) hashmap.get("exceptionMessage"), dynamicQuery);
		eqMethod((String) hashmap.get("method"), dynamicQuery);
		eqRequestUri((String) hashmap.get("requestUri"), dynamicQuery);
		eqRemoteHost((String) hashmap.get("remoteHost"), dynamicQuery);
		betweenDate((LocalDate) hashmap.get("startDate"), (LocalDate) hashmap.get("endDate"), dynamicQuery);
		dynamicQuery.with(Sort.by(Sort.Order.desc("sendDateTime")));

		if (pageable != null) {
			dynamicQuery.with(pageable);
		}

		List<Log> logList = mongoTemplate.find(dynamicQuery, Log.class);

		long total = mongoTemplate.count(dynamicQuery, Log.class);
		return PageableExecutionUtils.getPage(logList, pageable, () -> mongoTemplate.count(Query.of(dynamicQuery).limit(-1).skip(-1), Log.class));

	}

	private void likeUserName(String userName, Query dynamicQuery) {
		if (userName != null) {
			dynamicQuery.addCriteria(Criteria.where("userName").regex(".*" + userName + ".*"));
		}
	}

	private void likeCompanyCode(String companyCode, Query dynamicQuery) {
		if (companyCode != null) {
			dynamicQuery.addCriteria(Criteria.where("companyCode").regex(".*" + companyCode + ".*"));
		}
	}

	private void eqExceptionClass(String exceptionClass, Query dynamicQuery) {
		if (exceptionClass != null) {
			dynamicQuery.addCriteria(Criteria.where("exceptionClass").regex(".*" + exceptionClass + ".*"));
		}
	}

	private void eqExceptionMessage(String exceptionMessage, Query dynamicQuery) {
		if (exceptionMessage != null) {
			dynamicQuery.addCriteria(Criteria.where("exceptionMessage").regex(".*" + exceptionMessage + ".*"));
		}
	}

	private void eqMethod(String method, Query dynamicQuery) {
		if (method != null) {
			dynamicQuery.addCriteria(Criteria.where("method").regex(".*" + method + ".*"));
		}
	}

	private void eqRequestUri(String requestUri, Query dynamicQuery) {
		if (requestUri != null) {
			dynamicQuery.addCriteria(Criteria.where("requestUri").regex(".*" + requestUri + ".*"));
		}
	}

	private void eqRemoteHost(String remoteHost, Query dynamicQuery) {
		if (remoteHost != null) {
			dynamicQuery.addCriteria(Criteria.where("remoteHost").regex(".*" + remoteHost + ".*"));
		}
	}

	private void betweenDate(LocalDate startDate, LocalDate endDate, Query dynamicQuery) {
		if (startDate != null && endDate != null) {
			LocalDateTime s = LocalDateTime.of(startDate, LocalTime.of(00, 00, 00));
			LocalDateTime e = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
			dynamicQuery.addCriteria(Criteria
					.where("sendDateTime")
					.gte(s)
					.lt(e));
		}
	}
}
