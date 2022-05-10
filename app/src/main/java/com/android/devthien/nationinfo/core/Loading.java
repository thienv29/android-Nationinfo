package com.android.devthien.nationinfo.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.android.devthien.nationinfo.R;

public class Loading {
    private Activity activity;
    private AlertDialog dialog;
    public Loading(Activity activity){
        this.activity = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
    }
    public void startLoading(){

        dialog.show();
    }
    public void dismissDialog(){
        dialog.dismiss();
    }
}
