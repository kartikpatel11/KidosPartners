package com.androidapp.kidospartners.beans;

public class KidosPartnersActivitySummaryBean {

	private String activityId;
	private String name;
	private String area;
	private boolean published;
	private boolean activitydetails;
	private boolean locationdetails;
	private boolean imagesdetails;
	private boolean contactdetails;
	private KidosPartnersCategoryBean type;


	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public KidosPartnersCategoryBean getType() {
		return type;
	}

	public void setType(KidosPartnersCategoryBean type) {
		this.type = type;
	}

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
	public boolean isActivitydetails() {
		return activitydetails;
	}

	public void setActivitydetails(boolean activitydetails) {
		this.activitydetails = activitydetails;
	}

	public boolean isLocationdetails() {
		return locationdetails;
	}

	public void setLocationdetails(boolean locationdetails) {
		this.locationdetails = locationdetails;
	}

	public boolean isImagesdetails() {
		return imagesdetails;
	}

	public void setImagesdetails(boolean imagesdetails) {
		this.imagesdetails = imagesdetails;
	}

	public boolean isContactdetails() {
		return contactdetails;
	}

	public void setContactdetails(boolean contactdetails) {
		this.contactdetails = contactdetails;
	}

	public StringBuffer getSetupMissingMsg()
	{
		StringBuffer msg=new StringBuffer("Following setup is missing. \nPlease fill the details before publishing activity\n\n");
		if(!activitydetails)
			msg.append("Activity Details\n");
		if(!contactdetails)
			msg.append("Contact Details\n");
		if(!locationdetails)
			msg.append("Location Details");

		return msg;
	}


	@Override
	public String toString() {
		return "KidosPartnersActivitySummaryBean{" +
				"activityId='" + activityId + '\'' +
				", name='" + name + '\'' +
				", area='" + area + '\'' +
				", published=" + published +
				", activitydetails=" + activitydetails +
				", locationdetails=" + locationdetails +
				", imagesdetails=" + imagesdetails +
				", contactdetails=" + contactdetails +
				", type=" + type +
				'}';
	}

}
