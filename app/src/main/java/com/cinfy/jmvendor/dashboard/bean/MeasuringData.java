package com.cinfy.jmvendor.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MeasuringData {

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("data")
    @Expose
    public ArrayList<MeasuringDataList> data = new ArrayList<>();
}
