package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class TicketListAdapter extends ArrayAdapter<Ticket> {
    private Context context;
    private int resource;
    private List<Ticket> tickets;
    public TicketListAdapter(Context context, int resource, List<Ticket> tickets)
    {
        super(context, resource, tickets);
        this.resource = resource;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View converView, @Nullable ViewGroup parent)
    {
        View v = converView;
        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }
        Ticket ve = getItem(position);
        if (ve!=null) {
            TextView nameTextView = (TextView) v.findViewById(R.id.tvName);
            TextView timeTextView = (TextView) v.findViewById(R.id.tvTime);
            TextView studioTextView = (TextView) v.findViewById(R.id.tvStudio);
            RoundedImageView posterRImageView = (RoundedImageView) v.findViewById(R.id.ivPoster);

            if (nameTextView != null)
                nameTextView.setText(ve.getName());
            if (timeTextView != null)
                timeTextView.setText(ve.getTime());
            if (studioTextView != null)
                studioTextView.setText(ve.getStudio());
            if (posterRImageView != null)
                posterRImageView.setImageResource(ve.getPoster());
        }
        return v;
    }
}
