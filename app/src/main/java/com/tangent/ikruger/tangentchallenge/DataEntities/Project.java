package com.tangent.ikruger.tangentchallenge.DataEntities;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ivan Kruger on 2016-10-29.
 */

public class Project {
    int pk;
    String title;
    String description;
    String start_date;
    String end_date;
    boolean is_billable;
    boolean is_active;
    Task[] tasks;
    Resource[] resources;

    public void addTask(Task T){
        List<Task> temp = Arrays.asList(this.tasks);
        temp.add(T);
        this.tasks = temp.toArray(this.tasks);
    }

    public boolean removeTask(Task T){
        List<Task> temp = Arrays.asList(this.tasks);
        boolean result = temp.remove(T);

        this.tasks = temp.toArray(this.tasks);
        return result;
    }

    public List<Task> getTasks(){
        return Arrays.asList(this.tasks);
    }

    public Resource[] getResources() {
        return resources;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {

        if (end_date == null)
            return "";

        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public boolean is_billable() {
        return is_billable;
    }

    public void setIs_billable(boolean is_billable) {
        this.is_billable = is_billable;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public static Project fromJson(String s){
        Gson gson = new Gson();
        return gson.fromJson(s,Project.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this,Project.class);
    }
}
