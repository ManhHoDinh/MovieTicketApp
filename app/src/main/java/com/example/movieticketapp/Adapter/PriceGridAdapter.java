package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.TopUpActivity;
import com.example.movieticketapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class PriceGridAdapter extends BaseAdapter{
    private List<String> listPrice;
    private Context context;
    private TextInputEditText textInputEditText;
    private static ViewHolder prevView;


    public PriceGridAdapter(Context context,  List<String> listPrice, TextInputEditText textInputEditText) {
        this.context = context;
        this.listPrice = listPrice;
        this.textInputEditText = textInputEditText;

    }

    @Override
    public int getCount() {
        return listPrice.size();
    }

    @Override
    public Object getItem(int position) {
        return listPrice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_price_item, null);
            holder = new ViewHolder();
            holder.priceBtn = (Button) convertView.findViewById(R.id.priceBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String price = this.listPrice.get(position);
        holder.priceBtn.setText(price);
        holder.priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prevView == null){
                    holder.priceBtn.setSelected(true);
                    textInputEditText.setText( holder.priceBtn.getText());
                }
                else{
                    if(prevView.priceBtn.getText() != holder.priceBtn.getText()){
                        holder.priceBtn.setSelected(true);
                        textInputEditText.setText( holder.priceBtn.getText());
                        prevView.priceBtn.setSelected(false);
                    }
                    else {
                        holder.priceBtn.setSelected(false);
                        textInputEditText.setText("");

                    }
                }
                prevView = holder;
            }
        });

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).


    static class ViewHolder {

        Button priceBtn;

    }
//        List<String> listPrice;
//   private int selectedPosition = -1;
//    public PriceGridAdapter(@NonNull Context context, int resource, List<String> listPrice) {
//        super(context, resource, listPrice);
//    }
//    private void setSelectedPosition(int position)
//    {
//        selectedPosition=position;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View itemView;
//        itemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_price_item, null);
//        Button priceBtn = (Button) itemView.findViewById(R.id.priceBtn);
//        priceBtn.setText(getItem(position));
//        priceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                setSelectedPosition(position);
////                if(position==selectedPosition)
////                {
////                    priceBtn.setBackgroundColor(Color.RED);
////                }
////                else priceBtn.setBackgroundColor(Color.GREEN);
//                //PriceGridAdapter.this.notifyDataSetChanged();
//
////                    for(int i = 0; i < getCount(); i++){
////                        if(i!=position){
////                            priceBtn.setBackground(ContextCompat.getDrawable( priceBtn.getContext(),R.drawable.bg_tabview_button));
////                        }
////                        else  priceBtn.setBackground(ContextCompat.getDrawable( priceBtn.getContext(),R.drawable.background_button));
////
////                    }
//
//
//
//            }
//        });
//        return itemView;
//
//    }
    
}
