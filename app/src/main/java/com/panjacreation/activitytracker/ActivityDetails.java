package com.panjacreation.activitytracker;

public class ActivityDetails {
    String activityName;
    String location;

    public ActivityDetails(String activityName, String location) {
        this.activityName = activityName;
        this.location = location;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
