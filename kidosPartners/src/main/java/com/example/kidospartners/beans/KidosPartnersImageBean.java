package com.example.kidospartners.beans;

public class KidosPartnersImageBean {

	private String imgUrl;
	private String name;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "KidosPartnersImageBean [imgUrl=" + imgUrl + ", name=" + name
				+ "]";
	}
	
	
}
