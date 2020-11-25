package com.creativeitem.academy.Models;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String title;
    private String thumbnail;
    private String price;
    private String instructor;
    private float rating;
    private int totalNumberRating;
    private int numberOfEnrollment;
    private String shareableLink;
    private String courseOverviewProvider;
    private String courseOverviewUrl;

    public Course(int id, String title, String thumbnail, String price, String instructor, float rating, int totalNumberRating, int numberOfEnrollment, String shareableLink, String courseOverviewProvider, String courseOverviewUrl) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.instructor = instructor;
        this.rating = rating;
        this.totalNumberRating = totalNumberRating;
        this.numberOfEnrollment = numberOfEnrollment;
        this.shareableLink = shareableLink;
        this.courseOverviewProvider = courseOverviewProvider;
        this.courseOverviewUrl = courseOverviewUrl;
    }

    public Course(int id, String title, String thumbnail, String price, float rating) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.instructor = instructor;
        this.rating = rating;
        this.totalNumberRating = totalNumberRating;
        this.numberOfEnrollment = numberOfEnrollment;
        this.shareableLink = shareableLink;
        this.courseOverviewProvider = courseOverviewProvider;
        this.courseOverviewUrl = courseOverviewUrl;
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

    public String getPrice() {
        return price;
    }

    public String getInstructor() {
        return instructor;
    }

    public float getRating() {
        return rating;
    }

    public int getTotalNumberRating() {
        return totalNumberRating;
    }
    public int getNumberOfEnrollment() {
        return numberOfEnrollment;
    }

    public void setNumberOfEnrollment(int numberOfEnrollment) {
        this.numberOfEnrollment = numberOfEnrollment;
    }
    public String getShareableLink() {
        return shareableLink;
    }

    public void setShareableLink(String shareableLink) {
        this.shareableLink = shareableLink;
    }

    public String getCourseOverviewProvider() {
        return courseOverviewProvider;
    }

    public void setCourseOverviewProvider(String courseOverviewProvider) {
        this.courseOverviewProvider = courseOverviewProvider;
    }

    public String getCourseOverviewUrl() {
        return courseOverviewUrl;
    }

    public void setCourseOverviewUrl(String courseOverviewUrl) {
        this.courseOverviewUrl = courseOverviewUrl;
    }
}
