package com.cinfy.jmvendor.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandDataList {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("sub_item_type_id")
    @Expose
    public int sub_item_type_id;

    @SerializedName("brand_name")
    @Expose
    public String brand_name;
}
