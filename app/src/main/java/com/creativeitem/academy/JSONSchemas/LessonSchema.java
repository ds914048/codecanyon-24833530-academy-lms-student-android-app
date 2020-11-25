
package com.creativeitem.academy.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonSchema {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("video_type")
    @Expose
    private String videoType;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("lesson_type")
    @Expose
    private String lessonType;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("attachment_type")
    @Expose
    private String attachmentType;
    @SerializedName("attachment_url")
    @Expose
    private String attachmentUrl;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("user_validity")
    @Expose
    private Boolean userValidity;
    @SerializedName("is_completed")
    @Expose
    private int isCompleted;

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getUserValidity() {
        return userValidity;
    }

    public void setUserValidity(Boolean userValidity) {
        this.userValidity = userValidity;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}
