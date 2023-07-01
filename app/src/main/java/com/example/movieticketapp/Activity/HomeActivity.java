package com.example.movieticketapp.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Activity.Account.AccountActivity;
import com.example.movieticketapp.Activity.City.AddCityActivity;
import com.example.movieticketapp.Activity.City.CinemaOfCity;
import com.example.movieticketapp.Activity.City.CityViewAllActivity;
import com.example.movieticketapp.Activity.Discount.AddDiscount;
import com.example.movieticketapp.Activity.Discount.DiscountViewAll;
import com.example.movieticketapp.Activity.Movie.SearchActivity;
import com.example.movieticketapp.Activity.Movie.ViewAllActivity;
import com.example.movieticketapp.Activity.Notification.NotificationActivity;
import com.example.movieticketapp.Activity.Report.ReportActivity;
import com.example.movieticketapp.Activity.Ticket.Service.AddService;
import com.example.movieticketapp.Activity.Ticket.Service.ServiceViewAll;
import com.example.movieticketapp.Activity.Ticket.MyTicketAllActivity;
import com.example.movieticketapp.Activity.Wallet.MyWalletActivity;
import com.example.movieticketapp.Adapter.CityAdapter;
import com.example.movieticketapp.Adapter.ListTypeAdapter;
import com.example.movieticketapp.Adapter.PromotionAdapter;
import com.example.movieticketapp.Adapter.ServiceAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.City;
import com.example.movieticketapp.Model.Discount;
import com.example.movieticketapp.Model.Service;
import com.example.movieticketapp.Model.UserAndDiscount;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.HomeScreenBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.SupportMapFragment;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //private ViewPager2 viewPager;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    SupportMapFragment smf;
    FusedLocationProviderClient client;

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
    private ImageView addDiscount;
    private TextView viewAllPlayingBtn;
    private TextView viewAllComingBtn;
    private ImageView addService;
    private RecyclerView serviceView;
    private TextView viewAllCity;
    private ImageView addCity;
    private RecyclerView cityView;


    ConstraintLayout serviceHeader;


    ConstraintLayout cityHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String[] listType = {"All", "Horror", "Action", "Drama", "War", "Comedy", "Crime"};
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        accountImage = findViewById(R.id.accountImage);
        addDiscount = findViewById(R.id.AddDiscount);
        addDiscount = findViewById(R.id.AddDiscount);
        viewAllPlayingBtn = findViewById(R.id.viewAllPlayingBtn);
        viewAllComingBtn = findViewById(R.id.viewAllComingBtn);
        promotionView = findViewById(R.id.promotionView);
        searchView=findViewById(R.id.searchField);
        serviceView = findViewById(R.id.ServiceView);
        addService = findViewById(R.id.AddService);
        addCity = findViewById(R.id.addCity);
        cityView = findViewById(R.id.cityView);
        viewAllCity = findViewById(R.id.cityViewAll);
        cityHeader = findViewById(R.id.cityHeader);
        serviceHeader = findViewById(R.id.ServiceHeader);
        checkTypeUser();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful())
                    {
                        Log.w(TAG, "Fetching FCM token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.d(TAG, "FCM token: " + token);

                    FirebaseMessaging.getInstance().subscribeToTopic("all")
                            .addOnCompleteListener(subscribeTask -> {
                                if (subscribeTask.isSuccessful()) {
                                    Log.d(TAG, "Subscribed to topic 'all'");
                                } else {
                                    Log.w(TAG, "Subscription to topic 'all' failed", subscribeTask.getException());
                                }
                            });
                });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                }
            }
        });
        if(currentUser != null){
            if (currentUser.getPhotoUrl() != null)
                Picasso.get().load(currentUser.getPhotoUrl()).into(accountImage);
            else accountImage.setImageResource(R.drawable.avatar);
        }


        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), AccountActivity.class);
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
        typeListView = (RecyclerView) findViewById(R.id.listTypeMovie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeListView.setLayoutManager(layoutManager);
        typeListView.setAdapter(new ListTypeAdapter(this, listType));
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.walletPage:
                    startActivity(new Intent(HomeActivity.this, MyWalletActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.ticketPage:
                    startActivity(new Intent(HomeActivity.this, MyTicketAllActivity.class));
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
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ServiceViewAll.class);
                startActivity(i);
            }
        });
        GetDiscounts();
        viewAllCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, CityViewAllActivity.class);
                startActivity(i);
            }
        });



        addDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AddDiscount.class);
                startActivity(i);
            }
        });
        //checkAccountType();
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddCityActivity.class);
                startActivity(intent);
            }
        });
        viewAllPlayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewAllActivity.class);
                intent.putExtra("status", "playing");
                startActivity(intent);
            }
        });
        viewAllComingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewAllActivity.class);
                intent.putExtra("status", "coming");
                startActivity(intent);
            }
        });

    }

//    void checkAccountType() {
//        try {
//                if (isAdmin) {
//                    Log.e("fd", "binh");
//                    ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
//                    params.height = 0;
//                    binding.AddDiscount.setLayoutParams(params);
//                    addDiscount.setVisibility(View.INVISIBLE);
//                    ViewGroup.LayoutParams serviceParams = binding.AddService.getLayoutParams();
//                    serviceParams.height = 0;
//                    binding.AddService.setLayoutParams(serviceParams);
//                    addService.setVisibility(View.INVISIBLE);
//                    ViewGroup.LayoutParams cityParams = addCity.getLayoutParams();
//                    addCity.setLayoutParams(cityParams);
//                    addCity.setVisibility(View.INVISIBLE);
//
//            }
//            else{
//                Menu menu = binding.bottomNavigation.getMenu();
//                MenuItem ReportPage = menu.findItem(R.id.ReportPage);
//                MenuItem WalletPage = menu.findItem(R.id.walletPage);
//                WalletPage.setVisible(false);
//                ReportPage.setVisible(true);
//
//            }
//
//        } catch (Exception e) {
//            ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
//            params.height = 0;
//            binding.AddDiscount.setLayoutParams(params);
//            addDiscount.setVisibility(View.INVISIBLE);
//
//            ViewGroup.LayoutParams serviceParams = binding.AddService.getLayoutParams();
//            serviceParams.height = 0;
//            binding.AddService.setLayoutParams(serviceParams);
//            addService.setVisibility(View.INVISIBLE);
//            addCity.setVisibility(View.VISIBLE);
//        }




//    }

    void GetDiscounts() {

        List<Discount> Discounts = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users currentUser = documentSnapshot.toObject(Users.class);

                    if(((currentUser.getAccountType().toString()).equals("admin"))){

//                FirebaseFirestore.getInstance().collection(Discount.CollectionName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<Discount> listDiscounts = new ArrayList<Discount>();
//                        for(DocumentSnapshot doc : queryDocumentSnapshots){
//                            Discount f = doc.toObject(Discount.class);
//                            listDiscounts.add(f);
//
//                        }
//                        PromotionAdapter promotionAdapter = new PromotionAdapter(HomeActivity.this,R.layout.promo_item,listDiscounts);
//                        promotionView.setAdapter(promotionAdapter);
//                    }
//                });
                    FirebaseFirestore.getInstance().collection(Discount.CollectionName).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            List<Discount> listDiscounts = new ArrayList<Discount>();
                            for (DocumentSnapshot doc : value) {
                                Discount f = doc.toObject(Discount.class);
                                listDiscounts.add(f);

                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                            PromotionAdapter promotionAdapter = new PromotionAdapter(listDiscounts, null);
                            promotionView.setLayoutManager(linearLayoutManager);
                            promotionView.setAdapter(promotionAdapter);

                        }
                    });
                } else {
                    CollectionReference PromoRef = db.collection(UserAndDiscount.collectionName);


                    Query query = PromoRef.whereEqualTo("userID", FirebaseRequest.mAuth.getUid());

                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<String> listDiscountID = new ArrayList<>();

                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                listDiscountID.add(doc.get("discountID").toString());
                                //DocumentReference document = FirebaseRequest.database.collection(Discount.CollectionName).document(doc.get("discountID").toString());
                            }


                            if (listDiscountID.size() > 0) {
                                Query query2 = db.collection(Discount.CollectionName).whereIn("ID", listDiscountID);
                                query2.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot discountvalue, @Nullable FirebaseFirestoreException error) {
                                        for (DocumentSnapshot doc : discountvalue) {
                                            Discount f = doc.toObject(Discount.class);
                                            Discounts.add(f);
                                        }

                                        //   LinearLayoutManager VerLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                        // promotionView.setLayoutManager(VerLayoutManager);
                                        Intent intent = getIntent();
                                        PromotionAdapter promotionAdapter = new PromotionAdapter(Discounts, null);
                                        promotionView.setAdapter(promotionAdapter);
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
                                });

                            } else
                                promotionView.setAdapter(new PromotionAdapter(new ArrayList<Discount>(), null));

                        }
                    });
                }
            }
        });


    }

    void checkTypeUser() {
        FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users currentUser = documentSnapshot.toObject(Users.class);
                Log.e("fs",  currentUser.getAccountType());
                if (((currentUser.getAccountType().toString()).equals("admin"))) {
                    GetServices();
                    GetCities();
                    Menu menu = binding.bottomNavigation.getMenu();
                    MenuItem ReportPage = menu.findItem(R.id.ReportPage);
                    MenuItem WalletPage = menu.findItem(R.id.walletPage);
                    WalletPage.setVisible(false);
                    ReportPage.setVisible(true);
                } else {

                    ViewGroup.LayoutParams params = binding.AddDiscount.getLayoutParams();
                    params.height = 0;
                    binding.AddDiscount.setLayoutParams(params);
                    addDiscount.setVisibility(View.INVISIBLE);
//                    ViewGroup.LayoutParams serviceParams = binding.AddService.getLayoutParams();
//                    serviceParams.height = 0;
//                    binding.AddService.setLayoutParams(serviceParams);
                    addService.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams cityParams = addCity.getLayoutParams();
//                    addCity.setLayoutParams(cityParams);
                    addCity.setVisibility(View.GONE);
                    serviceHeader.setVisibility(View.GONE);
                    cityHeader.setVisibility(View.GONE);
                    serviceView.setVisibility(View.GONE);
                    cityView.setVisibility(View.GONE);


                }
            }
        });

    }

    void GetServices() {

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
                } else {
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

    void GetCities() {
        List<City> cities = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cityRef = db.collection("City");
        cityRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // this method is called when error is not null
                    // and we get any error
                    // in this case we are displaying an error message.
                    Toast.makeText(HomeActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    cities.clear();
                    for (DocumentSnapshot documentSnapshot : value) {
                        City newCity = documentSnapshot.toObject(City.class);
                        cities.add(newCity);
                        Log.e("d", newCity.getName());

                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                    cityView.setLayoutManager(linearLayoutManager);
                    cityView.setAdapter(new CityAdapter(cities, HomeActivity.this));
//                    cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                            Intent intent = new Intent(HomeActivity.this, CinemaOfCity.class);
//                            intent.putExtra("city", cities.get(i));
//                            startActivity(intent);
//
//                        }
//                    });
                    if (cities.size() == 0) {
                        ViewGroup.LayoutParams params = cityView.getLayoutParams();
                        params.height = 0;
                        cityView.setLayoutParams(params);
                    }
                    if (cities.size() == 1) {
                        ViewGroup.LayoutParams params = cityView.getLayoutParams();
                        params.height = 400;
                        cityView.setLayoutParams(params);
                    }
                    if (cities.size() == 2) {
                        ViewGroup.LayoutParams params = cityView.getLayoutParams();
                        params.height = 800;
                        cityView.setLayoutParams(params);
                    }
                }
            }
        });
    }
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





}