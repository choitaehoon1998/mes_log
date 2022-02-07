package com.broanex.mes_log.repository;

import com.broanex.mes_log.document.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface CustomLogRepository {

	Page<Log> findByParam(HashMap<String,Object> hashmap, Pageable pageable);
}
