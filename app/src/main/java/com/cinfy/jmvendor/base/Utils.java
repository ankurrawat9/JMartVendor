package com.cinfy.jmvendor.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cinfy.jmvendor.dashboard.DashBoardActivity;
import com.cinfy.jmvendor.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static void saveSharedSetting(Context context, String settingName, String settingValue) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(settingName, settingValue);
            editor.apply();

        } catch (Exception e) {
            Log.e("Exception : ", e.getMessage());
        }
    }

    public static void saveSharedSetting(Context context, String settingName, int settingValue) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(settingName, settingValue);
            editor.apply();

        } catch (Exception e) {
            Log.e("Exception : ", e.getMessage());
        }
    }

    public static void saveSharedSettingBoolean(Context context, String settingName, boolean settingValue) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(settingName, settingValue);
            editor.apply();

        } catch (Exception e) {
            Log.e("Exception : ", e.getMessage());
        }
    }

    public static boolean readSharedSettingBoolean(Context context, String settingName, boolean defaultValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(settingName, defaultValue);
    }

    public static String readSharedSetting(Context context, String settingName, String defaultValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static int readSharedSetting(Context context, String settingName, int defaultValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getInt(settingName, defaultValue);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(cs.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isOnline() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public static String getDate(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public static void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void hideKeyboardTextView(Activity activity, TextView textView) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            editText.requestFocus();
        }
    }

    public static void setConnectionTimeOut(JsonObjectRequest jsonObjectRequest) {
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static void setConnectionTimeOut(StringRequest jsonObjectRequest) {
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static Dialog getDialog(String title, final Context context, String msg, final int action) {

        if (action == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Uri marketUri = Uri.parse("market://details?id=" + context.getPackageName());
                                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                                context.startActivity(marketIntent);
                            } catch (Exception e) {
                                Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());
                                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                                context.startActivity(marketIntent);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            return builder.create();

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (action == 1) {
                            ((DashBoardActivity) context).onBackPressed();
                        } else if (action == 3) {
                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
                        } else if (action == 10) {
//                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
                        } else if (action == 15) {
//                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
                        } else if (action == 11) {
                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
                            ((DashBoardActivity) context).onBackPressed();
//                            ((DashBoardActivity) context).onBackPressed();
                        }
                    }
                });
        return builder.create();
    }
}
