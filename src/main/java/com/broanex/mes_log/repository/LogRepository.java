package com.broanex.mes_log.repository;

import com.broanex.mes_log.document.Log;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LogRepository extends PagingAndSortingRepository<Log, String>, CustomLogRepository {
}
