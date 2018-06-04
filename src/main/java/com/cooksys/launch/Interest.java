package com.cooksys.launch;

public class Interest {
	public Interest(){}
	public Interest(String title) {
		super();
		this.title = title;
	}
	
	private Long id = null;
	private String title;
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
