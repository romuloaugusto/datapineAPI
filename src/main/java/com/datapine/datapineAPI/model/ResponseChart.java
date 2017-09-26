package com.datapine.datapineAPI.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
public class ResponseChart {
	
	private List<String> categories = new ArrayList<>();
	
	private List<Serie> series = new ArrayList<>();

	/**
	 * 
	 */
	public ResponseChart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param categories
	 * @param series
	 */
	public ResponseChart(List<String> categories, List<Serie> series) {
		super();
		this.categories = categories;
		this.series = series;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * @return the series
	 */
	public List<Serie> getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(List<Serie> series) {
		this.series = series;
	}
	

}
