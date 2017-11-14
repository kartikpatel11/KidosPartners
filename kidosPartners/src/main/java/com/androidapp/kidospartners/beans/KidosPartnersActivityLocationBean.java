package com.androidapp.kidospartners.beans;


import java.util.Arrays;

public class KidosPartnersActivityLocationBean {

    private int activityId;

    //private String type;

    private double[] coordinates;

    public KidosPartnersActivityLocationBean(int _activityId, double[] locArr) {
        activityId=+_activityId;
        coordinates=locArr;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }


    public double[] getCoordinates ()
    {
        return coordinates;
    }

    public void setCoordinates (double[] coordinates)
    {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "KidosPartnersActivityLocationBean{" +
                "activityId=" + activityId +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
