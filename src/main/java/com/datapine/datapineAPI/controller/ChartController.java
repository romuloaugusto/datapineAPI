/**
 * 
 */
package com.datapine.datapineAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.datapine.datapineAPI.model.RequestChart;
import com.datapine.datapineAPI.model.ResponseChart;
import com.datapine.datapineAPI.service.ChartService;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
@RestController
public class ChartController {
	
	@Autowired
	private ChartService chartService;
	
	@PostMapping(value="/chart")
	public ResponseChart getChart(@RequestBody final RequestChart request) {
		return chartService.getChart(request);
	}
	
}
