package com.cinfy.jmvendor.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BrandData {

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("data")
    @Expose
    public ArrayList<BrandDataList> data = new ArrayList<>();

}
