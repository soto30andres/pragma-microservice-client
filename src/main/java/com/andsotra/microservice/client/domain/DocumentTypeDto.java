package com.andsotra.microservice.client.domain;

import java.io.Serializable;
import java.util.Date;

public class DocumentTypeDto implements Serializable{
	
	private int id;
	private String name;
	private String shortName;
	public DocumentTypeDto(int id, String name, String shortName) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	
	
}
