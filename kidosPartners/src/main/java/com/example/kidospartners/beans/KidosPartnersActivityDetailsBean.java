package com.example.kidospartners.beans;

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
