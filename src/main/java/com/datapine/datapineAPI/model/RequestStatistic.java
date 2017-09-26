package com.datapine.datapineAPI.model;

/**
 * @author Romulo Augusto - romuloaugusto.silva@gmail.com
 *
 */
public class RequestStatistic {
	
	private Integer last;
	
	private String timeUnit;
	
	private Double mavgPoints;

	/**
	 * @return the last
	 */
	public Integer getLast() {
		return last;
	}

	/**
	 * @param last the last to set
	 */
	public void setLast(Integer last) {
		this.last = last;
	}

	/**
	 * @return the timeUnit
	 */
	public String getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * @return the mavgPoints
	 */
	public Double getMavgPoints() {
		return mavgPoints;
	}

	/**
	 * @param mavgPoints the mavgPoints to set
	 */
	public void setMavgPoints(Double mavgPoints) {
		this.mavgPoints = mavgPoints;
	}
	

}
