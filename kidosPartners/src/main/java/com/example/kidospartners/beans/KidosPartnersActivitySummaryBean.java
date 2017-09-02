package com.example.kidospartners.beans;

public class KidosPartnersActivitySummaryBean {

	private String activityId;
	private String name;
	private String area;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Override
	public String toString() {
		return "KidosPartnersActivitySummaryBean [activityId=" + activityId
				+ ", name=" + name + ", area=" + area + "]";
	}
	
	
}
