package com.example.movieticketapp.Activity.City;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Cinema.AddCinemaActivity;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
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
    private EditText cityNameEt;
    private Button confirmBtn;
    private Button cancelBtn;
    private Button backBtn;
    City city;
    List<String> listCity = new ArrayList<>();
   private static String selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        cityNameEt = findViewById(R.id.cityNameEt);
        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        backBtn = findViewById(R.id.backbutton);
        Intent intent = getIntent();
        city = intent.getParcelableExtra("city");
        if(city != null){
            cityNameEt.setText(city.getName());
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cityNameEt.getText() == null){
                    Toast.makeText(AddCityActivity.this, "Please type the name of city!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    CreateDiscountToDatabase();
                }

            }
        });
    }
    private void CreateDiscountToDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference doc ;

        if (city!=null)
            doc = db.collection("City").document(city.getID());
        else{
            doc = db.collection("City").document();
        }

        City cityDoc = new City(doc.getId(), cityNameEt.getText().toString());
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