
package com.creativeitem.academy.JSONSchemas;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetailsSchema {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Integer categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private Integer subCategoryId;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("requirements")
    @Expose
    private List<String> requirements = null;
    @SerializedName("includes")
    @Expose
    private List<String> includes = null;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount_flag")
    @Expose
    private String discountFlag;
    @SerializedName("discounted_price")
    @Expose
    private String discountedPrice;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
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
    private Integer isTopCourse;
    @SerializedName("is_admin")
    @Expose
    private Integer isAdmin;
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
    private Integer rating;
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
    @SerializedName("sections")
    @Expose
    private List<SectionSchema> sections = null;

    @SerializedName("is_wishlisted")
    @Expose
    private boolean isWishlisted = false;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
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

    public String getDiscountFlag() {
        return discountFlag;
    }

    public void setDiscountFlag(String discountFlag) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public Integer getIsTopCourse() {
        return isTopCourse;
    }

    public void setIsTopCourse(Integer isTopCourse) {
        this.isTopCourse = isTopCourse;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
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

    public List<SectionSchema> getSections() {
        return sections;
    }

    public void setSections(List<SectionSchema> sections) {
        this.sections = sections;
    }
    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public boolean isWishlisted() {
        return isWishlisted;
    }

    public void setWishlisted(boolean wishlisted) {
        isWishlisted = wishlisted;
    }
}
