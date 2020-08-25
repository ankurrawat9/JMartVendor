package com.cinfy.jmvendor.dashboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cinfy.jmvendor.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BillGenerateFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.bill_generate, container, false);

        init(rootView);



        return rootView;

    }

    private void init(View rootView) {



    }



}



















