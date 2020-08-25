package com.cinfy.jmvendor.base;

import com.android.volley.VolleyError;

public  interface NetworkEventListener {
    public void OnSuccess(Object object);
    public void OnError(VolleyError exception);
}