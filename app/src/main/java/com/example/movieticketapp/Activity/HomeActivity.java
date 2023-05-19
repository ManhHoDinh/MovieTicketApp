package com.example.movieticketapp.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Account.AccountActivity;
import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.ListTypeAdapter;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Adapter.posterAdapter;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.InforBooked;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //private ViewPager2 viewPager;

    private RecyclerView typeListView;
    private RecyclerView posterRecyclerView;
    private RecyclerView promotionView;
    private SearchView searchView;
    private ViewPager2 typeMoviePage;
    private BottomNavigationView bottomNavigationView;
    private TabLayout typeMovieLayout;
    private BottomNavigationView bottomNavigation;
    private HomeScreenBinding binding;
    private ImageView accountImage;
    private  ImageView addDiscount;
    private TextView viewAllBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String[] listType = {"All", "Horror", "Action", "Drama", "War", "Comedy", "Crime"};
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        accountImage = findViewById(R.id.accountImage);
        addDiscount= findViewById(R.id.AddDiscount);
        viewAllBtn = findViewById(R.id.viewAllBtn);
        searchView = findViewById(R.id.searchField);


        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                }
            }
        });
        if (currentUser.getPhotoUrl()!=null)
            Picasso.get().load(currentUser.getPhotoUrl()).into(accountImage);
        else accountImage.setImageResource(R.drawable.avatar);

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(i);
            }
        });
        List<Integer> listPoster = new ArrayList<Integer>();
        listPoster.add(R.drawable.poster_1);
        listPoster.add(R.drawable.poster_1);
        listPoster.add(R.drawable.poster_1);

        posterRecyclerView = (RecyclerView) findViewById(R.id.commingMovieView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        posterRecyclerView.setAdapter(new posterAdapter(listPoster));
        posterRecyclerView.setLayoutManager(linearLayoutManager);
        typeListView = (RecyclerView) findViewById(R.id.listTypeMovie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeListView.setLayoutManager(layoutManager);
        //  typeListView.addItemDecoration(new AddDecoration(10));
        typeListView.setAdapter(new ListTypeAdapter(this, listType));
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.walletPage:
                    startActivity(new Intent(HomeActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(HomeActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0,0);
                    break;
            }
            return true;
        });

        List<Discount> Discounts = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference PromoRef = db.collection(Discount.CollectionName);
        PromoRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // this method is called when error is not null
                    // and we get any error
                    // in this case we are displaying an error message.
                    Toast.makeText(HomeActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
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
                    if(Discounts.size() == 0)
                    {
                        ViewGroup.LayoutParams params=promotionView.getLayoutParams();
                        params.height=0;
                        promotionView.setLayoutParams(params);
                    }
                    if(Discounts.size()==1)
                    {
                        ViewGroup.LayoutParams params=promotionView.getLayoutParams();
                        params.height=300;
                        promotionView.setLayoutParams(params);
                    }
                    if(Discounts.size()==2)
                    {
                        ViewGroup.LayoutParams params=promotionView.getLayoutParams();
                        params.height=700;
                        promotionView.setLayoutParams(params);
                    }

                }
            }
        });
//        PromoRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful())
//            {
//                QuerySnapshot querySnapshot = task.getResult();
//                for (DocumentSnapshot documentSnapshot : querySnapshot)
//                {
//                    Discount f = documentSnapshot.toObject(Discount.class);
//                    Discounts.add(f);Discounts.add(f); Discounts.add(f);
//
//                }
//                promotionView =(RecyclerView) findViewById(R.id.promotionView);
//                LinearLayoutManager VerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//                promotionView.setAdapter(new PromotionAdapter(Discounts));
//                promotionView.setLayoutManager(VerLayoutManager);
//                if(Discounts.size()==1)
//                {
//                    ViewGroup.LayoutParams params=promotionView.getLayoutParams();
//                    params.height=300;
//                    promotionView.setLayoutParams(params);
//                }
//                if(Discounts.size()==2)
//                {
//                    ViewGroup.LayoutParams params=promotionView.getLayoutParams();
//                    params.height=700;
//                    promotionView.setLayoutParams(params);
//                }
//            } else
//            {
//                Log.d(TAG, "Error getting documents: ", task.getException());
//            }
//        });

        addDiscount.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(HomeActivity.this, AddDiscount.class);
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
                    addDiscount.setVisibility(View.INVISIBLE);
                }
        }
        catch (Exception e)
        {
            ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
            params.height = 0;
            binding.AddDiscount.setLayoutParams(params);
            addDiscount.setVisibility(View.INVISIBLE);
        }
        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ViewAllActivity.class));
            }
        });
    }

}
