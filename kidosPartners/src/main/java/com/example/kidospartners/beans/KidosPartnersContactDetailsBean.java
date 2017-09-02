package com.example.kidospartners.beans;

/**
 * Created by Kartik on 02/09/17.
 */

public class KidosPartnersContactDetailsBean {
    private String activityId;
    private String phno;
    private String altphno;
    private String mobno;
    private String website;
    private String facebook;
    private String twitter;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getAltphno() {
        return altphno;
    }

    public void setAltphno(String altphno) {
        this.altphno = altphno;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public String toString() {
        return "KidosPartnersContactDetailsBean{" +
                "activityId='" + activityId + '\'' +
                ", phno='" + phno + '\'' +
                ", altphno='" + altphno + '\'' +
                ", mobno='" + mobno + '\'' +
                ", website='" + website + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                '}';
    }
}
