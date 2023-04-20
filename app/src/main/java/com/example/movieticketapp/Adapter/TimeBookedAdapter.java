package com.example.movieticketapp.Adapter;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.BookedActivity;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class TimeBookedAdapter extends RecyclerView.Adapter<TimeBookedAdapter.ViewHolder> {
    private List<String> listDate;
    private List<String> listTime;
    private String cinemaName;
    private Button preButton;
    private int checkedPosition = -1;
    private static String prevType = "";

    private static int prevPosition = 0;
    private View timeView;
    private static View prevView;
    public TimeBookedAdapter(List<String> listDate,@Nullable List<String> listTime, @Nullable String cinemaName, @Nullable View view) {
        this.listDate = listDate;
        this.listTime = listTime;
        this.cinemaName = cinemaName;
        this.timeView = view;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        Button dateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateBtn = (Button) itemView.findViewById(R.id.dateBtn);
            dateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(timeView != null){
                        if(listTime == null){

                            if(prevType != cinemaName){
                                if(prevView != null){
                                    Log.e("f","f");
                                    TextView tv = (TextView) prevView.findViewById(R.id.cinemaName);
                                    RecyclerView rv = (RecyclerView) prevView.findViewById(R.id.listTime);
                                    View v = rv.getLayoutManager().findViewByPosition(prevPosition);
                                    Log.e("f", String.valueOf(prevPosition) + " " + dateBtn.getText());
                                    Button btn  = v.findViewById(R.id.dateBtn);
                                    Log.e("f", String.valueOf(prevPosition) + " " + dateBtn.getText());
                                    btn.setBackgroundColor(Color.TRANSPARENT);
                                    btn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.bg_tabview_button));
                                    InforBooked.getInstance().timeBooked = dateBtn.getText().toString();
                                }
                                InforBooked.getInstance().nameCinema = cinemaName;


                            }
                        }
                        prevType = cinemaName;
                        prevView = timeView;
                        prevPosition = getAdapterPosition();
                    }

                    else InforBooked.getInstance().dateBooked = dateBtn.getText().toString();
                    dateBtn.setBackgroundColor(Color.TRANSPARENT);
                    dateBtn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.background_button));

                    if(checkedPosition!=getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }

                }
            });
        }

        void Binding(){
            if(checkedPosition != getAdapterPosition()){
                dateBtn.setBackgroundColor(Color.TRANSPARENT);
                dateBtn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.bg_tabview_button));
            }
            else {
                dateBtn.setBackgroundColor(Color.TRANSPARENT);
                dateBtn.setBackground(ContextCompat.getDrawable(dateBtn.getContext(), R.drawable.background_button));
            }


        }
    }
    @NonNull
    @Override
    public TimeBookedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_booked_item, parent, false);
        return new TimeBookedAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeBookedAdapter.ViewHolder holder, int position) {
        if(listTime != null){
            holder.dateBtn.setText(listDate.get(position) + "\n" + listTime.get(position));

        }
        else holder.dateBtn.setText(listDate.get(position));
        holder.Binding();

    }

    @Override
    public int getItemCount() {
        return listDate.size();
    }


}
