package com.mivek.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the BECMG part of a metar.
 * 
 * @author mivek
 *
 */
public class BECMG {
	private Time start;
	private Time end;
	private List<Cloud> clouds;
	private List<WeatherCondition> weatherConditions;

	public BECMG() {
		clouds = new ArrayList<Cloud>();
		weatherConditions = new ArrayList<WeatherCondition>();
	}

	/**
	 * @return the start. The start of the changement.
	 */
	public Time getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Time start) {
		this.start = start;
	}

	/**
	 * @return the end. The end of the changement.
	 */
	public Time getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Time end) {
		this.end = end;
	}

	/**
	 * @return the clouds
	 */
	public List<Cloud> getClouds() {
		return clouds;
	}

	/**
	 * @return the weatherConditions
	 */
	public List<WeatherCondition> getWeatherConditions() {
		return weatherConditions;
	}

	/**
	 * 
	 * @param c
	 */
	public void addCloud(Cloud c) {
		clouds.add(c);
	}

	/**
	 * 
	 * @param wc
	 */
	public void addWeatherCondition(WeatherCondition wc) {
		weatherConditions.add(wc);
	}
}