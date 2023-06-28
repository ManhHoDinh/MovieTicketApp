package com.example.movieticketapp.Activity.Ticket.Service;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.ServiceAdapter;
import com.example.movieticketapp.Model.Service;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityServiceViewAllBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceViewAll extends AppCompatActivity {

    ActivityServiceViewAllBinding binding;
    private RecyclerView ServiceView;
    private ImageView backBtn;
    private ImageView AddServiceButton;


    private List<Service> services = new ArrayList<>();
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view_all);
        ServiceView = findViewById(R.id.ServiceView);
        backBtn = findViewById(R.id.BackBtn);
        AddServiceButton = findViewById(R.id.AddServiceButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference ServiceRef = db.collection("Service");
            ServiceRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        // this method is called when error is not null
                        // and we get any error
                        // in this case we are displaying an error message.
                        Toast.makeText(ServiceViewAll.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                        return;
                    } else
                    {
                        services.clear();
                        for (DocumentSnapshot documentSnapshot : value) {
                            Service newService = documentSnapshot.toObject(Service.class);
                            services.add(newService);
                            Log.d(TAG, "data: " + newService.getName());
                        }
                        LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        ServiceView.setAdapter(new ServiceAdapter(services));
                        ServiceView.setLayoutManager(VerLayoutManager);
                    }
                }
            });

        AddServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceViewAll.this, AddService.class);
                startActivity(i);
            }
        });
        checkAccountType();
    }

    void checkAccountType()
    {
        try{
            Log.d("account type", Users.currentUser.getAccountType());
            if(Users.currentUser!=null)
                if((!(Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                    ViewGroup.LayoutParams params = binding.AddServiceButton.getLayoutParams();
                    params.height = 0;
                    binding.AddServiceButton.setLayoutParams(params);
                    binding.AddServiceButton.setVisibility(View.INVISIBLE);
                }
        }
        catch (Exception e)
        {
            ViewGroup.LayoutParams params = AddServiceButton.getLayoutParams();
            params.height = 0;
            AddServiceButton.setLayoutParams(params);
            AddServiceButton.setVisibility(View.INVISIBLE);
        }
    }
}