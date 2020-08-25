package com.cinfy.jmvendor.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.cinfy.jmvendor.R;
import com.cinfy.jmvendor.base.BaseActivity;
import com.cinfy.jmvendor.base.ProgressBarDialog;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class DashBoardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        launchFragment();
        setContentView(R.layout.frame);

        HomeFragment homeFragment = new HomeFragment();
        setNavigationFragment(homeFragment, true, "HomeFragment", "", false);

    }


    public void setNavigationFragment(Fragment fragment, boolean isBackStacked, String
            tag, String anim, boolean onResume) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (onResume) {
            transaction.replace(R.id.container, fragment, tag);
        } else {
            transaction.add(R.id.container, fragment, tag);
        }
        if (isBackStacked) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (!ProgressBarDialog.getInstance().getProgress()) {

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle(getString(R.string.app_name))
                        .setMessage(getString(R.string.close_application))
                        .setNegativeButton(getString(R.string.cancelText), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setCancelable(false);

                builder.create();
                builder.show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    }

}
