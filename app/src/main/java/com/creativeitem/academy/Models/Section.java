package com.creativeitem.academy.Models;
import com.creativeitem.academy.JSONSchemas.LessonSchema;

import java.util.List;

public class Section {
    private int id;
    private int numberOfCompletedLessons;
    private String title;
    private List<LessonSchema> mLesson;
    private String totalDuration;
    private int lessonCounterStarts;
    private int lessonCounterEnds;
    public Section(int id, String title, List<LessonSchema> lesson, int numberOfCompletedLessons, String totalDuration, int lessonCounterStarts, int lessonCounterEnds) {
        this.id = id;
        this.title = title;
        this.mLesson = lesson;
        this.numberOfCompletedLessons = numberOfCompletedLessons;
        this.totalDuration = totalDuration;
        this.lessonCounterStarts = lessonCounterStarts;
        this.lessonCounterEnds = lessonCounterEnds;
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

    public List<LessonSchema> getmLesson() {
        return mLesson;
    }

    public void setmLesson(List<LessonSchema> mLesson) {
        this.mLesson = mLesson;
    }

    public int getNumberOfCompletedLessons() {
        return numberOfCompletedLessons;
    }

    public void setNumberOfCompletedLessons(int numberOfCompletedLessons) {
        this.numberOfCompletedLessons = numberOfCompletedLessons;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getLessonCounterStarts() {
        return lessonCounterStarts;
    }

    public void setLessonCounterStarts(int lessonCounterStarts) {
        this.lessonCounterStarts = lessonCounterStarts;
    }

    public int getLessonCounterEnds() {
        return lessonCounterEnds;
    }

    public void setLessonCounterEnds(int lessonCounterEnds) {
        this.lessonCounterEnds = lessonCounterEnds;
    }
}
