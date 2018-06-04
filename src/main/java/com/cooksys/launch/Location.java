package com.cooksys.launch;

public class Location {
	public Location(){}
	public Location(String city, String state, String country) {
		super();
		this.city = city;
		this.state = state;
		this.country = country;
	}
	
	private Long id = null;
	private String city;
	private String state;
	private String country;
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
