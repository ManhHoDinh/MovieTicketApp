package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.movieticketapp.Model.Service;
import com.example.movieticketapp.Model.ServiceInTicket;
import com.example.movieticketapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UseServiceAdapter extends ArrayAdapter<Service> {
    private List<Service> listService;
    private TextView totalService;
    private List<ServiceInTicket> listServiceInTicket;
    private int total;
    public UseServiceAdapter(@NonNull Context context, int resource, List<Service> listService, TextView totalService, List<ServiceInTicket> listServiceInTicket) {
        super(context, resource, listService);
        this.totalService = totalService;
        this.listServiceInTicket = listServiceInTicket;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_service_item, null);
        ImageView image = itemView.findViewById(R.id.primaryImg);
        TextView name = itemView.findViewById(R.id.nameService);
        TextView price = itemView.findViewById(R.id.priceService);
        TextView detail = itemView.findViewById(R.id.detailService);
        ImageButton addBtn = itemView.findViewById(R.id.addBtn);
        ImageButton subBtn = itemView.findViewById(R.id.subBtn);
        TextView countTv = itemView.findViewById(R.id.countTv);

        Service service = getItem(position);
        name.setText(service.getName());
        price.setText(String.valueOf(service.getPrice()));
        detail.setText(service.getDetail());
        Picasso.get().load(service.getImage()).into(image);
        total = Integer.parseInt(totalService.getText().toString());
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(countTv.getText().toString());
                if(count < 8){
                    ++count;
                    total += Integer.parseInt(price.getText().toString());
                    totalService.setText(String.valueOf(total));
                    countTv.setText(String.valueOf(count));
                    subBtn.setBackgroundColor(Color.TRANSPARENT);
                    subBtn.setBackground(ContextCompat.getDrawable(subBtn.getContext(), R.drawable.adjust_able_btn));
                    subBtn.setImageResource(R.drawable.btn_sub_able);
                    if(count == 8){
                        addBtn.setBackgroundColor(Color.TRANSPARENT);
                        addBtn.setBackground(ContextCompat.getDrawable(addBtn.getContext(), R.drawable.adjust_unable_btn));
                        addBtn.setImageResource(R.drawable.btn_add_unable);
                    }
                    updateServiceInticket(service, count);


                }


            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(countTv.getText().toString());
                if(count > 0) {
                    --count;
                    total -= Integer.parseInt(price.getText().toString());
                    totalService.setText(String.valueOf(total));
                    addBtn.setBackgroundColor(Color.TRANSPARENT);
                    addBtn.setBackground(ContextCompat.getDrawable(addBtn.getContext(), R.drawable.adjust_able_btn));
                    addBtn.setImageResource(R.drawable.btn_add_able);
                    countTv.setText(String.valueOf(count));
                    if (count == 0) {
                        subBtn.setBackgroundColor(Color.TRANSPARENT);
                        subBtn.setBackground(ContextCompat.getDrawable(subBtn.getContext(), R.drawable.adjust_unable_btn));
                        subBtn.setImageResource(R.drawable.btn_sub_unable);
                    }
                    updateServiceInticket(service, count);

                }



            }
        });

        return itemView;

    }
    void updateServiceInticket(Service service, int count){
        boolean isExisted = false;



        ServiceInTicket deletedService = null;
        for(ServiceInTicket serviceInTicket : listServiceInTicket){
            if(serviceInTicket.getServiceID().equals(service.getID())){
                if(count == 0){
                    deletedService = serviceInTicket;

                }
                serviceInTicket.setCount(count);
                isExisted = true;
            }
        }
        if(deletedService != null){
            listServiceInTicket.remove(deletedService);
        }
        if(!isExisted){
            listServiceInTicket.add(new ServiceInTicket(count, service.getID()));
        }

    }
}