package com.cinfy.jmvendor.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuccessBean {
    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("data")
    @Expose
    public String data;

    @SerializedName("error")
    @Expose
    public Object error;




}
