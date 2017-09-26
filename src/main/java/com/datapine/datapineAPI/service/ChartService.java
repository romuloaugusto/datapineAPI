/**
 * 
 */
package com.datapine.datapineAPI.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datapine.datapineAPI.model.RequestChart;
import com.datapine.datapineAPI.model.ResponseChart;
import com.datapine.datapineAPI.model.Serie;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
@Service
public class ChartService {

	private static Map<String, Serie> series = new HashMap<>();
	private static List<String> categories = new ArrayList<>();

	@Autowired
	private LogService logService;

	static {
		// mocked data
		series.put("revenue", new Serie("revenue", Arrays.asList(625.0, 620.0, 600.0, 400.0, 250.0)));
		series.put("champions", new Serie("champions", Arrays.asList(12.0, 5.0, 5.0, 5.0, 7.0)));
		series.put("leagues", new Serie("leagues", Arrays.asList(33.0, 24.0, 26.0, 18.0, 18.0)));
		categories = Arrays.asList("Real Madrid", "Barcelona", "Bayern Munich", "Liverpool", "Milan");
	}

	public ResponseChart getChart(final RequestChart request) {

		// save the request log
		this.logService.saveRequestLog();

		ResponseChart result = new ResponseChart();

		if (!isValidRequest(request)) {
			// save the invalid request log (serie optional with the simple moving average)
			this.logService.saveInvalidRequestLog();
			return result;
		}

		for (String serieRequest : request.getMeasures()) {

			// save the query log of each measures from the request even that measures doesn't belong to result set configured
			this.logService.saveQueryLog();

			if (series.containsKey(serieRequest)) {
				result.getSeries().add(series.get(serieRequest));
			}
		}

		if (request.getDimensions().get(0).equals("team")) {
			result.setCategories(categories);
		}

		// logService.addChartStatistic(request);

		return result;
	}
	
	private boolean isValidRequest(final RequestChart request) {
		
		if (request.getDimensions() == null || request.getDimensions().isEmpty() || request.getMeasures() == null
				|| request.getMeasures().isEmpty()) {
			return false;
		}
		
		return true;
	}

}
