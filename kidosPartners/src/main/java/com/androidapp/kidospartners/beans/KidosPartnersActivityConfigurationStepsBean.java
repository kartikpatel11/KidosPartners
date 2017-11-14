package com.androidapp.kidospartners.beans;

import java.util.ArrayList;

public class KidosPartnersActivityConfigurationStepsBean {

	private static String[] activityStepTitle=new String[]{
		"Class Details",
		"Contact Details",
		"Activity Details",
		"Location Details",
		"Images"
	};
	
	private static String[] activityStepDescription=new String[]{
			"Information like name of the class, address, city, state, pincode of the class",
			"Information about contact phone number, email, facebook, twitter, website of the class",
			"Detailed description of class along with age criteria, timings and fees",
			"Information on landmark and exact location of the class on map",
			"Some images from the class location to increase the subscription count"
		};
	
	private String stepTitle;
	private String stepDescription;
	public String getStepTitle() {
		return stepTitle;
	}
	public void setStepTitle(String stepTitle) {
		this.stepTitle = stepTitle;
	}
	public String getStepDescription() {
		return stepDescription;
	}
	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}
	@Override
	public String toString() {
		return "KidosPartnersActivityConfigurationStepsBean [stepTitle="
				+ stepTitle + ", stepDescription=" + stepDescription + "]";
	}
	
	public static ArrayList<KidosPartnersActivityConfigurationStepsBean> getActivityConfigurationSteps()
	{
		ArrayList<KidosPartnersActivityConfigurationStepsBean> list=new ArrayList<KidosPartnersActivityConfigurationStepsBean>();
		
		for(int i=0;i<activityStepTitle.length;i++)
		{
			KidosPartnersActivityConfigurationStepsBean bean=new KidosPartnersActivityConfigurationStepsBean();
			bean.setStepTitle(activityStepTitle[i]);
			bean.setStepDescription(activityStepDescription[i]);
			list.add(bean);
		}
		
		System.out.println("List==>"+list.size()+",list="+list);
		return list;
	}
}
