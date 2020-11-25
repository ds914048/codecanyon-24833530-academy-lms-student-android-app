
package com.creativeitem.academy.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paypal {

    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("sandbox_client_id")
    @Expose
    private String sandboxClientId;
    @SerializedName("production_client_id")
    @Expose
    private String productionClientId;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSandboxClientId() {
        return sandboxClientId;
    }

    public void setSandboxClientId(String sandboxClientId) {
        this.sandboxClientId = sandboxClientId;
    }

    public String getProductionClientId() {
        return productionClientId;
    }

    public void setProductionClientId(String productionClientId) {
        this.productionClientId = productionClientId;
    }

}
