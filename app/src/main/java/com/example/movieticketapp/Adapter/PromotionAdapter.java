package com.example.movieticketapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Account.SignInActivity;
import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.NameTV.setText(Discounts.get(position).getName());
        holder.DescriptionTV.setText(Discounts.get(position).getDescription());
        holder.DiscountRateTV.setText("OFF "+new DecimalFormat("#.0#").format(Discounts.get(position).getDiscountRate())+ "%");
        //if user
        try{
            Log.d("account type", Users.currentUser.getAccountType());
            if(Users.currentUser!=null)
                if((!(Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                   holder.PromoMenu.setVisibility(View.INVISIBLE);
                }
        }
        catch (Exception e)
        {
            holder.PromoMenu.setVisibility(View.INVISIBLE);
        }
        finally {
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
                                {
                                    Intent i = new Intent(holder.itemView.getContext(), AddDiscount.class);
                                    i.putExtra(ExtraIntent.discount, Discounts.get(position));
                                    holder.itemView.getContext().startActivity(i);
                                }
                                return true;
                                case R.id.promo_delete:
                                {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext(), R.style.CustomAlertDialog);
                                    LayoutInflater factory = LayoutInflater.from(holder.itemView.getContext());
                                    final View deleteDialogView = factory.inflate(R.layout.yes_no_dialog, null);
                                    alertDialog.setView(deleteDialogView);
                                    AlertDialog OptionDialog = alertDialog.create();
                                    OptionDialog.show();
                                    TextView Cancel = deleteDialogView.findViewById(R.id.Cancel_Button);
                                    TextView Delete = deleteDialogView.findViewById(R.id.DeleteButton);
                                    Delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            CollectionReference PromoRef = db.collection(Discount.CollectionName);
                                            PromoRef.document(Discounts.get(position).getID()).delete();
                                            OptionDialog.dismiss();
                                        }
                                    });
                                    Cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            OptionDialog.dismiss();
                                        }
                                    });
                                }
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
}
