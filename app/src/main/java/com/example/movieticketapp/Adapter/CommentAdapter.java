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

import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.Model.Ticket;
import com.example.movieticketapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Context context;
    private int resource;
    List<Comment> commentList;

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
            RoundedImageView profile = v.findViewById(R.id.profile);
            TextView name = v.findViewById(R.id.name);
            TextView reviewText = v.findViewById(R.id.review_text);
            TextView likeNumber = v.findViewById(R.id.likeNumber);
            TextView dislikeNumber = v.findViewById(R.id.dislikeNumber);
            TextView timeStamp = v.findViewById(R.id.timeStamp);

            SetImage(comment, profile);
            name.setText(comment.getName());
            reviewText.setText(comment.getReviewText());
            likeNumber.setText(comment.getLike());
            dislikeNumber.setText(comment.getDislike());
            timeStamp.setText(comment.getTimeStamp());
        }

        return v;
    }
}
