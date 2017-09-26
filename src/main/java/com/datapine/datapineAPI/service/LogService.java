package com.datapine.datapineAPI.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.stereotype.Service;

import com.datapine.datapineAPI.model.Chart;
import com.datapine.datapineAPI.model.RequestStatistic;
import com.datapine.datapineAPI.model.ResponseStatistic;
import com.datapine.datapineAPI.model.Serie;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
@Service
public class LogService {

	private static final String SECONDS = "seconds";

	private static final String MINUTES = "minutes";

	private MultivaluedMap<LocalDateTime, Object> requests = new MultivaluedHashMap<>(); 
	
	private MultivaluedMap<LocalDateTime, Object> queries = new MultivaluedHashMap<>();
	
	//	serie optional with the simple moving average. This serie logs the invalid request 
	private MultivaluedMap<LocalDateTime, Object> invalidRequests = new MultivaluedHashMap<>();
	
	public void saveRequestLog() {
		LocalDateTime data = LocalDateTime.now().withNano(0);
		this.requests.add(data, "request");
	}
	
	public void saveQueryLog() {
		LocalDateTime data = LocalDateTime.now().withNano(0);
		this.queries.add(data, "query");
	}
	
	public void saveInvalidRequestLog() {
		LocalDateTime data = LocalDateTime.now().withNano(0);
		this.invalidRequests.add(data, "invalidRequest");
	}

	public ResponseStatistic getStatistic(RequestStatistic request) {
		
		ResponseStatistic response = new ResponseStatistic();
		response.setChart(new Chart());
		
		if (request.getLast() == null || request.getLast() < 1 || request.getTimeUnit() == null || request.getTimeUnit().trim().isEmpty()
				|| (!request.getTimeUnit().equals(MINUTES) && !request.getTimeUnit().equals(SECONDS))) {
			return response;
		}
		
		Double totalRequests = 0.0;
		Double totalQueries = 0.0;
		
		Serie serieRequest = new Serie("requests", new ArrayList<>());
		Serie serieQuery = new Serie("queries", new ArrayList<>());
		Serie serieAverageInvalidRequest = new Serie("averageInvalidRequest", new ArrayList<>());
		
		LocalDateTime current = null;
		LocalDateTime end = LocalDateTime.now().withNano(0);
		
		if(request.getTimeUnit().trim().equals(MINUTES)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
			current = end.minusMinutes(request.getLast());
			
			while(!current.equals(end)) {
				response.getChart().getCategories().add(formatter.format(current));
				
				totalRequests = handlerRequestsByMinute(totalRequests, serieRequest, current, formatter);
				
				totalQueries = handlerQueriesByMinutes(totalQueries, serieQuery, current, formatter);
				
				handlerInvalidRequestsByMinutes(request, serieAverageInvalidRequest, current, formatter);
				
				current = current.plusMinutes(1);
			}
		}
		
		if (request.getTimeUnit().trim().equals(SECONDS)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
			current = end.minusSeconds(request.getLast());

			while (!current.equals(end)) {

				response.getChart().getCategories().add(formatter.format(current));

				totalRequests = handlerRequestsBySeconds(totalRequests, serieRequest, current);

				totalQueries = handlerQueriesBySeconds(totalQueries, serieQuery, current);
				
				handlerInvalidRequestsBySeconds(request, serieAverageInvalidRequest, current);

				current = current.plusSeconds(1);
			}
		}
		
		response.setTotalRequests(totalRequests);
		response.setTotalQueries(totalQueries);
		response.getChart().getSeries().add(serieRequest);
		response.getChart().getSeries().add(serieQuery);
		
		if(request.getMavgPoints() != null && request.getMavgPoints() > 0) {
			response.getChart().getSeries().add(serieAverageInvalidRequest);
		}
		
		return response;
	}
	
	private void handlerInvalidRequestsBySeconds(RequestStatistic request, Serie serieAverageInvalidRequest, LocalDateTime current) {
		if(request.getMavgPoints() != null && request.getMavgPoints() > 0) {
			List<Object> invalidRequestByDate = invalidRequests.get(current);
			
			Integer sizeInvalidRequest = invalidRequestByDate != null ? invalidRequestByDate.size() : 0;

			serieAverageInvalidRequest.getData().add(sizeInvalidRequest/request.getMavgPoints());
		}
	}

	private Double handlerQueriesBySeconds(Double totalQueries, Serie serieQuery, LocalDateTime current) {
		List<Object> queriesByDate = queries.get(current);
		
		Double sizeQuery = queriesByDate != null ? queriesByDate.size() : 0.0;

		totalQueries += sizeQuery;
		serieQuery.getData().add(sizeQuery);
		return totalQueries;
	}

	private Double handlerRequestsBySeconds(Double totalRequests, Serie serieRequest, LocalDateTime current) {
		List<Object> requestsByDate = requests.get(current);

		Double sizeRequest = requestsByDate != null ? requestsByDate.size() : 0.0;

		totalRequests += sizeRequest;
		serieRequest.getData().add(sizeRequest);
		return totalRequests;
	}

	@SuppressWarnings("rawtypes")
	private void handlerInvalidRequestsByMinutes(RequestStatistic request, Serie serieAverageInvalidRequest, LocalDateTime current,
			DateTimeFormatter formatter) {
		
		if(request.getMavgPoints() != null && request.getMavgPoints() > 0) {
			// Getting a Set of Key-value pairs
			Set entrySet = invalidRequests.entrySet();

			// Obtaining an iterator for the entry set
			Iterator it = entrySet.iterator();
			
			Integer sizeInvalidRequest = 0;
			
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				
				if(formatter.format(current).equals(formatter.format((TemporalAccessor) me.getKey()))) {
					
					List<Object> invalidRequestsByDate = invalidRequests.get(me.getKey());
					
					sizeInvalidRequest += invalidRequestsByDate.size();
				}
			}
			
			serieAverageInvalidRequest.getData().add(sizeInvalidRequest/request.getMavgPoints());
		}
	}

	@SuppressWarnings("rawtypes")
	private Double handlerQueriesByMinutes(Double totalQueries, Serie serieQuery, LocalDateTime current,
			DateTimeFormatter formatter) {

		// Getting a Set of Key-value pairs
		Set entrySet = queries.entrySet();

		// Obtaining an iterator for the entry set
		Iterator it = entrySet.iterator();
		
		Double sizeQuery = 0.0;
		
		while (it.hasNext()) {
			Map.Entry me = (Map.Entry) it.next();
			
			if(formatter.format(current).equals(formatter.format((TemporalAccessor) me.getKey()))) {
				List<Object> queriesByDate = queries.get(me.getKey());
				
				sizeQuery += queriesByDate.size();
				totalQueries += queriesByDate.size();
			}
		}
		
		serieQuery.getData().add(sizeQuery);
		return totalQueries;
	}

	@SuppressWarnings("rawtypes")
	private Double handlerRequestsByMinute(Double totalRequests, Serie serieRequest, LocalDateTime current,
			DateTimeFormatter formatter) {
		
		// Getting a Set of Key-value pairs
		Set entrySet = requests.entrySet();

		// Obtaining an iterator for the entry set
		Iterator it = entrySet.iterator();
		
		Double sizeRequest = 0.0;

		while (it.hasNext()) {
			Map.Entry me = (Map.Entry) it.next();

			if(formatter.format(current).equals(formatter.format((TemporalAccessor) me.getKey()))) {
				List<Object> requestsByDate = requests.get(me.getKey());

				sizeRequest += requestsByDate.size();
				totalRequests += requestsByDate.size();
			} 
		}
		
		serieRequest.getData().add(sizeRequest);
		return totalRequests;
	}

}
