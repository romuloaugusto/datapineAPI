/**
 * 
 */
package com.datapine.datapineAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.datapine.datapineAPI.model.RequestStatistic;
import com.datapine.datapineAPI.model.ResponseStatistic;
import com.datapine.datapineAPI.service.StatisticService;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
@RestController
public class StatisticController {
	
	@Autowired
	private StatisticService statisticService;
	
	@PostMapping(value="/statistics")
	public ResponseStatistic getChartStatistic(@RequestBody final RequestStatistic request) {
		return statisticService.getStatistics(request);
	}
	
}
