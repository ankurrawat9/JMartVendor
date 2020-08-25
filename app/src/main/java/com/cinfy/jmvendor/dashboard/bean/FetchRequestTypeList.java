package com.cinfy.jmvendor.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchRequestTypeList {

    @SerializedName("request_type_master_id")
    @Expose
    public int requestTypeMasterId;

    @SerializedName("request_type_master_name")
    @Expose
    public String requestTypeMasterName;

}
