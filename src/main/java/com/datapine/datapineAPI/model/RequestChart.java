package com.datapine.datapineAPI.model;

import java.util.List;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
public class RequestChart {

	private List<String> dimensions;
	
	private List<String> measures;

	/**
	 * 
	 */
	public RequestChart() {
		super();
	}

	/**
	 * @param dimensions
	 * @param measures
	 */
	public RequestChart(List<String> dimensions, List<String> measures) {
		super();
		this.dimensions = dimensions;
		this.measures = measures;
	}

	/**
	 * @return the dimensions
	 */
	public List<String> getDimensions() {
		return dimensions;
	}

	/**
	 * @param dimensions the dimensions to set
	 */
	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * @return the measures
	 */
	public List<String> getMeasures() {
		return measures;
	}

	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(List<String> measures) {
		this.measures = measures;
	}

}
