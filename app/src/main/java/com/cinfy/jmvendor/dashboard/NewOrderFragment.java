package com.cinfy.jmvendor.dashboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cinfy.jmvendor.R;
import com.cinfy.jmvendor.dashboard.bean.NewOrderAdapter;
import com.cinfy.jmvendor.dashboard.bean.NewOrderData;

import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NewOrderFragment extends Fragment {

    RecyclerView recycler1;
    NewOrderAdapter adapter;

    List<NewOrderData> dataList;
    RecyclerView.LayoutManager layoutManager;
    String name,orderid,address,itemdata,amount,status;

    private static final String url= " Network url here ";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.new_orders, container, false);

        init(rootView);



        return rootView;

    }

    private void init(View rootView) {
        recycler1 = rootView.findViewById(R.id.recyclerview);
        loadData();

    }


private void loadData(){


    StringRequest stringRequest;
    stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {



                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject pro = array.getJSONObject(i);

                   NewOrderData data=new NewOrderData();

                    Gson gson = new Gson();
                    NewOrderData bean = gson.fromJson(pro.toString(), NewOrderData.class);


                    name = pro.getString("name");
                    orderid = pro.getString("orderid");
                    address = pro.getString("address");
                    itemdata = pro.getString("itemdata");
                    amount = pro.getString("amount");



                    data.setName(name);
                    data.setOrderid(orderid);
                    data.setAddress(address);
                    data.setItemdetail(itemdata);
                    data.setAmount(amount);


                    dataList.add(data);
                }

                adapter=new NewOrderAdapter(dataList,getContext());
                recycler1.setAdapter(adapter);



            } catch (JSONException e) {

                e.printStackTrace();

                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                //error message
            }
        }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    Volley.newRequestQueue(getContext()).add(stringRequest);


}
    }

















