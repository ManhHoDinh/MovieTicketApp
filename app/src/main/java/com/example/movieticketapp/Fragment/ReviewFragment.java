package com.example.movieticketapp.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.CommentAdapter;
import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.FragmentReviewBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReviewFragment extends Fragment {

    FilmModel film;
    public ReviewFragment(FilmModel f) {
        // Required empty public constructor
        film = f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ListView commentList = getView().findViewById(R.id.ReviewList);

        ArrayList<Comment> comments = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference CommentRef = db.collection("Movies").document(film.getName()).collection("Comment");
        CommentRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> listDoc = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : listDoc)
                    {
                        String profileUrl = doc.getString("profileUrl");
                        String name = doc.getString("name");
                        String reviewText = doc.getString("reviewText");
                        String like = doc.getString("like");
                        String dislike = doc.getString("dislike");
                        String timeStamp = doc.getString("timeStamp");
                        comments.add(new Comment(profileUrl, name, reviewText, like, dislike, timeStamp));
                        Log.d(TAG, "Added comment from: " + name);
                    }

                    CommentAdapter commentAdapter = new CommentAdapter(getView().getContext(), R.layout.review_comment_view, comments);
                    commentList.setAdapter(commentAdapter);
                }
            }
        });
    }
}