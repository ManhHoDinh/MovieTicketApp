package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Context context;
    private int resource;
    List<Comment> commentList;
    List<String> likeComments = new ArrayList<>();
    List<String> dislikeComments = new ArrayList<>();

    FilmModel film;
    int cellHeight = -1;
    public CommentAdapter (Context context, int resource, List<Comment> commentList, FilmModel film)
    {
        super(context, resource, commentList);
        this.resource = resource;
        this.commentList = commentList;
        this.film = film;
    }

    void SetImage(Comment postItem, RoundedImageView imageView){
        Picasso.get()
                .load(postItem.getProfileUrl())
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (convertView == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }


        ImageView likeBtn = v.findViewById(R.id.likeBtn);
        ImageView dislikeBtn = v.findViewById(R.id.dislikeBtn);
        DocumentReference userRef = FirebaseRequest.database.collection("Users").document(FirebaseRequest.mAuth.getUid());
        Comment comment = getItem(position);
        if (comment != null)
        {
            userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    Users user = value.toObject(Users.class);
                    likeComments = user.getLikeComments();
                    dislikeComments = user.getDislikeComments();
                    if(likeComments != null){
                        for(String id : likeComments){
                            if(id.equals(comment.getID())){
                                likeBtn.setImageResource(R.drawable.heart_fill_icon);
                                likeBtn.setTag(R.drawable.heart_fill_icon);
                                break;
                            }
                            else{
                                likeBtn.setImageResource(R.drawable.heart_icon);
                                likeBtn.setTag("bg");
                            }
                        }
                    }
                    if(dislikeComments != null){
                        for(String id: dislikeComments){
                            if(id.equals(comment.getID())){
                                Log.e("dfd", comment.getID());
                                dislikeBtn.setImageResource(R.drawable.dislike_fill_icon);
                                dislikeBtn.setTag(R.drawable.dislike_fill_icon);
                                break;
                            }
                            else{
                                Log.e("false", comment.getID());
                                dislikeBtn.setImageResource(R.drawable.dislike_icon);
                                dislikeBtn.setTag("cg");
                            }
                        }
                    }



                }
            });
//            userRef.collection("LikeComment").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                @Override
//                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                    for(DocumentSnapshot doc : queryDocumentSnapshots){
//                        if(doc.get("commentID").toString().equals(comment.getID())){
//                            likeBtn.setImageResource(R.drawable.heart_fill_icon);
//                            likeBtn.setTag(R.drawable.heart_fill_icon);
//                        }
//                    }
//                }
//            });
//            userRef.collection("LikeComment").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                    Log.e("binhh", "hu");
//                    for(DocumentSnapshot doc : value){
//                        if(doc.get("commentID").toString().equals(comment.getID())){
//                            likeBtn.setImageResource(R.drawable.heart_fill_icon);
//                            likeBtn.setTag(R.drawable.heart_fill_icon);
//                        }
//                        else{
//                            likeBtn.setImageResource(R.drawable.heart_icon);
//                            likeBtn.setTag("bg");
//                        }
//                    }
//                }
//            });
//            userRef.collection("DisLikeComment").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                @Override
//                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                    for(DocumentSnapshot doc : queryDocumentSnapshots){
//                        if(doc.get("commentID").toString().equals(comment.getID())){
//                            dislikeBtn.setImageResource(R.drawable.dislike_fill_icon);
//                            dislikeBtn.setTag(R.drawable.dislike_fill_icon);
//                        }
//                    }
//                }
//            });
//            userRef.collection("DisLikeComment").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                    for(DocumentSnapshot doc : value){
//                        if(doc.get("commentID").toString().equals(comment.getID())){
//                            Log.e("dfd", comment.getID());
//                            dislikeBtn.setImageResource(R.drawable.dislike_fill_icon);
//                            dislikeBtn.setTag(R.drawable.dislike_fill_icon);
//                        }
//                        else{
//                            Log.e("false", comment.getID());
//                            dislikeBtn.setImageResource(R.drawable.dislike_icon);
//                            dislikeBtn.setTag("cg");
//                        }
//                    }
//                }
//            });
            DocumentReference commentDoc =  FirebaseRequest.database.collection("Movies")
                    .document(film.getId()).collection("Comment")
                    .document(comment.getID());
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("like", "like");
                    if(likeBtn.getTag().equals("bg")){
                        likeBtn.setImageResource(R.drawable.heart_fill_icon);
                        likeBtn.setTag(R.drawable.heart_fill_icon);
                        HashMap<String, String> likeComment = new HashMap<>();
                        if(likeComments == null){
                            likeComments = new ArrayList<>();
                        }
                        likeComments.add(comment.getID());
                        userRef.update("likeComments", likeComments);
//                        DocumentReference doc = userRef.collection("LikeComment").document(comment.getID());
//                        likeComment.put("commentID", comment.getID());
//                        doc.set(likeComment);
                        commentDoc.update("like", comment.getLike() + 1);
                        if(dislikeBtn.getTag().equals(R.drawable.dislike_fill_icon)){
                            dislikeBtn.setImageResource(R.drawable.dislike_icon);
                            dislikeBtn.setTag("cg");
                            dislikeComments.remove(comment.getID());
//                            userRef.collection("DisLikeComment").document(comment.getID()).delete();
                            userRef.update("dislikeComments", dislikeComments);
                            commentDoc.update("dislike", comment.getDislike() - 1);
                        }
                    }else {
                        Log.e("dislike", "dislike");
                        likeBtn.setImageResource(R.drawable.heart_icon);
                        likeBtn.setTag("bg");
                        likeComments.remove(comment.getID());
                        userRef.update("likeComments", likeComments);
                       // userRef.collection("LikeComment").document(comment.getID()).delete();
                        commentDoc.update("like", comment.getLike() - 1);
                    }
//                    commentDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            Comment comment = documentSnapshot.toObject(Comment.class);
//                            int countLike = comment.getLike();
//                            int countDisLike = comment.getDislike();
//
//                            if(likeBtn.getTag().equals("bg")){
//                                countLike -= 1;
//                            }
//                            else countLike +=1;
//                            commentDoc.update("like", countLike).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                }
//                            });
//
//
//                        }
//                    });

                }
            });
            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(dislikeBtn.getTag().equals("cg")){
                        dislikeBtn.setImageResource(R.drawable.dislike_fill_icon);
                        dislikeBtn.setTag(R.drawable.dislike_icon);
//                        HashMap<String, String> disLikeComment = new HashMap<>();
//                        DocumentReference doc = userRef.collection("DisLikeComment").document(comment.getID());
//                        disLikeComment.put("commentID", comment.getID());
//                        doc.set(disLikeComment);
                        if(dislikeComments == null){
                            dislikeComments = new ArrayList<>();
                        }
                        dislikeComments.add(comment.getID());
                        userRef.update("dislikeComments",dislikeComments);
                        commentDoc.update("dislike", comment.getDislike() + 1);
                        if(likeBtn.getTag().equals(R.drawable.heart_fill_icon)){
                            likeBtn.setImageResource(R.drawable.heart_icon);
                            likeBtn.setTag("bg");
                            likeComments.remove(comment.getID());
                            userRef.update("likeComments", likeComments);
//                            userRef.collection("LikeComment").document(comment.getID()).delete();
                            commentDoc.update("like", comment.getLike() - 1);
                        }
                    }else {

                        dislikeBtn.setImageResource(R.drawable.dislike_icon);
                        dislikeBtn.setTag("cg");
                        dislikeComments.remove(comment.getID());
                        userRef.update("dislikeComments", dislikeComments);
                        //userRef.collection("DisLikeComment").document(comment.getID()).delete();
                        commentDoc.update("dislike", comment.getDislike() - 1);
                    }

//                    commentDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            Comment comment = documentSnapshot.toObject(Comment.class);
//                            int countDislike = comment.getDislike();
//                            int countLike = comment.getLike();
//
//                            if(dislikeBtn.getTag().equals("cg")){
//                                countDislike -= 1;
//                            }
//                            else countDislike +=1;
//                            commentDoc.update("dislike", countDislike);
//                        }
//                    });

                }
            });
            String messager = "";
            RoundedImageView profile = v.findViewById(R.id.profile);
            TextView name = v.findViewById(R.id.name);
            TextView reviewText = v.findViewById(R.id.review_text);
            TextView likeNumber = v.findViewById(R.id.likeNumber);
            TextView dislikeNumber = v.findViewById(R.id.dislikeNumber);
            TextView timeStamp = v.findViewById(R.id.timeStamp);
            TextView rate = v.findViewById(R.id.rate);
            RecyclerView listReact = v.findViewById(R.id.listReact);
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext()){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
            listReact.setLayoutManager(flexboxLayoutManager);
            listReact.setAdapter(new FeelAdapter(comment.getListReact(), null));
            SetImage(comment, profile);
            if(comment.getUserId()!= null){
                FirebaseRequest.database.collection("Users").document(comment.getUserId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Users user = value.toObject(Users.class);
                        name.setText(user.getName());
                    }
                });
            }
            reviewText.setText(comment.getReviewText());
            likeNumber.setText(String.valueOf(comment.getLike()));
            dislikeNumber.setText(String.valueOf(comment.getDislike()));
            SimpleDateFormat formatter =new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            timeStamp.setText(formatter.format(comment.getTimeStamp().toDate()));
            switch (comment.getRating()){
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
            rate.setText(String.valueOf(comment.getRating()) + "/5" +" " + messager);
        }

        return v;
    }
}
