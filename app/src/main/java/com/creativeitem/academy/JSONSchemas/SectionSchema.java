
package com.creativeitem.academy.JSONSchemas;

import java.util.List;

import com.creativeitem.academy.Models.Lesson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionSchema {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("lessons")
    @Expose
    private List<LessonSchema> lessons = null;
    @SerializedName("completed_lesson_number")
    @Expose
    private Integer completedLessonNumber;
    @SerializedName("user_validity")
    @Expose
    private Boolean userValidity;
    @SerializedName("total_duration")
    @Expose
    private String totalDuration;
    @SerializedName("lesson_counter_starts")
    @Expose
    private Integer lessonCounterStarts;
    @SerializedName("lesson_counter_ends")
    @Expose
    private Integer lessonCounterEnds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<LessonSchema> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonSchema> lessons) {
        this.lessons = lessons;
    }

    public Integer getCompletedLessonNumber() {
        return completedLessonNumber;
    }

    public void setCompletedLessonNumber(Integer completedLessonNumber) {
        this.completedLessonNumber = completedLessonNumber;
    }

    public Boolean getUserValidity() {
        return userValidity;
    }

    public void setUserValidity(Boolean userValidity) {
        this.userValidity = userValidity;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getLessonCounterStarts() {
        return lessonCounterStarts;
    }

    public void setLessonCounterStarts(Integer lessonCounterStarts) {
        this.lessonCounterStarts = lessonCounterStarts;
    }

    public Integer getLessonCounterEnds() {
        return lessonCounterEnds;
    }

    public void setLessonCounterEnds(Integer lessonCounterEnds) {
        this.lessonCounterEnds = lessonCounterEnds;
    }
}
