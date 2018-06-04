package com.cooksys.launch;

import java.util.HashSet;
import java.util.Set;

public class Person {
	public Person(){}
	public Person(Long id, String firstName, String lastName, int age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	public Person(String firstName, String lastName, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	private Long id = null;
	private String firstName;
	private String lastName;
	private int age;
	
	private Location location;
	private HashSet<Interest> interests;
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

	public Location getLocation() {
		return location;
	}
	public void setLocation(int locationId) {
		this.location = LocationDao.getById(locationId);
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public HashSet<Interest> getInterests() {
		return interests;
	}
	public void setInterests(HashSet<Interest> interests) {
		this.interests = interests;
	}
}
