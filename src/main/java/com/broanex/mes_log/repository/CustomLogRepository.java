package com.broanex.mes_log.repository;

import com.broanex.mes_log.document.Log;

import java.util.HashMap;
import java.util.List;

public interface CustomLogRepository {

	List<Log> findByParam(HashMap<String,String> hashmap);
}
