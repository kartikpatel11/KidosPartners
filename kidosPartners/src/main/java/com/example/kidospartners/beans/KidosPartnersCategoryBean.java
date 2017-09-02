package com.example.kidospartners.beans;

public class KidosPartnersCategoryBean {
	private String catId;
	private String catImg;
	private String catName;
	private String catbackground;
	
	
	
	public String getCatbackground() {
		return catbackground;
	}



	public void setCatbackground(String catbackground) {
		this.catbackground = catbackground;
	}



	public String getCatId() {
		return catId;
	}



	public void setCatId(String catId) {
		this.catId = catId;
	}



	public String getCatImg() {
		return catImg;
	}



	public void setCatImg(String catImg) {
		this.catImg = catImg;
	}



	public String getCatName() {
		return catName;
	}



	public void setCatName(String catName) {
		this.catName = catName;
	}



	@Override
	public String toString() {
		return "KidosPartnersCategoryBean [catId=" + catId + ", catImg="
				+ catImg + ", catName=" + catName + ", catbackground="
				+ catbackground + "]";
	}

}
