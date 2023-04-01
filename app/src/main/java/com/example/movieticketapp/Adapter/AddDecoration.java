package com.example.movieticketapp.Adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddDecoration extends RecyclerView.ItemDecoration{
    private int spacing;
    public AddDecoration(int spacing){
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getAdapter() != null && parent.getChildLayoutPosition(view) == parent.getAdapter().getItemCount() - 1){
            outRect.right = spacing;
        }
    }
}