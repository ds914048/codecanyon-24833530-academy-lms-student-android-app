package com.creativeitem.academy.Models;

public class Lesson {
    private int id;
    private String title;
    private String duration;
    private int courseId;
    private int sectionId;
    private String videoUrl;
    private String lessonType;
    private String summary;
    private String attachmentType;
    private String attachment;
    private String attachmentUrl;

    public Lesson(int id, String title, String duration, int courseId, int sectionId, String videoUrl, String lessonType, String summary, String attachmentType, String attachment, String attachmentUrl) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.videoUrl = videoUrl;
        this.lessonType = lessonType;
        this.summary = summary;
        this.attachmentType = attachmentType;
        this.attachment = attachment;
        this.attachmentUrl = attachmentUrl;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}
