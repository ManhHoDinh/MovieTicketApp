package com.example.movieticketapp.Activity.Ticket.Service;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Model.Service;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddService extends AppCompatActivity {

    TextInputEditText Name;
    TextInputEditText Detail;
    TextInputEditText Price;

    RoundedImageView Image;
    TextView CancelButton;
    TextView ConfirmButton;
    TextView titleFoodDrink;
    Service service;
    private ActivityResultLauncher<String> launcher;
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    StorageReference storageReference;
    Uri ImageUri;
    String ImageURL;
    boolean ImageSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        Name = findViewById(R.id.NameOfService);
        Detail = findViewById(R.id.DetailOfService);
        Price = findViewById(R.id.PriceOfService);
        Image = findViewById(R.id.ImageOfService);
        ConfirmButton = findViewById(R.id.ServiceConfirmButton);
        CancelButton = findViewById(R.id.ServiceCancelButton);
        titleFoodDrink = findViewById(R.id.titleFoodDrink);
        Intent intent = getIntent();
        service = intent.getParcelableExtra("service");
        LinearLayoutCompat layoutElement = findViewById(R.id.LinerLayout); // Replace with your actual layout element ID

        layoutElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        if (service != null)
        {
            Name.setText(service.getName());
            Detail.setText(service.getDetail());
            Price.setText(service.getPrice()+"");
            Picasso.get().load(service.getImage()).fit().centerCrop().into(Image);
            ImageSet = true;
            ImageURL = service.getImage();
            titleFoodDrink.setText("Edit Food/Drink");
        }

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                // Handle the selected image URI
                ImageUri = result;
                ImageSet = true;
                Image.setImageURI(ImageUri);
                uploadImageToStorage();
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

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
    }

    private void uploadImageToStorage() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String filename = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/" + filename);

        storageReference.putFile(ImageUri).addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                ImageURL = uri.toString();
                Toast.makeText(AddService.this, "Upload + Download Image Successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(exception -> {
                Toast.makeText(AddService.this, "Download Image Failed", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(exception -> {
            Toast.makeText(AddService.this, "Upload Image Failed", Toast.LENGTH_SHORT).show();
        });
    }

    private void Confirm() {
        boolean error = false;
        if(Name.length()==0)
        {
            Name.setError("Service Name must not be empty!!!");
            error=true;
        }
        if(Detail.length()==0)
        {
            Detail.setError("Service Detail must not be empty!!!");
            error=true;
        }
        if(Price.length()==0)
        {
            Price.setError("Service Price must not be empty!!!");
            error=true;
        }
        if(ImageSet == false)
        {
            Toast.makeText(getApplicationContext(), "Image must not be Empty", Toast.LENGTH_LONG).show();
            error=true;
        }
        try{
            int num = Integer.parseInt(Price.getText().toString());
            // is an integer!
        } catch (NumberFormatException e) {
            // not an integer!
            Price.setError("Service Price must is integer!!!");
            error=true;
        }
        if (!error)
        {
            AddServiceToDatabase();
        }
    }

    private void AddServiceToDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference doc ;
        if (service != null)
            doc = db.collection("Service").document(service.getID());
        else
            doc = db.collection("Service").document();
        Service newService = new Service(Detail.getText().toString(), ImageURL, Name.getText().toString(), Integer.parseInt(Price.getText().toString()), doc.getId());
        doc.set(newService.toJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (service == null) {
                    Name.setText("");
                    Detail.setText("");
                    Price.setText("");
                    Image.setImageResource(R.drawable.foodinput);
                    finish();
                }else
                {
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing Service document", e);
            }
        });
    }

}