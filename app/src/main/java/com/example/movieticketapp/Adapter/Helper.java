package com.example.movieticketapp.Adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Helper {
    public static void getListViewSize(ListView myListView, Activity activity) {
        ListAdapter myListAdapter=myListView.getAdapter();
        if (myListAdapter==null) {
            //do nothing return null
            return;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        //set listAdapter in loop for getting final size
        int totalHeight=height - 40;
        for (int size=0; size < myListAdapter.getCount(); size++) {

            View listItem=myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        //setting listview item in adapter
        ViewGroup.LayoutParams params=myListView.getLayoutParams();
        params.height=totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);

    }


}
