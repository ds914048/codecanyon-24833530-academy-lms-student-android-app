
package com.creativeitem.academy.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StripeKey {

    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("testmode")
    @Expose
    private String testmode;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("secret_key")
    @Expose
    private String secretKey;
    @SerializedName("public_live_key")
    @Expose
    private String publicLiveKey;
    @SerializedName("secret_live_key")
    @Expose
    private String secretLiveKey;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTestmode() {
        return testmode;
    }

    public void setTestmode(String testmode) {
        this.testmode = testmode;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPublicLiveKey() {
        return publicLiveKey;
    }

    public void setPublicLiveKey(String publicLiveKey) {
        this.publicLiveKey = publicLiveKey;
    }

    public String getSecretLiveKey() {
        return secretLiveKey;
    }

    public void setSecretLiveKey(String secretLiveKey) {
        this.secretLiveKey = secretLiveKey;
    }

}
