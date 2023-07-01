package com.example.movieticketapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.List;

public class DiscountAdapter extends ArrayAdapter<Discount> {
    private List<Discount> Discounts;
    public DiscountAdapter(@NonNull Context context, int resource, List<Discount> Discounts) {
        super(context, resource, Discounts);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item, null);
        TextView NameTv;
        TextView DescriptionTV;
        TextView DiscountRateTV;
        ImageView PromoMenu;
        // imageView = itemView.findViewById(R.id.posterItem);
        NameTv = (TextView) itemView.findViewById(R.id.PromoName);
        DescriptionTV = itemView.findViewById(R.id.PromoDescription);
        DiscountRateTV = itemView.findViewById(R.id.PromoRate);
        PromoMenu = itemView.findViewById(R.id.PromoMenu);
        Discount discount = getItem(position);
        NameTv.setText(discount.getName());
        DescriptionTV.setText(discount.getDescription());
        DiscountRateTV.setText("OFF" + new DecimalFormat("#.0#").format(discount.getDiscountRate()) + "%");
        try {
            Log.d("account type", Users.currentUser.getAccountType());
            if (Users.currentUser != null)
                if ((!(Users.currentUser.getAccountType().toString()).equals("admin"))) {
                    PromoMenu.setVisibility(View.INVISIBLE);
                }
        } catch (Exception e) {
            PromoMenu.setVisibility(View.INVISIBLE);
        } finally {
            PromoMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), PromoMenu);
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
                                    Intent i = new Intent(itemView.getContext(), AddDiscount.class);
                                    i.putExtra(ExtraIntent.discount, discount);
                                    itemView.getContext().startActivity(i);
                                }
                                return true;
                                case R.id.promo_delete: {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext(), R.style.CustomAlertDialog);
                                    LayoutInflater factory = LayoutInflater.from(itemView.getContext());
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
                                            PromoRef.document(discount.getID()).delete();
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
        return itemView;
    }



}
