package com.broanex.mes_log.repository.impl;

import com.broanex.mes_log.repository.CustomLogRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

public class LogRepositoryImpl implements CustomLogRepository {

	private final MongoTemplate mongoTemplate;

	public LogRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
