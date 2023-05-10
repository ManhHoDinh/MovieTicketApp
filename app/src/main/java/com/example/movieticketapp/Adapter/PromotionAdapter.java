package com.example.movieticketapp.Adapter;

import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Account.SignInActivity;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.ViewHolder holder, int position) {
        holder.NameTV.setText(Discounts.get(position).getName());
        holder.DescriptionTV.setText(Discounts.get(position).getDescription());
        holder.DiscountRateTV.setText("OFF "+new DecimalFormat("#.0#").format(Discounts.get(position).getDiscountRate())+ "%");

        holder.PromoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.PromoMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.promo_menu);
                SpannableString s = new SpannableString("Edit");
                s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
                popup.getMenu().getItem(0).setTitle(s);
                SpannableString delete = new SpannableString("Delete");
                delete.setSpan(new ForegroundColorSpan(Color.RED), 0, delete.length(), 0);
                popup.getMenu().getItem(1).setTitle(delete);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.promo_edit:
                                //handle menu1 click
                                Toast.makeText(view.getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.promo_delete:
                                //handle menu2 click
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }
    List<Discount> Discounts;

    public PromotionAdapter(List<Discount> Discounts) {
        this.Discounts = Discounts;
    }

    @Override
    public int getItemCount() {
        return Discounts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView NameTV;
        TextView DescriptionTV;
        TextView DiscountRateTV;
        ImageView PromoMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // imageView = itemView.findViewById(R.id.posterItem);
            NameTV = itemView.findViewById(R.id.PromoName);
            DescriptionTV = itemView.findViewById(R.id.PromoDescription);
            DiscountRateTV = itemView.findViewById(R.id.PromoRate);
            PromoMenu=itemView.findViewById(R.id.PromoMenu);
        }
    }
    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item, parent, false);
        return new PromotionAdapter.ViewHolder(itemView);
    }
    public static Rect locateView(View v)
    {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try
        {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe)
        {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

}
