package com.andsotra.microservice.client.domain;

import java.io.Serializable;
import java.util.Date;

public class ClientDto implements Serializable{
	
	private int id;
	private String firstName;
	private String secondName;
	private String documentNumber;
	private String shortNameDocumentType;
	private Date birthDay;
	private String imageId;
	private String base64Image;
	
	public ClientDto(int id, String firstName, String secondName, Date birthDay, String imageId, 
			String documentNumber, String shortNameDocumentType, String base64Image) {
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.birthDay = birthDay;
		this.imageId = imageId;
		this.shortNameDocumentType = shortNameDocumentType;
		this.documentNumber = documentNumber;
		this.base64Image = base64Image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getShortNameDocumentType() {
		return shortNameDocumentType;
	}
	public void setShortNameDocumentType(String shortNameDocumentType) {
		this.shortNameDocumentType = shortNameDocumentType;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
	
	
}
