
package com.creativeitem.academy.JSONSchemas;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemSettings {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("system_name")
    @Expose
    private String systemName;
    @SerializedName("system_title")
    @Expose
    private String systemTitle;
    @SerializedName("system_email")
    @Expose
    private String systemEmail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("purchase_code")
    @Expose
    private String purchaseCode;
    @SerializedName("paypal")
    @Expose
    private List<Paypal> paypal = null;
    @SerializedName("stripe_keys")
    @Expose
    private List<StripeKey> stripeKeys = null;
    @SerializedName("youtube_api_key")
    @Expose
    private String youtubeApiKey;
    @SerializedName("vimeo_api_key")
    @Expose
    private String vimeoApiKey;
    @SerializedName("slogan")
    @Expose
    private String slogan;
    @SerializedName("text_align")
    @Expose
    private Object textAlign;
    @SerializedName("allow_instructor")
    @Expose
    private String allowInstructor;
    @SerializedName("instructor_revenue")
    @Expose
    private String instructorRevenue;
    @SerializedName("system_currency")
    @Expose
    private String systemCurrency;
    @SerializedName("paypal_currency")
    @Expose
    private String paypalCurrency;
    @SerializedName("stripe_currency")
    @Expose
    private String stripeCurrency;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("currency_position")
    @Expose
    private String currencyPosition;
    @SerializedName("website_description")
    @Expose
    private String websiteDescription;
    @SerializedName("website_keywords")
    @Expose
    private String websiteKeywords;
    @SerializedName("footer_text")
    @Expose
    private Object footerText;
    @SerializedName("footer_link")
    @Expose
    private String footerLink;
    @SerializedName("protocol")
    @Expose
    private String protocol;
    @SerializedName("smtp_host")
    @Expose
    private String smtpHost;
    @SerializedName("smtp_port")
    @Expose
    private String smtpPort;
    @SerializedName("smtp_user")
    @Expose
    private String smtpUser;
    @SerializedName("smtp_pass")
    @Expose
    private String smtpPass;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("student_email_verification")
    @Expose
    private String studentEmailVerification;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("favicon")
    @Expose
    private String favicon;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public List<Paypal> getPaypal() {
        return paypal;
    }

    public void setPaypal(List<Paypal> paypal) {
        this.paypal = paypal;
    }

    public List<StripeKey> getStripeKeys() {
        return stripeKeys;
    }

    public void setStripeKeys(List<StripeKey> stripeKeys) {
        this.stripeKeys = stripeKeys;
    }

    public String getYoutubeApiKey() {
        return youtubeApiKey;
    }

    public void setYoutubeApiKey(String youtubeApiKey) {
        this.youtubeApiKey = youtubeApiKey;
    }

    public String getVimeoApiKey() {
        return vimeoApiKey;
    }

    public void setVimeoApiKey(String vimeoApiKey) {
        this.vimeoApiKey = vimeoApiKey;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Object getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(Object textAlign) {
        this.textAlign = textAlign;
    }

    public String getAllowInstructor() {
        return allowInstructor;
    }

    public void setAllowInstructor(String allowInstructor) {
        this.allowInstructor = allowInstructor;
    }

    public String getInstructorRevenue() {
        return instructorRevenue;
    }

    public void setInstructorRevenue(String instructorRevenue) {
        this.instructorRevenue = instructorRevenue;
    }

    public String getSystemCurrency() {
        return systemCurrency;
    }

    public void setSystemCurrency(String systemCurrency) {
        this.systemCurrency = systemCurrency;
    }

    public String getPaypalCurrency() {
        return paypalCurrency;
    }

    public void setPaypalCurrency(String paypalCurrency) {
        this.paypalCurrency = paypalCurrency;
    }

    public String getStripeCurrency() {
        return stripeCurrency;
    }

    public void setStripeCurrency(String stripeCurrency) {
        this.stripeCurrency = stripeCurrency;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCurrencyPosition() {
        return currencyPosition;
    }

    public void setCurrencyPosition(String currencyPosition) {
        this.currencyPosition = currencyPosition;
    }

    public String getWebsiteDescription() {
        return websiteDescription;
    }

    public void setWebsiteDescription(String websiteDescription) {
        this.websiteDescription = websiteDescription;
    }

    public String getWebsiteKeywords() {
        return websiteKeywords;
    }

    public void setWebsiteKeywords(String websiteKeywords) {
        this.websiteKeywords = websiteKeywords;
    }

    public Object getFooterText() {
        return footerText;
    }

    public void setFooterText(Object footerText) {
        this.footerText = footerText;
    }

    public String getFooterLink() {
        return footerLink;
    }

    public void setFooterLink(String footerLink) {
        this.footerLink = footerLink;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPass() {
        return smtpPass;
    }

    public void setSmtpPass(String smtpPass) {
        this.smtpPass = smtpPass;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStudentEmailVerification() {
        return studentEmailVerification;
    }

    public void setStudentEmailVerification(String studentEmailVerification) {
        this.studentEmailVerification = studentEmailVerification;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }
}
