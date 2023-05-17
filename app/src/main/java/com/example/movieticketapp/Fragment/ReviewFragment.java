package com.example.movieticketapp.Fragment;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.CommentAdapter;
import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.FragmentReviewBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewFragment extends Fragment {

    FilmModel film;

    ArrayList<Comment> comments;
    RoundedImageView profileImgView;
    EditText commentEditText;
    ImageView sendBtn;
    ListView commentList;

    CommentAdapter commentAdapter;
    public ReviewFragment(FilmModel f) {
        // Required empty public constructor
        film = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comments = new ArrayList<>();
        commentList = getView().findViewById(R.id.ReviewList);
        profileImgView = getView().findViewById(R.id.profileImgView);
        commentEditText = getView().findViewById(R.id.commentEditText);
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        sendBtn = getView().findViewById(R.id.sendBtn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference CommentRef = db.collection("Movies").document(film.getId()).collection("Comment");
        CommentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                comments.clear();
                for (QueryDocumentSnapshot doc : value)
                {
                    String profileUrl = doc.getString("profileUrl");
                    String name = doc.getString("name");
                    String reviewText = doc.getString("reviewText");
                    String like = doc.getString("like");
                    String dislike = doc.getString("dislike");
                    String timeStamp = doc.getString("timeStamp");
                    Comment data = new Comment(profileUrl, name, reviewText, like, dislike, timeStamp);
                    comments.add(data);
                    Log.d(TAG, "Added comment from: " + name);
                }
                commentAdapter = new CommentAdapter(getView().getContext(), R.layout.review_comment_view, comments);
                commentList.setAdapter(commentAdapter);
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                Map<String, Object> comment = new HashMap<>();
                comment.put("like", "0");
                comment.put("dislike", "0");
                comment.put("name", currentUser.getDisplayName());
                comment.put("profileUrl", "https://firebasestorage.googleapis.com/v0/b/movie-ticket-app-0.appspot.com/o/avatar.png?alt=media&token=23a1d250-ca27-414b-a46b-bbef69dac7da");
                comment.put("reviewText", commentEditText.getText().toString());
                comment.put("timeStamp", currentDate.format(formatter));

                CommentRef.add(comment)
                        .addOnSuccessListener(documentReference ->
                        {
                            Log.d(TAG, "Document written with ID: " + documentReference.getId());
                        })
                        .addOnFailureListener(e -> {
                            Log.d(TAG, "Error adding document: " + e);
                        });

                commentEditText.setText("");
            }
        });
    }
}