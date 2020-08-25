package com.cinfy.jmvendor.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchSubTypeList {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("request_type_id")
    @Expose
    public int request_type_id;

    @SerializedName("name")
    @Expose
    public String name;

}
