package com.example.movieticketapp.Activity.Discount;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.UserAndDiscount;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDiscount extends AppCompatActivity {
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

    TextInputEditText Name;
    TextInputEditText Description;
    TextInputEditText DiscountPercent;
    ImageView IncreasingDiscountPercent;
    ImageView DecreasingDiscountPercent;
    TextView CancelButton;
    TextView ConfirmButton;
    Discount discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_discount_screen);
        Name = findViewById(R.id.DiscountName);
        Description = findViewById(R.id.DiscountDescription);
        DiscountPercent = findViewById(R.id.DiscountPercent);
        IncreasingDiscountPercent = findViewById(R.id.IncreasingDiscountPercent);
        DecreasingDiscountPercent = findViewById(R.id.DecreasingDiscountPercent);
        ConfirmButton = findViewById(R.id.ConfirmButton);
        CancelButton = findViewById(R.id.CancelButton);
        ConstraintLayout layoutElement = findViewById(R.id.AddDiscountLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        LinearLayoutCompat layoutElement2 = findViewById(R.id.AddDiscountLayout2); // Replace with your actual layout element ID

        layoutElement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        Intent intent = getIntent();
        discount = intent.getParcelableExtra(ExtraIntent.discount);
        if(discount!=null) {
            Name.setText(discount.getName());
            Description.setText(discount.getDescription());
            DiscountPercent.setText(String.valueOf(discount.getDiscountRate()));

        }
        IncreasingDiscountPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncreasingDiscountPercent();
            }
        });
        DecreasingDiscountPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecreasingDiscountPercent();
            }
        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Confirm() {
        boolean error = false;
        if(Name.length()==0)
        {
            Name.setError("Discount Name is not empty!!!");
            error=true;
        }
        if(Description.length()==0)
        {
            Description.setError("Discount Description is not empty!!!");
            error=true;
        }
        if (DiscountPercent.length()==0)
        {
            DiscountPercent.setError("Discount Percent is not empty!!!");
            error=true;
        }
        else{
            double percent = Double.valueOf(DiscountPercent.getText().toString());
            if(percent<0 || percent>100)
            {
                DiscountPercent.setError("Discount Percent between 0 and 100!!!");
                error=true;
            }
        }
        if(!error)
            CreateDiscountToDatabase();
    }

    private void CreateDiscountToDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference doc ;
        boolean isDocNull = false;
        if (discount!=null)
         doc = db.collection(Discount.CollectionName).document(discount.getID());
        else{
            doc = db.collection(Discount.CollectionName).document();
            isDocNull = true;
        }

        Discount discount = new Discount(doc.getId(), Name.getText().toString(), Description.getText().toString(), Double.valueOf(DiscountPercent.getText().toString()));
        doc.set(discount.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(discount==null)
                        {
                            Name.setText("");
                            Description.setText("");
                            DiscountPercent.setText("50");
                        }
                        else
                            finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        if(isDocNull == true){
            addUserAndDiscountToDb(doc);
        }

    }
    void addUserAndDiscountToDb(DocumentReference documentReference){
        FirebaseFirestore.getInstance().collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listDocs = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc : listDocs){
                    UserAndDiscount userAndDiscount = new UserAndDiscount(doc.getId(), documentReference.getId());
                    FirebaseFirestore.getInstance().collection("UserAndDiscount").document().set(userAndDiscount);
                }
            }
        });
//        FirebaseFirestore.getInstance().collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<DocumentSnapshot> listDocs = value.getDocuments();
//                for(DocumentSnapshot doc : listDocs){
//                    UserAndDiscount userAndDiscount = new UserAndDiscount(doc.getId(), documentReference.getId());
//                    FirebaseFirestore.getInstance().collection("UserAndDiscount").document().set(userAndDiscount);
//                }
//            }
//        });
    }
    void IncreasingDiscountPercent()
    {
        try {
            double percent = Double.valueOf(DiscountPercent.getText().toString());

            if ((percent+5)<= 100)
            {
                percent+= 5;
                DiscountPercent.setText(String.valueOf(percent));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error Increasing Discount Percent: ", e);
        }
    }
    void DecreasingDiscountPercent()
    {
        try {
            double percent = Double.valueOf(DiscountPercent.getText().toString());
            if ((percent-5)>= 0)
            {
                percent-= 5;
                DiscountPercent.setText(String.valueOf(percent));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error Decreasing Discount Percent: ", e);
        }

    }
}