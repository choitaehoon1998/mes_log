package com.broanex.mes_log.repository;

import com.broanex.mes_log.document.Log;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, String>, CustomLogRepository {
}
