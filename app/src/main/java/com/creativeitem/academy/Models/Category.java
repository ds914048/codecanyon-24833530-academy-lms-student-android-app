package com.creativeitem.academy.Models;
import java.util.ArrayList;

public class Category {
    private int id;
    private String title;
    private String thumbnail;
    private int numberOfCourses;

    public Category(int id, String title, String thumbnail, int numberOfCourses) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.numberOfCourses = numberOfCourses;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    @Override
    public String toString() {
        return title;
    }
}
