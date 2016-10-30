package com.tangent.ikruger.tangentchallenge.DataEntities;

import java.util.Date;

/**
 * Created by Ivan Kruger on 2016-10-29.
 */

public class Task {
    private int id;
    private String title;
    private Date due_date;
    private float estimated_hours;
    private int project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public float getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(float estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }
}
