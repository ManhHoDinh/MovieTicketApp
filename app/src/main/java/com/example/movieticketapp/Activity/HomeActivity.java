package com.example.movieticketapp.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Account.AccountActivity;
import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Activity.Discount.DiscountViewAll;
import com.example.movieticketapp.Activity.Movie.SearchActivity;
import com.example.movieticketapp.Activity.Movie.ViewAllActivity;
import com.example.movieticketapp.Activity.Service.AddService;
import com.example.movieticketapp.Activity.Service.ServiceViewAll;
import com.example.movieticketapp.Activity.Notification.NotificationActivity;
import com.example.movieticketapp.Activity.Report.ReportActivity;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.ListTypeAdapter;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Adapter.ServiceAdapter;
import com.example.movieticketapp.Adapter.posterAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Service;
import com.example.movieticketapp.Model.UserAndDiscount;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //private ViewPager2 viewPager;

    private RecyclerView typeListView;
    private RecyclerView posterRecyclerView;
    private ListView promotionView;
    private RecyclerView serviceView;
    private SearchView searchView;
    private ViewPager2 typeMoviePage;
    private BottomNavigationView bottomNavigationView;
    private TabLayout typeMovieLayout;
    private BottomNavigationView bottomNavigation;
    private HomeScreenBinding binding;
    private ImageView accountImage;
    private ImageView addDiscount;

    private ImageView addService;
    private TextView serviceViewAll;
    private TextView viewAllPlayingBtn;
    private TextView viewAllComingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String[] listType = {"All", "Horror", "Action", "Drama", "War", "Comedy", "Crime"};
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        accountImage = findViewById(R.id.accountImage);
        addDiscount = findViewById(R.id.AddDiscount);
        viewAllPlayingBtn = findViewById(R.id.viewAllPlayingBtn);
        viewAllComingBtn = findViewById(R.id.viewAllComingBtn);
        promotionView = findViewById(R.id.promotionView);
        searchView=findViewById(R.id.searchField);
        addService = findViewById(R.id.AddService);
        serviceViewAll = findViewById(R.id.ServiceViewAll);
        searchView = findViewById(R.id.searchField);
        serviceView = findViewById(R.id.ServiceView);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                }
            }
        });
        if (currentUser.getPhotoUrl() != null)
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
        //  GetComingMovies();

        typeListView = (RecyclerView) findViewById(R.id.listTypeMovie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeListView.setLayoutManager(layoutManager);
        //  typeListView.addItemDecoration(new AddDecoration(10));
        typeListView.setAdapter(new ListTypeAdapter(this, listType));

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.walletPage:
                    startActivity(new Intent(HomeActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.ReportPage:
                    startActivity(new Intent(HomeActivity.this, ReportActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.NotificationPage:
                    startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.ticketPage:
                    startActivity(new Intent(HomeActivity.this, MyTicketAllActivity.class));
                    overridePendingTransition(0, 0);
                    break;
            }
            return true;
        });
        binding.DiscountViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, DiscountViewAll.class);
                startActivity(i);
            }
        });
        binding.ServiceViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ServiceViewAll.class);
                startActivity(i);
            }
        });
        GetDiscounts();
        GetServices();
        addDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AddDiscount.class);
                startActivity(i);
            }
        });

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddService.class);
                startActivity(i);
            }
        });
        checkAccountType();
    }
    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
    }
    void checkAccountType() {
        try {
            if (Users.currentUser != null)
                if ((!(Users.currentUser.getAccountType().toString()).equals("admin"))) {
                    ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
                    params.height = 0;
                    binding.AddDiscount.setLayoutParams(params);
                    addDiscount.setVisibility(View.INVISIBLE);

                    ViewGroup.LayoutParams serviceParams = binding.AddService.getLayoutParams();
                    serviceParams.height = 0;
                    binding.AddService.setLayoutParams(serviceParams);
                    addService.setVisibility(View.INVISIBLE);
                }
            else {
                    Menu menu = binding.bottomNavigation.getMenu();
                    MenuItem ReportPage = menu.findItem(R.id.ReportPage);
                    MenuItem WalletPage = menu.findItem(R.id.walletPage);
                    WalletPage.setVisible(false);
                    ReportPage.setVisible(true);
                }
        } catch (Exception e) {
            ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
            params.height = 0;
            binding.AddDiscount.setLayoutParams(params);
            addDiscount.setVisibility(View.INVISIBLE);

            ViewGroup.LayoutParams serviceParams = binding.AddService.getLayoutParams();
            serviceParams.height = 0;
            binding.AddService.setLayoutParams(serviceParams);
            addService.setVisibility(View.INVISIBLE);
        }
        viewAllPlayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ViewAllActivity.class));
            }
        });
    }

    void GetDiscounts() {

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
                } else {
                    Discounts.clear();
                    for (DocumentSnapshot documentSnapshot : value) {
                        Discount f = documentSnapshot.toObject(Discount.class);
                        Discounts.add(f);
                        Log.d(TAG, "data: " + f.getName());
                    }
                    promotionView = (ListView) findViewById(R.id.promotionView);
                    LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    promotionView.setAdapter(new PromotionAdapter(HomeActivity.this,R.layout.promo_item,Discounts));
                    //promotionView.setLayoutManager(VerLayoutManager);
                    if (Discounts.size() == 0) {
                        ViewGroup.LayoutParams params = promotionView.getLayoutParams();
                        params.height = 0;
                        promotionView.setLayoutParams(params);
                    }
                    if (Discounts.size() == 1) {
                        ViewGroup.LayoutParams params = promotionView.getLayoutParams();
                        params.height = 300;
                        promotionView.setLayoutParams(params);
                    }
                    if (Discounts.size() == 2) {
                        ViewGroup.LayoutParams params = promotionView.getLayoutParams();
                        params.height = 700;
                        promotionView.setLayoutParams(params);
                    }

                }
            }
        });

    }

    void GetServices()
    {
        List<Service> services = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ServiceRef = db.collection("Service");
        ServiceRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // this method is called when error is not null
                    // and we get any error
                    // in this case we are displaying an error message.
                    Toast.makeText(HomeActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
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
                    serviceView.setAdapter(new ServiceAdapter(services));
                    serviceView.setLayoutManager(VerLayoutManager);
                    if (services.size() == 0) {
                        ViewGroup.LayoutParams params = serviceView.getLayoutParams();
                        params.height = 0;
                        serviceView.setLayoutParams(params);
                    }
                    if (services.size() == 1) {
                        ViewGroup.LayoutParams params = serviceView.getLayoutParams();
                        params.height = 400;
                        serviceView.setLayoutParams(params);
                    }
                    if (services.size() == 2) {
                        ViewGroup.LayoutParams params = serviceView.getLayoutParams();
                        params.height = 800;
                        serviceView.setLayoutParams(params);
                    }
                }
            }
        });
    }
}
