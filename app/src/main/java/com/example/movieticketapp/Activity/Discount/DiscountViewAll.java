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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.HomeActivity;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.UserAndDiscount;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityDiscountViewAllBinding;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DiscountViewAll extends AppCompatActivity {
    ActivityDiscountViewAllBinding binding;
    private ListView promotionView;
    List<Discount> Discounts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDiscountViewAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        promotionView =(ListView) findViewById(R.id.promotionView);
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
        if(Users.currentUser!=null)
            if(((Users.currentUser.getAccountType().toString()).equals("admin"))){
                FirebaseFirestore.getInstance().collection(Discount.CollectionName).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Discount> listDiscounts = new ArrayList<Discount>();
                        for(DocumentSnapshot doc : value){
                            Discount f = doc.toObject(Discount.class);
                            listDiscounts.add(f);

                        }
                        PromotionAdapter promotionAdapter = new PromotionAdapter(DiscountViewAll.this,R.layout.promo_item,listDiscounts);
                        promotionView.setAdapter(promotionAdapter);

                    }
                });
            }
        else{
                CollectionReference PromoRef = db.collection(UserAndDiscount.collectionName);
                Query query = PromoRef.whereEqualTo("userID", FirebaseRequest.mAuth.getUid());

                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        List<String> listDiscountID = new ArrayList<>();
                        for(DocumentSnapshot doc : value){
                            listDiscountID.add(doc.get("discountID").toString());
                            // DocumentReference document = FirebaseRequest.database.collection(Discount.CollectionName).document(doc.get("discountID").toString());
                        }
                        if(listDiscountID.size() > 0){
                            Query query2 = db.collection(Discount.CollectionName).whereIn("ID", listDiscountID);
                            query2.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for(DocumentSnapshot doc : value){
                                        Discount f = doc.toObject(Discount.class);
                                        Discounts.add(f);
                                    }

                                    //   LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                    // promotionView.setLayoutManager(VerLayoutManager);
                                    Intent intent = getIntent();
                                    PromotionAdapter promotionAdapter = new PromotionAdapter(DiscountViewAll.this,R.layout.promo_item,Discounts);
                                    promotionView.setAdapter(promotionAdapter);
                                    Double totalBook = intent.getDoubleExtra("total", 0);
                                    if( totalBook != 0){
                                        promotionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                double finalTotal = totalBook * Discounts.get(i).getDiscountRate() /100;
                                                intent.putExtra("total", finalTotal);
                                                intent.putExtra("nameDiscount", Discounts.get(i).getName());
                                                intent.putExtra("idDiscount", Discounts.get(i).getID());
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            });
                        }


                    }
                });
            }

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
            promotionView.setAdapter(new PromotionAdapter(DiscountViewAll.this,R.layout.promo_item, filteredlist));

    }
}