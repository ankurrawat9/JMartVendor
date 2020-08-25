package com.cinfy.jmvendor.dashboard.manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cinfy.jmvendor.base.MyApplication;
import com.cinfy.jmvendor.base.NetworkEventListener;
import com.cinfy.jmvendor.base.NetworkUrl;
import com.cinfy.jmvendor.base.Utils;
import com.cinfy.jmvendor.dashboard.bean.FetchRequestTypeData;
import com.cinfy.jmvendor.dashboard.bean.SuccessBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashBoardManager {

    public static void getTypeData(final NetworkEventListener networkEventListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NetworkUrl.fetchRequestType , new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject customJsonObject) {

                        Gson gson = new Gson();
                        FetchRequestTypeData bean = gson.fromJson(customJsonObject.toString(), FetchRequestTypeData.class);
                        networkEventListener.OnSuccess(bean);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                networkEventListener.OnError(volleyError);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
        Utils.setConnectionTimeOut(jsonObjectRequest);

    }

    public static void createItem(Map data, final NetworkEventListener networkEventListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetworkUrl.CREATEITEM, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject customJsonObject) {

                        Gson gson = new Gson();
                        SuccessBean bean = gson.fromJson(customJsonObject.toString(), SuccessBean.class);

                        networkEventListener.OnSuccess(bean);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                networkEventListener.OnError(volleyError);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
        Utils.setConnectionTimeOut(jsonObjectRequest);

    }


}
