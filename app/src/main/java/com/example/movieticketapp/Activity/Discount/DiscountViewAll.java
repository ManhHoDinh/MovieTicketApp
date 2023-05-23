package com.example.movieticketapp.Activity.Discount;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityDiscountViewAllBinding;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiscountViewAll extends AppCompatActivity {
    ActivityDiscountViewAllBinding binding;
    private RecyclerView promotionView;
    List<Discount> Discounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDiscountViewAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // below line is to call set on query text listener method.
        binding.searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        binding.BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference PromoRef = db.collection(Discount.CollectionName);
        PromoRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // this method is called when error is not null
                    // and we get any error
                    // in this case we are displaying an error message.
                    Toast.makeText(DiscountViewAll.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Discounts.clear();
                    for (DocumentSnapshot documentSnapshot : value)
                    {
                        Discount f = documentSnapshot.toObject(Discount.class);
                        Discounts.add(f);
                        Log.d(TAG, "data: " + f.getName());
                    }
                    promotionView =(RecyclerView) findViewById(R.id.promotionView);
                    LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    promotionView.setAdapter(new PromotionAdapter(Discounts));
                    promotionView.setLayoutManager(VerLayoutManager);

                }
            }
        });

        binding.AddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DiscountViewAll.this, AddDiscount.class);
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
                    ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
                    params.height = 0;
                    binding.AddDiscount.setLayoutParams(params);
                    binding.AddDiscount.setVisibility(View.INVISIBLE);
                }
        }
        catch (Exception e)
        {
            ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
            params.height = 0;
            binding.AddDiscount.setLayoutParams(params);
            binding.AddDiscount.setVisibility(View.INVISIBLE);
        }
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Discount> filteredlist = new ArrayList<Discount>();

        // running a for loop to compare elements.
        for (Discount item : Discounts) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
            promotionView.setAdapter(new PromotionAdapter(filteredlist));

    }
}