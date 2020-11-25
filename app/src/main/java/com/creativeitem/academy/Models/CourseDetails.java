package com.creativeitem.academy.Models;

import java.util.List;

public class CourseDetails {
    private int courseId;
    private List<String> courseIncludes;
    private List<String> courseRequirements;
    private List<String> courseOutcomes;
    private boolean isWishlisted;

    public CourseDetails(int courseId, List<String> courseIncludes, List<String> courseOutcomes, List<String> courseRequirements, boolean isWishlisted) {
        this.courseId = courseId;
        this.courseIncludes = courseIncludes;
        this.courseRequirements = courseRequirements;
        this.courseOutcomes = courseOutcomes;
        this.isWishlisted = isWishlisted;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<String> getCourseIncludes() {
        return courseIncludes;
    }

    public void setCourseIncludes(List<String> courseIncludes) {
        this.courseIncludes = courseIncludes;
    }

    public List<String> getCourseRequirements() {
        return courseRequirements;
    }

    public void setCourseRequirements(List<String> courseRequirements) {
        this.courseRequirements = courseRequirements;
    }

    public List<String> getCourseOutcomes() {
        return courseOutcomes;
    }

    public void setCourseOutcomes(List<String> courseOutcomes) {
        this.courseOutcomes = courseOutcomes;
    }

    public boolean isWishlisted() {
        return isWishlisted;
    }

    public void setWishlisted(boolean wishlisted) {
        isWishlisted = wishlisted;
    }
}
