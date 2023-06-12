package com.example.movieticketapp.Activity.Notification;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.NotificationModel;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNotificationActivity extends AppCompatActivity {

    TextInputEditText Description;
    TextInputEditText Heading;
    NotificationModel notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notification);
        Intent intent = getIntent();
        notification = intent.getParcelableExtra(ExtraIntent.notification);
        AddNotification();
    }
    void AddNotification(){
        Description= findViewById(R.id.DescriptionET);
        Heading=findViewById(R.id.HeadingET);
        if(notification!=null)
        {
            Heading.setText(notification.getHeading());
            Description.setText(notification.getDescription());
        }
        AppCompatButton SaveBtn = findViewById(R.id.SaveBtn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if(Description.length()==0)
                {
                    Description.setError("Full Name is not empty!!!");
                    error=true;
                }
                if(Heading.length()==0)
                {
                    Heading.setError("Email is not empty!!!");
                    error=true;
                }
                if(!error)
                {
                    AddNotificationToFirebase();
                }

            }

            private void AddNotificationToFirebase() {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference doc;
if(notification!=null)
    doc= db.collection(NotificationModel.CollectionName).document(notification.getID());
else
    doc= db.collection(NotificationModel.CollectionName).document();
                FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

                NotificationModel notification = new NotificationModel(doc.getId(), Heading.getText().toString(),  Description.getText().toString(),currentUser.getDisplayName(), Timestamp.now());
                doc.set(notification.toJson())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });
    }

}