package com.cinfy.jmvendor.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cinfy.jmvendor.R;
import com.cinfy.jmvendor.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        if (!((BaseActivity) getActivity()).launchFragment()) {
            ActivityCompat.requestPermissions(getActivity(), ((BaseActivity) getActivity()).permissions, ((BaseActivity) getActivity()).PERMISSION_ALL);
        }
        init(rootView);

        return rootView;
    }

    private void init(View rootView) {

        progressBar = rootView.findViewById(R.id.progressBar);

        rootView.findViewById(R.id.addItemLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemFragment addItemFragment = new AddItemFragment();
                ((DashBoardActivity) getActivity()).setNavigationFragment(addItemFragment, true, "AddItemFragment", "", false);
            }
        });

        rootView.findViewById(R.id.neworderlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewOrderFragment newOrderFragment = new NewOrderFragment();
                ((DashBoardActivity) getActivity()).setNavigationFragment(newOrderFragment, true, "NewOrderFragment", "", false);
            }
        });

        rootView.findViewById(R.id.pendingOrderLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();
                ((DashBoardActivity) getActivity()).setNavigationFragment(pendingOrderFragment, true, "PendingOrderFragment", "", false);

            }
        });

        rootView.findViewById(R.id.billGenerateLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BillGenerateFragment billGenerateFragment = new BillGenerateFragment();
                ((DashBoardActivity) getActivity()).setNavigationFragment(billGenerateFragment, true, "BillGenerateFragment", "", false);
            }
        });


    }

}

