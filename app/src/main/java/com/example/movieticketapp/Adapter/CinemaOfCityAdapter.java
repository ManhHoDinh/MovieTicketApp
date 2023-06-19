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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieticketapp.Activity.Cinema.AddCinemaActivity;
import com.example.movieticketapp.Activity.City.AddCityActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Cinema;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.ShowTime;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CinemaOfCityAdapter extends ArrayAdapter<Cinema> {
    private List<Cinema> listCinema;
    public CinemaOfCityAdapter(@NonNull Context context, int resource, List<Cinema> listCinema) {
        super(context, resource, listCinema);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.cinema_of_city_item, null);
        TextView nameTv = itemView.findViewById(R.id.nameCinema);
        TextView addressTv = itemView.findViewById(R.id.addressCinema);
        TextView hotlineTv = itemView.findViewById(R.id.hotline);
        RoundedImageView image = itemView.findViewById(R.id.imageCinema);
        ImageView cinemaMenu = itemView.findViewById(R.id.cinemaMenu);
        Cinema cinema = getItem(position);
        nameTv.setText(cinema.getName());
        addressTv.setText(cinema.getAddress());
        hotlineTv.setText(cinema.getHotline());
        Picasso.get().load(cinema.getImage()).into(image);
        cinemaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), cinemaMenu);
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
                                Intent i = new Intent(itemView.getContext(), AddCinemaActivity.class);
                                i.putExtra("cinema", cinema);
                                i.putExtra("cityID", cinema.getCityID());
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
                                        CollectionReference cinemaRef = db.collection("Cinema");
                                        FirebaseRequest.database.collection("Showtime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                boolean isExisted = false;
                                                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                                    ShowTime showTime = documentSnapshot.toObject(ShowTime.class);
                                                    if(showTime.getCinemaID().equals(cinema.getCinemaID())){
                                                        isExisted = true;
                                                    }
                                                }
                                                if(isExisted){
                                                    Toast.makeText(getContext(), "Cinema is having showtime!!", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    cinemaRef.document(cinema.getCinemaID()).delete();
                                                }
                                            }
                                        });
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
        return itemView;
    }
}
