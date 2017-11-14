package com.androidapp.kidospartners.beans;

public class KidosPartnersClassDetailsBean {

	int activityId;
	int activityStatus;
	String name;
	String addressline1;
	String area;
	String city;
	String state;
	String pincode;
	int userid;
	String type;
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(int activityStatus) {
		this.activityStatus = activityStatus;
	}

	@Override
	public String toString() {
		return "KidosPartnersClassDetailsBean{" +
				"activityId=" + activityId +
				", activityStatus=" + activityStatus +
				", name='" + name + '\'' +
				", addressline1='" + addressline1 + '\'' +
				", area='" + area + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", pincode='" + pincode + '\'' +
				", userid='" + userid + '\'' +
				", type='" + type + '\'' +
				'}';
	}


}
