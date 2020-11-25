
package com.creativeitem.academy.JSONSchemas;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseSchema {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("outcomes")
    @Expose
    private List<String> outcomes = null;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("requirements")
    @Expose
    private List<String> requirements = null;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount_flag")
    @Expose
    private Object discountFlag;
    @SerializedName("discounted_price")
    @Expose
    private String discountedPrice;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("video_url")
    @Expose
    private String courseOverviewUrl;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;
    @SerializedName("visibility")
    @Expose
    private Object visibility;
    @SerializedName("is_top_course")
    @Expose
    private String isTopCourse;
    @SerializedName("is_admin")
    @Expose
    private String isAdmin;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("course_overview_provider")
    @Expose
    private String courseOverviewProvider;
    @SerializedName("meta_keywords")
    @Expose
    private String metaKeywords;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;
    @SerializedName("is_free_course")
    @Expose
    private Object isFreeCourse;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("number_of_ratings")
    @Expose
    private Integer numberOfRatings;
    @SerializedName("instructor_name")
    @Expose
    private String instructorName;
    @SerializedName("total_enrollment")
    @Expose
    private Integer totalEnrollment;
    @SerializedName("shareable_link")
    @Expose
    private String shareableLink;
    @SerializedName("completion")
    @Expose
    private int courseCompletion;

    @SerializedName("total_number_of_lessons")
    @Expose
    private int totalNumberOfLessons;

    @SerializedName("total_number_of_completed_lessons")
    @Expose
    private int totalNumberOfCompletedLessons;

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<String> outcomes) {
        this.outcomes = outcomes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getDiscountFlag() {
        return discountFlag;
    }

    public void setDiscountFlag(Object discountFlag) {
        this.discountFlag = discountFlag;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCourseOverviewUrl() {
        return courseOverviewUrl;
    }

    public void setCourseOverviewUrl(String courseOverviewUrl) {
        this.courseOverviewUrl = courseOverviewUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Object getVisibility() {
        return visibility;
    }

    public void setVisibility(Object visibility) {
        this.visibility = visibility;
    }

    public String getIsTopCourse() {
        return isTopCourse;
    }

    public void setIsTopCourse(String isTopCourse) {
        this.isTopCourse = isTopCourse;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseOverviewProvider() {
        return courseOverviewProvider;
    }

    public void setCourseOverviewProvider(String courseOverviewProvider) {
        this.courseOverviewProvider = courseOverviewProvider;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Object getIsFreeCourse() {
        return isFreeCourse;
    }

    public void setIsFreeCourse(Object isFreeCourse) {
        this.isFreeCourse = isFreeCourse;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }


    public Integer getTotalEnrollment() {
        return totalEnrollment;
    }

    public void setTotalEnrollment(Integer totalEnrollment) {
        this.totalEnrollment = totalEnrollment;
    }

    public String getShareableLink() {
        return shareableLink;
    }

    public void setShareableLink(String shareableLink) {
        this.shareableLink = shareableLink;
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
}
