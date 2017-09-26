package com.datapine.datapineAPI.model;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
public class ResponseStatistic {

	private Double totalRequests;
	
	private Double totalQueries;
	
	private Chart chart;

	/**
	 * @return the totalRequests
	 */
	public Double getTotalRequests() {
		return totalRequests;
	}

	/**
	 * @param totalRequests the totalRequests to set
	 */
	public void setTotalRequests(Double totalRequests) {
		this.totalRequests = totalRequests;
	}

	/**
	 * @return the totalQueries
	 */
	public Double getTotalQueries() {
		return totalQueries;
	}

	/**
	 * @param totalQueries the totalQueries to set
	 */
	public void setTotalQueries(Double totalQueries) {
		this.totalQueries = totalQueries;
	}

	/**
	 * @return the chart
	 */
	public Chart getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(Chart chart) {
		this.chart = chart;
	}
	
	
}
