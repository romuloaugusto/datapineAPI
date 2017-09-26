package com.datapine.datapineAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datapine.datapineAPI.model.RequestStatistic;
import com.datapine.datapineAPI.model.ResponseStatistic;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
@Service
public class StatisticService {
	
	@Autowired
	private LogService logService;

	public ResponseStatistic getStatistics(RequestStatistic request) {
		return logService.getStatistic(request);
	}


}
