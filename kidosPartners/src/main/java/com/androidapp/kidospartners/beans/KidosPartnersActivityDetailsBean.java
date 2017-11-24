package com.androidapp.kidospartners.beans;

import java.util.ArrayList;

/**
 * Created by Kartik on 03/09/17.
 */

public class KidosPartnersActivityDetailsBean {
    private int activityId;
    private String description;
    private String fees;
    private KidosPartnersAgeCriteriaBean age;
    private ArrayList<KidosPartnersBatchesBean> batches;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public KidosPartnersAgeCriteriaBean getAge() {
        return age;
    }

    public void setAge(KidosPartnersAgeCriteriaBean age) {
        this.age = age;
    }


    public ArrayList<KidosPartnersBatchesBean> getBatches() {
        return batches;
    }

    public void setBatches(ArrayList<KidosPartnersBatchesBean> batches) {
        this.batches = batches;
    }

    public void setFrom(String fromStr)
    {
        int from=Integer.parseInt(fromStr);
        if(age!=null)
            age.setFrom(from);
        else {
            age = new KidosPartnersAgeCriteriaBean();
            setAge(age);
            age.setFrom(from);
        }
    }

    public void setTo(String toStr)
    {
        int to = Integer.parseInt(toStr);
        if(age!=null)
            age.setTo(to);
        else {
            age = new KidosPartnersAgeCriteriaBean();
            setAge(age);
            age.setTo(to);
        }
    }

    public String getFrom()
    {
        if(age!=null)
            return String.valueOf(age.getFrom());
        else
            return "";
    }

    public String getTo()
    {
        if(age!=null)
            return String.valueOf(age.getTo());
        else
            return "";
    }
/*
    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        view.setText(Integer.toString(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        if("".equals(view.getText().toString()))
            return 0;
        else
            return Integer.parseInt(view.getText().toString());
    }
*/
    @Override
    public String toString() {
        return "KidosPartnersActivityDetailsBean{" +
                "activityId=" + activityId +
                ", description='" + description + '\'' +
                ", fees='" + fees + '\'' +
                ", age=" + age +
                ", batches=" + batches +
                '}';
    }
}
