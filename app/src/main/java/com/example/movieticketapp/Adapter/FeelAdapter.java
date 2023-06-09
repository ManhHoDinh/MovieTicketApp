package com.example.movieticketapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class FeelAdapter extends RecyclerView.Adapter<FeelAdapter.FeelHolder> {
    private List<String> listFeel = new ArrayList<>();
    private List<String> listEmote = new ArrayList<>();

    public FeelAdapter(List<String> listFeel, @Nullable List<String> listEmote) {
        this.listFeel = listFeel;
        this.listEmote = listEmote;
    }

    @NonNull
    @Override
    public FeelAdapter.FeelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feel_item, parent, false);
        return new FeelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeelAdapter.FeelHolder holder, int position) {
        holder.feelBtn.setText(listFeel.get(position));
        holder.feelBtn.setBackgroundColor(Color.TRANSPARENT);
        holder.feelBtn.setBackground(ContextCompat.getDrawable(holder.feelBtn.getContext(), R.drawable.bg_tabview_button));
        if(listEmote == null){
            holder.feelBtn.setTextSize(10);
        }
    }

    @Override
    public int getItemCount() {
        return listFeel.size();
    }
    public class FeelHolder extends RecyclerView.ViewHolder {
        TextView feelBtn;

        public FeelHolder(@NonNull View itemView) {
            super(itemView);
            feelBtn = itemView.findViewById(R.id.feelBtn);
            if(listEmote != null){
                feelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectedIndex = -1;
                        for(int i = 0; i < listEmote.size(); i ++){
                            if(listEmote.get(i).equals(feelBtn.getText().toString())){
                                selectedIndex  = i;
                            }
                        }
                        if(selectedIndex == -1){
                            feelBtn.setBackgroundColor(Color.TRANSPARENT);
                            feelBtn.setBackground(ContextCompat.getDrawable(feelBtn.getContext(), R.drawable.background_button));
                            listEmote.add(feelBtn.getText().toString());
                        }
                        else {
                            feelBtn.setBackgroundColor(Color.TRANSPARENT);
                            feelBtn.setBackground(ContextCompat.getDrawable(feelBtn.getContext(), R.drawable.bg_tabview_button));
                            listEmote.remove(selectedIndex);
                        }
                    }
                });
            }

        }
    }

}
