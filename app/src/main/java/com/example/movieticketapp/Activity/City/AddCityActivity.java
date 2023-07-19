package com.example.movieticketapp.Activity.City;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Booking.BookedActivity;
import com.example.movieticketapp.Activity.Cinema.AddCinemaActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private Button confirmBtn;
    private Button cancelBtn;
    private Button backBtn;
    private AutoCompleteTextView cityTv;
    City city;

   private String selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        backBtn = findViewById(R.id.backbutton);
        cityTv = findViewById(R.id.cityAutoTv);
        String[] listCity = {"An Giang","Ba Ria – Vung Tau","Bac Lieu","Bac Giang ","Bac Kan ",
                "Bac Ninh","Ben Tre","Binh Duong","Binh Dinh","Binh Phuoc","Binh Thuan",
                "Cà Mau","Cao Bang","Can Tho","Da Nang","Dak Lak","Dak Nong","Dien Bien","Yen Bai",
                "Đong Nai","Dong Thap","Gia Lai","Ha Giang","Ha Nam","Ha Noi","Ha Tinh","Hai Duong","Hai Phong","Hau Giang","Hoa Binh",
                "Ho Chi Minh","Hung Yen","Khanh Hoa","Kien Giang","Kon Tum","Lai Chau","Lang Son","Lao Cai","Lam Dong","Long An","Nam Dinh",
                "Nghe An","Ninh Binh","Ninh Thuan","Phu Tho","Phu Yen","Quang Binh","Quang Nam","Quang Ngai","Quang Ninh","Quang Tri","Soc Trang",
                "Son La","Tay Ninh","Thai Binh","Thai Nguyen","Thanh Hoa","Thua Thien Hue","Tien Giang","Tra Vinh","Tuyen Quang","Vinh Long","Vinh Phuc",
                };
        cityTv.setAdapter(new ArrayAdapter<String>(AddCityActivity.this, R.layout.country_item, listCity));
        cityTv.setDropDownBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_background_1)));

        cityTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCity = cityTv.getText().toString();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedCity == null){
                    Toast.makeText(AddCityActivity.this, "Please type the name of city!!", Toast.LENGTH_SHORT).show();
                }

                else {

                    FirebaseRequest.database.collection("City").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            boolean isExisted = false;
                            for(DocumentSnapshot doc : queryDocumentSnapshots){
                                City cityDoc = doc.toObject(City.class);
                                if(cityDoc.getName().equals(selectedCity)){
                                    isExisted = true;
                                    Log.e("fs",selectedCity);
                                }
                            }
                            if(isExisted){
                                Toast.makeText(AddCityActivity.this, "City existed!", Toast.LENGTH_SHORT).show();
                            }
                            else CreateDiscountToDatabase();
                        }
                    });

                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void CreateDiscountToDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc ;
        doc = db.collection("City").document();
        City cityDoc = new City(doc.getId(), selectedCity);
        doc.set(cityDoc.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                            finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}