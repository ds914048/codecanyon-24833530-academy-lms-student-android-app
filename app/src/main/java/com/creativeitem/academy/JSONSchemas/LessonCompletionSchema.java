
package com.creativeitem.academy.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonCompletionSchema {

    @SerializedName("course_id")
    @Expose
    private int courseId;
    @SerializedName("number_of_lessons")
    @Expose
    private Integer numberOfLessons;
    @SerializedName("number_of_completed_lessons")
    @Expose
    private Integer numberOfCompletedLessons;
    @SerializedName("course_progress")
    @Expose
    private Integer courseProgress;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Integer getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(Integer numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public Integer getNumberOfCompletedLessons() {
        return numberOfCompletedLessons;
    }

    public void setNumberOfCompletedLessons(Integer numberOfCompletedLessons) {
        this.numberOfCompletedLessons = numberOfCompletedLessons;
    }

    public Integer getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(Integer courseProgress) {
        this.courseProgress = courseProgress;
    }

}
