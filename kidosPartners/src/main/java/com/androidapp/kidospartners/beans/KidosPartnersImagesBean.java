package com.androidapp.kidospartners.beans;

import java.util.ArrayList;

/**
 * Created by Kartik on 20/09/17.
 */

public class KidosPartnersImagesBean {
    private int activityId;
    private ArrayList<KidosPartnersImageDetailsBean> images;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public ArrayList<KidosPartnersImageDetailsBean> getImages() {
        return images;
    }

    public void setImages(ArrayList<KidosPartnersImageDetailsBean> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "KidosPartnersImagesBean{" +
                "activityId=" + activityId +
                ", images=" + images +
                '}';
    }
}
