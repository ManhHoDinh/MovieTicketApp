package com.example.movieticketapp.Fragment;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.CommentAdapter;
import com.example.movieticketapp.Adapter.FeelAdapter;
import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.TicketListAdapter;
import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityCityViewAllBinding;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewFragment extends Fragment {

    FilmModel film;
    RatingBar ratingBar;

    ArrayList<Comment> comments;
//    RoundedImageView profileImgView;
//    EditText commentEditText;
//    ImageView sendBtn;
    ListView commentList;
    CollectionReference CommentRef;
//    TextView ratioRate;
    //RelativeLayout commentLayout;

    CommentAdapter commentAdapter;
    String messager;

    List<String> listFeel;
    LinearLayout rateLayout;

    List<String> listReact = new ArrayList<>();

    FirebaseUser currentUser;
    int height = 0;
    int rate = 0;
    Activity myActivity;
    public ReviewFragment(FilmModel f) {
        // Required empty public constructor
        film = f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity =(Activity) context;
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
//        profileImgView = getView().findViewById(R.id.profileImgView);
//        commentEditText = getView().findViewById(R.id.commentEditText);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        //sendBtn = getView().findViewById(R.id.sendBtn);
        ratingBar = getView().findViewById(R.id.rating);
        rateLayout = getView().findViewById(R.id.ratingFilm);
//        commentLayout = getView().findViewById(R.id.commentSectionLayout);
//        commentLayout.setVisibility(View.GONE);
//        ratioRate = getView().findViewById(R.id.ratio);
//        ratioRate.setVisibility(View.GONE);

        listFeel = new ArrayList<>();
        listFeel.add(getEmoji(0x1F917) + " empathetic");
        listFeel.add(getEmoji(0x1F970) + " satisfied");
        listFeel.add(getEmoji(0x1F62D) + " emotion");
        listFeel.add(getEmoji(0x1F923) + " humorous");
        listFeel.add(getEmoji(0x1F929) + " overwhelmed");


        ratingFilm();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CommentRef = db.collection("Movies").document(film.getId()).collection("Comment");
        if(CommentRef!=null){
            CommentRef.orderBy("timeStamp", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    comments.clear();
                    for (QueryDocumentSnapshot doc : value)

                    {
                        comments.add(doc.toObject(Comment.class));

                    }
                    commentAdapter = new CommentAdapter(myActivity, R.layout.review_comment_view, comments, film);
                    int totalHeight= 0;
                    DisplayMetrics displayMetrics = new DisplayMetrics();

                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;
                    for (int size=0; size < commentAdapter.getCount(); size++) {

                        View listItem = commentAdapter.getView(size, null, commentList);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight();
                    }
                    ViewGroup.LayoutParams params=commentList.getLayoutParams();
                    DisplayMetrics display = new DisplayMetrics();
                    myActivity.getWindowManager().getDefaultDisplay().getMetrics(display);
                    int d = display.heightPixels;
                    params.height = d/2 - rateLayout.getMeasuredHeight() - 100;


                    commentList.setLayoutParams(params);
                    commentList.setAdapter(commentAdapter);



                }
            });
        }

    }
    void ratingFilm(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate = (int) v;
                if(rate > 0){
                    showDialogRate(rate);
                }
            }
        });
    }
    String getEmoji(int code){
        return new String(Character.toChars(code));
    }
    void showDialogRate(int rating){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_rating_layout);
        RecyclerView feelView = dialog.findViewById(R.id.yourFeel);
        RatingBar rateBar = dialog.findViewById(R.id.rating);
        TextView ratio = dialog.findViewById(R.id.ratio);
        EditText yourComment = dialog.findViewById(R.id.yourComment);
        Button submitBtn = dialog.findViewById(R.id.submitComment);
        TextView reviewTv = dialog.findViewById(R.id.reviewTv);
        rateBar.setRating(rating);

        ratio.setText(String.valueOf(rating) + "/5");
        switch (rating){
            case 1:
                messager = "So bad!";
                break;
            case 2:
                messager = "Bad!";
                break;
            case 3:
                messager = "Normal!";
                break;
            case 4:
                messager = "Great!";
                break;

            case 5:
                messager = "Excellent!!";
                break;

        }
        reviewTv.setText(messager);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        feelView.setLayoutManager(flexboxLayoutManager);
        feelView.setAdapter(new FeelAdapter(listFeel, listReact));
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate currentDate = LocalDate.now();
                Calendar calendar = Calendar.getInstance();
                Date current = calendar.getTime();
                Timestamp timestamp = new Timestamp(current);
                DocumentReference doc =  FirebaseRequest.database.collection("Movies").document(film.getId()).collection("Comment").document();
                Comment comment = new Comment("https://firebasestorage.googleapis.com/v0/b/movie-ticket-app-0.appspot.com/o/avatar.png?alt=media&token=23a1d250-ca27-414b-a46b-bbef69dac7da"
                ,currentUser.getDisplayName(), yourComment.getText().toString(), 0, 0, timestamp,(int) rateBar.getRating(), listReact, doc.getId());
                doc.set(comment);
//                Map<String, Object> comment = new HashMap<>();
//                comment.put("like", 0);
//                comment.put("dislike", "0");
//                comment.put("name", currentUser.getDisplayName());
//                comment.put("profileUrl", "https://firebasestorage.googleapis.com/v0/b/movie-ticket-app-0.appspot.com/o/avatar.png?alt=media&token=23a1d250-ca27-414b-a46b-bbef69dac7da");
//                comment.put("reviewText", yourComment.getText().toString());
//                comment.put("timeStamp", timestamp);
//                comment.put("rating", rateBar.getRating());
//                comment.put("listReact", listReact);
           //     FirebaseRequest.database.collection("Movies").document(film.getId()).collection("Comment").add(comment);
             //   commentEditText.setText("");
               // rate = 0;

               CommentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       int count = value.size();
                       updateRateFilm(count, rateBar.getRating());

                   }
               });
                ratingBar.setRating(0);
               // rateBar.setRating(0);
                dialog.dismiss();
                Toast.makeText(getContext(), "thanks for your comment!", Toast.LENGTH_SHORT).show();

            }
        });
        rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rate = (int) v;
                ratio.setText(String.valueOf(rate) + "/5");
                switch (rate){
                    case 1:
                        messager = "So bad!";
                        break;
                    case 2:
                        messager = "Bad!";
                        break;
                    case 3:
                        messager = "Normal!";
                        break;
                    case 4:
                        messager = "Great!";
                        break;

                    case 5:
                        messager = "Excellent!!";
                        break;

                }
                reviewTv.setText(messager);
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                ratingBar.setRating(0);
            }
        });
    }
    void updateRateFilm(int countComment, float rate){
        DocumentReference doc =  FirebaseRequest.database.collection("Movies").document(film.getId());
        float vote = film.getVote();
        vote = (vote * (countComment - 1) + rate)/ countComment;
        doc.update("vote", vote);
    }
}