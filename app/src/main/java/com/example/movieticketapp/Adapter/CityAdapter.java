package com.example.movieticketapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Activity.Cinema.AddCinemaActivity;
import com.example.movieticketapp.Activity.City.AddCityActivity;
import com.example.movieticketapp.Activity.City.CinemaOfCity;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<City> {
    List<City> listCity = new ArrayList<City>();
    Context context;
    public CityAdapter(@NonNull Context context, int resource, List<City> listCity) {
        super(context, resource, listCity);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View itemView;
       itemView = LayoutInflater.from(getContext()).inflate(R.layout.city_item, null);
       TextView cityName = itemView.findViewById(R.id.cityName);
        ImageView cityMenu = itemView.findViewById(R.id.cityMenu);
       City city = getItem(position);
       cityName.setText(city.getName());
       cityMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               PopupMenu popup = new PopupMenu(view.getContext(), cityMenu);
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
                               Intent i = new Intent(itemView.getContext(), AddCityActivity.class);
                               i.putExtra("city", city);
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
                                       CollectionReference cityRef = db.collection("City");
                                       cityRef.document(city.getID()).delete();
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
//       itemView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//                Intent intent = new Intent(context, CinemaOfCity.class);
//                intent.putExtra("ity", city.getName());
//
//           }
//       });
       return itemView;
    }
}
