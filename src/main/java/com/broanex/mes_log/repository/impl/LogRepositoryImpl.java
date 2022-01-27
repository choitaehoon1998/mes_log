package com.broanex.mes_log.repository.impl;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 Log을 관리하는 Repository 역활을 한다.
 * 관련 DB 테이블 : logs
 * */

import com.broanex.mes_log.document.Log;
import com.broanex.mes_log.repository.CustomLogRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. findByParam R:[List<Log>] P:[HashMap<String,String>]  : logs 에서 hashMap 에 담긴 정보들에 부합하는 정보들만 조회한다.
 * 2. eqUserName R:[x] P:[String ,Query]                    : UserName 이 같은것만 조회하도록 한다.
 * 3. eqCompanyCode R:[x] P:[String ,Query]                 : companyCode 가 같은것만 조회하도록 한다.
 * 4. eqExceptionClass R:[x] P:[String ,Query]              : exceptionClass 가 같은것만 조회하도록 한다.
 * 5. eqExceptionMessage R:[x] P:[String ,Query]            : exceptionMessage 가 같은것만 조회하도록 한다.
 * 6. eqMethod R:[x] P:[String ,Query]                      : Method 가 같은것만 조회하도록 한다.
 * 7. eqRequestUri R:[x] P:[String ,Query]                  : RequestUri 가 같은것만 조회하도록 한다.
 * 8. eqRemoteHost R:[x] P:[String ,Query]                  : RemoteHost 가 같은것만 조회하도록 한다.
 */

public class LogRepositoryImpl implements CustomLogRepository {

	private final MongoTemplate mongoTemplate;

	public LogRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Log> findByParam(HashMap<String, String> hashmap) {
		Query dynamicQuery = new Query();
		eqUserName(hashmap.get("userName"), dynamicQuery);
		eqCompanyCode(hashmap.get("companyCode"), dynamicQuery);
		eqExceptionClass(hashmap.get("exceptionClass"), dynamicQuery);
		eqExceptionMessage(hashmap.get("exceptionMessage"), dynamicQuery);
		eqMethod(hashmap.get("method"), dynamicQuery);
		eqRequestUri(hashmap.get("requestUri"), dynamicQuery);
		eqRemoteHost(hashmap.get("remoteHost"), dynamicQuery);

		return mongoTemplate.find(dynamicQuery, Log.class);
	}

	private void eqUserName(String userName, Query dynamicQuery) {
		if (userName != null) {
			dynamicQuery.addCriteria(Criteria.where("userName").is(userName));
		}
	}

	private void eqCompanyCode(String companyCode, Query dynamicQuery) {
		if (companyCode != null) {
			dynamicQuery.addCriteria(Criteria.where("companyCode").is(companyCode));
		}
	}

	private void eqExceptionClass(String exceptionClass, Query dynamicQuery) {
		if (exceptionClass != null) {
			dynamicQuery.addCriteria(Criteria.where("exceptionClass").is(exceptionClass));
		}
	}

	private void eqExceptionMessage(String exceptionMessage, Query dynamicQuery) {
		if (exceptionMessage != null) {
			dynamicQuery.addCriteria(Criteria.where("exceptionMessage").is(exceptionMessage));
		}
	}

	private void eqMethod(String method, Query dynamicQuery) {
		if (method != null) {
			dynamicQuery.addCriteria(Criteria.where("method").is(method));
		}
	}

	private void eqRequestUri(String requestUri, Query dynamicQuery) {
		if (requestUri != null) {
			dynamicQuery.addCriteria(Criteria.where("requestUri").is(requestUri));
		}
	}

	private void eqRemoteHost(String remoteHost, Query dynamicQuery) {
		if (remoteHost != null) {
			dynamicQuery.addCriteria(Criteria.where("remoteHost").is(remoteHost));
		}
	}

}
