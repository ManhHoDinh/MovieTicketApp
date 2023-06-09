package com.example.movieticketapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Context context;
    private int resource;
    List<Comment> commentList;
    int cellHeight = -1;
    public CommentAdapter (Context context, int resource, List<Comment> commentList)
    {
        super(context, resource, commentList);
        this.resource = resource;
        this.commentList = commentList;
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

        Comment comment = getItem(position);
        if (comment != null)
        {
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
            name.setText(comment.getName());
            reviewText.setText(comment.getReviewText());
            likeNumber.setText(comment.getLike());
            dislikeNumber.setText(comment.getDislike());
            SimpleDateFormat formatter =new SimpleDateFormat("dd/MM/yyyy");
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
