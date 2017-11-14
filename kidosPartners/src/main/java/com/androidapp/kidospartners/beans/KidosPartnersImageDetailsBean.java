package com.androidapp.kidospartners.beans;

public class KidosPartnersImageDetailsBean {

	private String imgurl;
	private String name;
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "KidosPartnersImageDetailsBean [imgurl=" + imgurl + ", name=" + name
				+ "]";
	}
	
	
}
