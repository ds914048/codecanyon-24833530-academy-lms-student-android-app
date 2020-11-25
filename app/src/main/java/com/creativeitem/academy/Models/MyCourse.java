package com.creativeitem.academy.Models;

import java.io.Serializable;

public class MyCourse implements Serializable {
    private int id;
    private String title;
    private String thumbnail;
    private String price;
    private String instructor;
    private float rating;
    private int totalNumberRating;
    private int numberOfEnrollment;
    private int courseCompletion;
    private int totalNumberOfLessons;
    private int totalNumberOfCompletedLessons;
    private String shareableLink;
    private String courseOverviewProvider;
    private String courseOverviewUrl;

    public MyCourse(int id, String title, String thumbnail, String price, String instructor, float rating, int totalNumberRating, int numberOfEnrollment, int courseCompletion, int totalNumberOfLessons, int totalNumberOfCompletedLessons, String shareableLink, String courseOverviewProvider, String courseOverviewUrl) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.instructor = instructor;
        this.rating = rating;
        this.totalNumberRating = totalNumberRating;
        this.numberOfEnrollment = numberOfEnrollment;
        this.courseCompletion = courseCompletion;
        this.totalNumberOfLessons = totalNumberOfLessons;
        this.totalNumberOfCompletedLessons = totalNumberOfCompletedLessons;
        this.shareableLink = shareableLink;
        this.courseOverviewProvider = courseOverviewProvider;
        this.courseOverviewUrl = courseOverviewUrl;
    }

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTotalNumberRating() {
        return totalNumberRating;
    }

    public void setTotalNumberRating(int totalNumberRating) {
        this.totalNumberRating = totalNumberRating;
    }

    public int getNumberOfEnrollment() {
        return numberOfEnrollment;
    }

    public void setNumberOfEnrollment(int numberOfEnrollment) {
        this.numberOfEnrollment = numberOfEnrollment;
    }

    public int getCourseCompletion() {
        return courseCompletion;
    }

    public void setCourseCompletion(int courseCompletion) {
        this.courseCompletion = courseCompletion;
    }

    public int getTotalNumberOfLessons() {
        return totalNumberOfLessons;
    }

    public void setTotalNumberOfLessons(int totalNumberOfLessons) {
        this.totalNumberOfLessons = totalNumberOfLessons;
    }

    public int getTotalNumberOfCompletedLessons() {
        return totalNumberOfCompletedLessons;
    }

    public void setTotalNumberOfCompletedLessons(int totalNumberOfCompletedLessons) {
        this.totalNumberOfCompletedLessons = totalNumberOfCompletedLessons;
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
