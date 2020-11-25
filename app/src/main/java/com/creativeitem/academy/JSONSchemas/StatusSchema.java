
package com.creativeitem.academy.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusSchema {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
