package com.datapine.datapineAPI.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
public class Serie {
	
	private String name;
	
	private List<Double> data = new ArrayList<>();
	
	public Serie() {
		super();
	}

	/**
	 * @param name
	 * @param data
	 */
	public Serie(String name, List<Double> data) {
		super();
		this.name = name;
		this.data = data;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the data
	 */
	public List<Double> getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(List<Double> data) {
		this.data = data;
	}
	
}
